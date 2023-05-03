package ru.practicum.ewm.main.server.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.server.event.controller.UpdateEventAdminRequestDto;
import ru.practicum.ewm.main.server.event.dto.AdminStateAction;
import ru.practicum.ewm.main.server.event.dto.EventFullDto;
import ru.practicum.ewm.main.server.event.dto.mapper.AdminEventMapper;
import ru.practicum.ewm.main.server.event.dto.mapper.EventMapper;
import ru.practicum.ewm.main.server.event.entity.Event;
import ru.practicum.ewm.main.server.event.repository.EventRepository;
import ru.practicum.ewm.main.server.event.state.EventState;
import ru.practicum.ewm.main.server.exception.EventNotFoundException;
import ru.practicum.ewm.main.server.exception.InvalidEventDateException;
import ru.practicum.ewm.main.server.exception.InvalidStateActionException;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.Objects.requireNonNullElseGet;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class AdminEventService {
    private final EventRepository eventRepository;
    private final AdminEventMapper adminEventMapper;
    private final EventMapper eventMapper;


    public List<EventFullDto> getEvents(List<Long> userIds, List<String> states, List<Long> categoryIds, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        return eventRepository.getEventsForAdmin(
                        userIds,
                        states,
                        categoryIds,
                        rangeStart,
                        rangeEnd,
                        from,
                        size
                ).stream()
                .map(adminEventMapper::toDto)
                .collect(toList());
    }

    public EventFullDto patchEvent(
            Long eventId,
            UpdateEventAdminRequestDto requestDto
    ) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Could not find the requested event."));
        Event updated = adminEventMapper.partialUpdate(requestDto, event);
        LocalDateTime eventDate = updated.getEventDate();
        LocalDateTime publicationDate = updated.getPublishedOn();
        checkEventDate(eventDate, publicationDate);
        applyStateAction(requestDto.getStateAction(), updated);
        Event saved = eventRepository.save(updated);
        return eventMapper.toFullDto(saved);
    }

    private void checkEventDate(LocalDateTime eventDate, LocalDateTime publicationDate) {
        if (eventDate.isBefore(
                requireNonNullElseGet(publicationDate, LocalDateTime::now).plusHours(1)
        )) {
            throw new InvalidEventDateException("You cannot set event date earlier than one hour after event publication.");
        }
    }

    private void applyStateAction(
            AdminStateAction stateAction,
            Event event
    ) {
        switch (stateAction) {
            case PUBLISH_EVENT: {
                if (!EventState.PENDING.equals(event.getState())) {
                    throw new InvalidStateActionException("You cannot publish event if its state is not PENDING.");
                }
                event.setState(EventState.PUBLISHED);
                event.setPublishedOn(now());
                return;
            }
            case REJECT_EVENT:
                if (!EventState.PENDING.equals(event.getState())) {
                    throw new InvalidStateActionException("You cannot reject event if it is already published.");
                }
                event.setState(EventState.REJECTED);
        }
    }
}
