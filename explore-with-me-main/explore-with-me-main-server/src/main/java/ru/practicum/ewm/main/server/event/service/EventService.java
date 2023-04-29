package ru.practicum.ewm.main.server.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.server.event.controller.AdminEventDto;
import ru.practicum.ewm.main.server.event.dto.mapper.AdminEventMapper;
import ru.practicum.ewm.main.server.event.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AdminEventMapper adminEventMapper;


    public List<AdminEventDto> getEvents(List<Long> userIds, List<String> states, List<Long> categoryIds, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
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
}
