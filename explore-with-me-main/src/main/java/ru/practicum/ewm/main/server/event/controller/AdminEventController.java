package ru.practicum.ewm.main.server.event.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.server.event.dto.EventFullDto;
import ru.practicum.ewm.main.server.event.dto.UpdateEventAdminRequestDto;
import ru.practicum.ewm.main.server.event.dto.filter.AdminEventFilterQuery;
import ru.practicum.ewm.main.server.event.entity.state.EventState;
import ru.practicum.ewm.main.server.event.service.AdminEventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class AdminEventController {
    private static final String DATETIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final AdminEventService adminEventService;

    @GetMapping
    public List<EventFullDto> getEvents(
            @RequestParam(
                    name = "users",
                    defaultValue = ""
            )
            List<Long> userIds,
            @RequestParam(
                    name = "states",
                    defaultValue = ""
            )
            List<EventState> states,
            @RequestParam(
                    name = "categories",
                    defaultValue = ""
            )
            List<Long> categoryIds,
            @RequestParam(
                    name = "rangeStart",
                    required = false
            )
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_FORMAT_PATTERN)
            LocalDateTime rangeStart,
            @RequestParam(
                    name = "rangeEnd",
                    required = false
            )
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_FORMAT_PATTERN)
            LocalDateTime rangeEnd,
            @RequestParam(
                    name = "from",
                    defaultValue = "0"
            )
            Integer from,
            @RequestParam(
                    name = "size",
                    defaultValue = "10"
            )
            Integer size
    ) {
        AdminEventFilterQuery filterQuery = AdminEventFilterQuery.builder()
                .userIds(userIds)
                .states(states)
                .categoryIds(categoryIds)
                .rangeStart(rangeStart)
                .from(from)
                .size(size)
                .rangeEnd(rangeEnd)
                .build();
        return adminEventService.getEvents(filterQuery);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEvent(
            @PathVariable(name = "eventId")
            Long eventId,
            @RequestBody
            UpdateEventAdminRequestDto requestDto
    ) {
        return adminEventService.patchEvent(eventId, requestDto);
    }
}
