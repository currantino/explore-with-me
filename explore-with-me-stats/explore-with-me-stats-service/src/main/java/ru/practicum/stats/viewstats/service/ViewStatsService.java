package ru.practicum.stats.viewstats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.stats.endpointhit.repository.EndpointHitRepository;
import ru.practicum.stats.viewstats.projections.ViewStatsProjection;
import ru.practicum.viewstats.ViewStats;
import ru.practicum.viewstats.ViewStatsRequestDto;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ViewStatsService {
    private final EndpointHitRepository endpointHitRepository;

    private static final Function<ViewStatsProjection, ViewStats> MAPPER = proj ->
            ViewStats
                    .builder()
                    .hits(proj.getHits())
                    .app(proj.getApp())
                    .uri(proj.getUri())
                    .build();

    public List<ViewStats> viewStats(ViewStatsRequestDto requestDto) {
        final LocalDateTime start = requestDto.getStart();
        final LocalDateTime end = requestDto.getEnd();
        if (requestDto.getUris() == null || requestDto.getUris()
                .isEmpty()) {
            if (requestDto.getUnique()) return endpointHitRepository.findAllByStartEndUnique(start, end)
                    .stream()
                    .map(MAPPER)
                    .collect(toList());
            else return endpointHitRepository.findAllByStartEnd(start, end)
                    .stream()
                    .map(MAPPER)
                    .collect(toList());
        }
        if (requestDto.getUnique()) {
            return requestDto.getUris()
                    .stream()
                    .map(uri -> endpointHitRepository.findByStartEndUriUnique(
                            start,
                            end,
                            uri
                    ))
                    .map(MAPPER)
                    .sorted(Comparator.comparing(ViewStats::getHits)
                            .reversed())
                    .collect(toList());
        } else {
            return requestDto.getUris()
                    .stream()
                    .map(uri ->
                            endpointHitRepository.findByStartEndUri(
                                    start,
                                    end,
                                    uri
                            )
                    )
                    .map(MAPPER)
                    .sorted(Comparator.comparing(ViewStats::getHits)
                            .reversed())
                    .collect(toList());
        }
    }


}
