package ru.practicum.ewm.main.server.participationrequest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.server.event.entity.Event;
import ru.practicum.ewm.main.server.event.entity.state.EventState;
import ru.practicum.ewm.main.server.event.repository.CustomEventRepository;
import ru.practicum.ewm.main.server.exception.*;
import ru.practicum.ewm.main.server.participationrequest.dto.ParticipationRequestDto;
import ru.practicum.ewm.main.server.participationrequest.dto.ParticipationRequestStatus;
import ru.practicum.ewm.main.server.participationrequest.entity.ParticipationRequest;
import ru.practicum.ewm.main.server.participationrequest.mapper.ParticipationRequestMapper;
import ru.practicum.ewm.main.server.participationrequest.repository.ParticipationRequestRepository;
import ru.practicum.ewm.main.server.user.entity.User;
import ru.practicum.ewm.main.server.user.repository.UserRepository;

import java.util.List;
import java.util.Objects;

import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toList;
import static ru.practicum.ewm.main.server.participationrequest.dto.ParticipationRequestStatus.*;

@Service
@RequiredArgsConstructor
public class PrivateParticipationRequestService {
    private final ParticipationRequestRepository participationRequestRepository;
    private final UserRepository userRepository;
    private final CustomEventRepository eventRepository;
    private final ParticipationRequestMapper participationRequestMapper;

    public ParticipationRequestDto createParticipationRequest(Long userId, Long eventId) {
        User requester = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Could not find the requested user."));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Could not find the requested event."));
        checkIfParticipantLimitWasReached(event);
        checkIfRequesterIsNotInitiatorOfEvent(userId, event);
        checkIfEventIsPublished(event);
        ParticipationRequestStatus status;
        if (event.getRequestModeration()) {
            status = PENDING;
        } else {
            status = CONFIRMED;
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        }
        ParticipationRequest request = ParticipationRequest
                .builder()
                .status(status)
                .event(event)
                .requester(requester)
                .created(now())
                .build();
        ParticipationRequest saved = participationRequestRepository.save(request);
        return participationRequestMapper.toDto(saved);
    }

    private void checkIfParticipantLimitWasReached(Event event) {
        if (!Objects.equals(event.getParticipantLimit(), 0)
                && Objects.equals(event.getParticipantLimit(), event.getConfirmedRequests())) {
            throw new ParticipantLimitReachedException("Reached participant limit of the event.");
        }
    }

    public List<ParticipationRequestDto> getParticipationRequestsByParticipatorId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("Could not find the requested user.");
        }
        return userRepository
                .findAllByRequesterId(userId).stream()
                .map(participationRequestMapper::toDto)
                .collect(toList());
    }

    public ParticipationRequestDto cancelParticipationRequest(Long userId, Long requestId) {
        return participationRequestRepository.findById(requestId)
                .map(
                        request -> {
                            if (!Objects.equals(request.getRequester().getId(), userId)) {
                                throw new EventNotAccessibleException("You can not cancel requests of other users.");
                            }
                            request.setStatus(CANCELED);
                            ParticipationRequest saved = participationRequestRepository.save(request);
                            return participationRequestMapper.toDto(saved);
                        }
                )
                .orElseThrow(
                        () -> new ParticipationRequestNotFoundException("Could not find the requested participation request.")
                );
    }

    private void checkIfEventIsPublished(Event event) {
        if (!EventState.PUBLISHED.equals(event.getState())) {
            throw new EventNotPublishedException("You cannot request participation in event that is not published.");
        }
    }

    private void checkIfRequesterIsNotInitiatorOfEvent(Long userId, Event event) {
        if (Objects.equals(event.getInitiator().getId(), userId)) {
            throw new InitiatorParticipationRequestException("You cannot request participation in the event if you are its initiator");
        }
    }
}
