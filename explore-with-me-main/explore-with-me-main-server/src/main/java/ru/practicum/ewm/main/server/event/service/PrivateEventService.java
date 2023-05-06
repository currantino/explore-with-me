package ru.practicum.ewm.main.server.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.server.category.entity.Category;
import ru.practicum.ewm.main.server.category.repository.CategoryRepository;
import ru.practicum.ewm.main.server.event.dto.*;
import ru.practicum.ewm.main.server.event.dto.stateaction.PrivateEventStateAction;
import ru.practicum.ewm.main.server.event.entity.Event;
import ru.practicum.ewm.main.server.event.entity.state.EventState;
import ru.practicum.ewm.main.server.event.mapper.EventMapper;
import ru.practicum.ewm.main.server.event.repository.EventRepository;
import ru.practicum.ewm.main.server.exception.*;
import ru.practicum.ewm.main.server.location.entity.Location;
import ru.practicum.ewm.main.server.location.mapper.LocationMapper;
import ru.practicum.ewm.main.server.location.repository.LocationRepository;
import ru.practicum.ewm.main.server.participationrequest.dto.ParticipationRequestDto;
import ru.practicum.ewm.main.server.participationrequest.dto.ParticipationRequestStatus;
import ru.practicum.ewm.main.server.participationrequest.entity.ParticipationRequest;
import ru.practicum.ewm.main.server.participationrequest.mapper.ParticipationRequestMapper;
import ru.practicum.ewm.main.server.participationrequest.repository.ParticipationRequestRepository;
import ru.practicum.ewm.main.server.user.entity.User;
import ru.practicum.ewm.main.server.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PrivateEventService {

    private final EventMapper eventMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final EventRepository eventRepository;
    private final ParticipationRequestRepository participationRequestRepository;
    private final ParticipationRequestMapper participationRequestMapper;

    public EventFullDto createEvent(
            Long userId,
            CreateEventRequestDto eventDto
    ) {
        User initiator = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Could not find the requested user."));
        Category category = categoryRepository.findById(eventDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException("Could not find the requested category."));
        Location location = createIfNotExists(
                locationMapper.toEntity(
                        eventDto.getLocation()
                )
        );

        Event event = eventMapper.toEntity(eventDto);
        event.setInitiator(initiator);
        event.setCategory(category);
        event.setLocation(location);
        event.setState(EventState.PENDING);
        event.setCreatedOn(now());
        Event saved = eventRepository.save(event);
        return eventMapper.toFullDto(saved);

    }

    private Location createIfNotExists(Location location) {
        return locationRepository
                .findByLonAndLat(
                        location.getLon(),
                        location.getLat()
                )
                .orElse(
                        locationRepository
                                .saveAndFlush(location)
                );
    }

    public List<EventShortDto> getEventsByUserId(
            Long userId,
            Integer from,
            Integer size
    ) {
        Pageable pageRequest = PageRequest.of(from, size);
        return eventRepository
                .findAllByInitiator(userId, pageRequest)
                .map(eventMapper::toShortDto)
                .getContent();
    }

    public EventFullDto getEventByUserIdAndEventId(Long userId, Long eventId) {
        return eventRepository
                .findById(eventId)
                .map(e -> {
                            checkThatRequesterIsEventInitiator(userId, e.getInitiator().getId());
                            return eventMapper.toFullDto(e);
                        }
                )
                .orElseThrow(() -> new EventNotFoundException("Could not find the requested event."));
    }

    public EventFullDto patchEvent(
            Long eventId,
            Long userId,
            UpdateEventRequestDto updateDto
    ) {
        checkIfCategoryForUpdateExists(updateDto.getCategory());
        Event event = eventRepository
                .findById(eventId)
                .map(e -> {
                            checkThatRequesterIsEventInitiator(userId, e.getInitiator().getId());
                            return e;
                        }
                )
                .orElseThrow(() -> new EventNotFoundException("Could not find the requested event."));
        if (EventState.PUBLISHED.equals(event.getState())) {
            throw new EventAlreadyPublishedException("You cannot update event if it is already published.");
        }
        eventMapper.partialUpdate(updateDto, event);
        PrivateEventStateAction stateAction = updateDto.getStateAction();
        if (stateAction != null) {
            applyStateAction(updateDto.getStateAction(), event);
        }
        Event saved = eventRepository.save(event);
        return eventMapper.toFullDto(saved);
    }

    public List<ParticipationRequestDto> getParticipationRequestsByEventId(Long userId, Long eventId) {
        Long eventInitiatorId = eventRepository.findInitiatorIdByEventId(eventId);
        checkThatRequesterIsEventInitiator(userId, eventInitiatorId);
        return participationRequestRepository
                .findAllByEventId(eventId).stream()
                .map(participationRequestMapper::toDto)
                .collect(toList());
    }

    public EventRequestStatusUpdateResult updateParticipationRequestStatus(
            Long userId,
            Long eventId,
            EventStatusUpdateRequest updateRequest
    ) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Could not find the requested event."));

        checkThatRequesterIsEventInitiator(userId, event.getInitiator().getId());

        List<ParticipationRequest> participationRequestsToBeUpdated = participationRequestRepository
                .findAllById(updateRequest.getRequestIds());
        ParticipationRequestStatus newStatus = updateRequest.getStatus();
        List<ParticipationRequestDto> confirmedRequests = new ArrayList<>();
        List<ParticipationRequestDto> rejectedRequests = new ArrayList<>();
        participationRequestsToBeUpdated.forEach(
                request -> {
                    if (!ParticipationRequestStatus.PENDING.equals(request.getStatus())) {
                        throw new ParticipationRequestStatusException("Request must have status PENDING");
                    }
                    if (Objects.equals(event.getParticipantLimit(), event.getConfirmedRequests())) {
                        rejectAllPendingRequestsByEventId(eventId);
                        throw new ParticipantLimitReachedException("Participant limit was reached.");
                    }
                    switch (newStatus) {
                        case CONFIRMED: {
                            confirmRequest(event, newStatus, confirmedRequests, request);
                            return;
                        }
                        case REJECTED: {
                            rejectRequest(newStatus, rejectedRequests, request);
                            return;
                        }
                        default: {
                            throw new InvalidStatusException("Unknown event status!");
                        }
                    }
                }
        );
        participationRequestRepository.saveAll(participationRequestsToBeUpdated);
        eventRepository.save(event);
        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(confirmedRequests)
                .rejectedRequests(rejectedRequests)
                .build();
    }

    private void rejectRequest(ParticipationRequestStatus newStatus, List<ParticipationRequestDto> rejectedRequests, ParticipationRequest request) {
        request.setStatus(newStatus);
        rejectedRequests.add(participationRequestMapper.toDto(request));
    }

    private void confirmRequest(Event event, ParticipationRequestStatus newStatus, List<ParticipationRequestDto> confirmedRequests, ParticipationRequest request) {
        request.setStatus(newStatus);
        event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        confirmedRequests.add(participationRequestMapper.toDto(request));
    }

    private void rejectAllPendingRequestsByEventId(Long eventId) {
        participationRequestRepository
                .updateStatusByEventIdAndPending(eventId, ParticipationRequestStatus.REJECTED);
    }

    private void applyStateAction(PrivateEventStateAction stateAction, Event event) {
        switch (stateAction) {
            case CANCEL_REVIEW: {
                event.setState(EventState.CANCELED);
                return;
            }
            case SEND_TO_REVIEW: {
                event.setState(EventState.PENDING);
                return;
            }
            default: {
                throw new InvalidStateActionException("Unknown state action!");
            }
        }
    }

    private void checkIfCategoryForUpdateExists(Long category) {
        if (category != null && !categoryRepository.existsById(category)) {
            throw new CategoryNotFoundException("Cannot find the requested category.");
        }
    }

    private static void checkThatRequesterIsEventInitiator(Long userId, Long eventInitiatorId) {
        if (!Objects.equals(userId, eventInitiatorId)) {
            throw new EventNotAccessibleException("You do not have access to the requested event.");
        }
    }
}
