package ru.practicum.stats.viewstats.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.stats.viewstats.service.ViewStatsService;
import ru.practicum.viewstats.ViewStats;
import ru.practicum.viewstats.ViewStatsRequestDto;

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
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime start,
            @RequestParam(name = "end")
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime end,
            @RequestParam(name = "uris",
                    required = false)
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
        return viewStatsService.viewStats(requestDto);
    }


}
