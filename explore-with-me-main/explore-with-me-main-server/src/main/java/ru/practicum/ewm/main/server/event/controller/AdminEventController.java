package ru.practicum.ewm.main.server.event.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main.server.event.dto.AdminEventDto;
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
    public List<AdminEventDto> getEvents(
            @RequestParam(
                    name = "users",
                    defaultValue = ""
            )
            List<Long> userIds,
            @RequestParam(
                    name = "states",
                    defaultValue = ""
            )
            List<String> states,
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
        return adminEventService.getEvents(
                userIds,
                states,
                categoryIds,
                rangeStart,
                rangeEnd,
                from,
                size);
    }


}
