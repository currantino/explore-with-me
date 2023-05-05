package ru.practicum.ewm.main.server.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.server.event.dto.EventFullDto;
import ru.practicum.ewm.main.server.event.dto.UpdateEventAdminRequestDto;
import ru.practicum.ewm.main.server.event.dto.stateaction.AdminEventStateAction;
import ru.practicum.ewm.main.server.event.entity.Event;
import ru.practicum.ewm.main.server.event.entity.state.EventState;
import ru.practicum.ewm.main.server.event.mapper.AdminEventMapper;
import ru.practicum.ewm.main.server.event.mapper.EventMapper;
import ru.practicum.ewm.main.server.event.repository.EventRepository;
import ru.practicum.ewm.main.server.exception.EventNotFoundException;
import ru.practicum.ewm.main.server.exception.InvalidStateActionException;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class AdminEventService {
    private final EventRepository eventRepository;
    private final AdminEventMapper adminEventMapper;
    private final EventMapper eventMapper;


    public List<EventFullDto> getEvents(List<Long> userIds, List<EventState> states, List<Long> categoryIds, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
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
        if (requestDto.getStateAction() != null) {
            applyStateAction(requestDto.getStateAction(), updated);
        }
        Event saved = eventRepository.save(updated);
        return eventMapper.toFullDto(saved);
    }

    private void applyStateAction(
            AdminEventStateAction stateAction,
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
