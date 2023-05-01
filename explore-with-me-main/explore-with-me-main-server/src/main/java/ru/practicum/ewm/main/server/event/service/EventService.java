package ru.practicum.ewm.main.server.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.server.category.entity.Category;
import ru.practicum.ewm.main.server.category.repository.CategoryRepository;
import ru.practicum.ewm.main.server.event.controller.EventShortDto;
import ru.practicum.ewm.main.server.event.dto.CreateEventRequestDto;
import ru.practicum.ewm.main.server.event.dto.EventFullDto;
import ru.practicum.ewm.main.server.event.dto.StateAction;
import ru.practicum.ewm.main.server.event.dto.UpdateEventRequestDto;
import ru.practicum.ewm.main.server.event.dto.mapper.EventMapper;
import ru.practicum.ewm.main.server.event.entity.Event;
import ru.practicum.ewm.main.server.event.repository.EventRepository;
import ru.practicum.ewm.main.server.event.state.State;
import ru.practicum.ewm.main.server.exception.CategoryNotFoundException;
import ru.practicum.ewm.main.server.exception.EventNotAccessibleException;
import ru.practicum.ewm.main.server.exception.EventNotFoundException;
import ru.practicum.ewm.main.server.exception.UserNotFoundException;
import ru.practicum.ewm.main.server.location.dto.mapper.LocationMapper;
import ru.practicum.ewm.main.server.location.entity.Location;
import ru.practicum.ewm.main.server.location.repository.LocationRepository;
import ru.practicum.ewm.main.server.participationrequest.entity.ParticipationRequestDto;
import ru.practicum.ewm.main.server.participationrequest.entity.ParticipationRequestMapper;
import ru.practicum.ewm.main.server.participationrequest.repository.ParticipationRequestRepository;
import ru.practicum.ewm.main.server.user.entity.User;
import ru.practicum.ewm.main.server.user.repository.UserRepository;

import java.util.List;
import java.util.Objects;

import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class EventService {

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
        event.setState(State.PENDING);
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
                            checkThatRequesterIsEventInitiator(e.getInitiator().getId(), userId);
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
                            checkThatRequesterIsEventInitiator(e.getInitiator().getId(), userId);
                            return e;
                        }
                )
                .orElseThrow(() -> new EventNotFoundException("Could not find the requested event."));
        eventMapper.partialUpdate(updateDto, event);
        applyStateAction(updateDto.getStateAction(), event);
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

    private void applyStateAction(StateAction stateAction, Event event) {
        if (Objects.requireNonNull(stateAction) == StateAction.CANCEL_REVIEW) {
            event.setState(State.CANCELED);
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
