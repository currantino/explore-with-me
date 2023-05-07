package ru.practicum.stats.viewstats.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.stats.dto.viewstats.ViewStats;
import ru.practicum.ewm.stats.dto.viewstats.ViewStatsRequestDto;
import ru.practicum.stats.viewstats.service.ViewStatsService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stats")
public class ViewStatsController {
    private final ViewStatsService viewStatsService;

    @GetMapping
    public List<ViewStats> viewStats(
            @RequestParam(name = "start")
            LocalDateTime start,
            @RequestParam(name = "end")
            LocalDateTime end,
            @RequestParam(name = "uris",
                    defaultValue = Strings.EMPTY)
            List<String> uris,
            @RequestParam(name = "unique",
                    defaultValue = "false")
            Boolean unique
    ) {
        ViewStatsRequestDto requestDto = ViewStatsRequestDto
                .builder()
                .start(start)
                .end(end)
                .uris(uris)
                .unique(unique)
                .build();
        return viewStatsService.getViewStats(requestDto);
    }


}
