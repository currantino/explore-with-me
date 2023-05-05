package ru.practicum.ewm.main.server.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.server.event.dto.EventFullDto;
import ru.practicum.ewm.main.server.event.dto.EventShortDto;
import ru.practicum.ewm.main.server.event.dto.EventSort;
import ru.practicum.ewm.main.server.event.entity.Event;
import ru.practicum.ewm.main.server.event.mapper.EventMapper;
import ru.practicum.ewm.main.server.event.repository.EventRepository;
import ru.practicum.ewm.main.server.exception.EventNotFoundException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PublicEventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Transactional
    public List<EventShortDto> getEventsFiltered(
            String searchQuery,
            List<Long> categoryIds,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Boolean onlyAvailable,
            EventSort sort,
            Integer from,
            Integer size
    ) {
        List<Event> filtered = eventRepository.getEventsForPublic(
                searchQuery,
                categoryIds,
                paid,
                rangeStart,
                rangeEnd,
                onlyAvailable,
                sort,
                from,
                size
        );

        List<Long> viewedEventsIds = filtered.stream()
                .map(Event::getId)
                .collect(toList());

        eventRepository.addViewByIdIn(viewedEventsIds);
        return filtered.stream()
                .map(eventMapper::toShortDto)
                .collect(toList());
    }

    @Transactional
    public EventFullDto getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Could not find the requested event."));
        eventRepository.addViewById(eventId);
        return eventMapper.toFullDto(event);
    }
}
