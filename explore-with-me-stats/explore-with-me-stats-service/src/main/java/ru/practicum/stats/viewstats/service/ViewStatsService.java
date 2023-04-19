package ru.practicum.stats.viewstats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.dto.viewstats.ViewStats;
import ru.practicum.ewm.stats.dto.viewstats.ViewStatsRequestDto;
import ru.practicum.stats.endpointhit.repository.EndpointHitRepository;
import ru.practicum.stats.viewstats.projections.ViewStatsProjection;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ViewStatsService {
    private final EndpointHitRepository endpointHitRepository;

    public List<ViewStats> getViewStats(ViewStatsRequestDto requestDto) {
        if (requestDto.getUris().isEmpty()) {
            return getViewStatsByStartEnd(requestDto);
        }
        return getViewStatsByStartEndUri(requestDto);
    }

    private List<ViewStats> getViewStatsByStartEnd(ViewStatsRequestDto requestDto) {
        if (requestDto.getUnique()) {
            return findAllByStartEndUnique(requestDto);
        }
        return findAllByStartEnd(requestDto);
    }

    private List<ViewStats> getViewStatsByStartEndUri(ViewStatsRequestDto requestDto) {
        if (requestDto.getUnique()) {
            return findAllByStartEndUriUnique(requestDto);
        }
        return findAllByStartEndUri(requestDto);
    }

    private List<ViewStats> findAllByStartEnd(ViewStatsRequestDto requestDto) {
        return mapToViewStats(
                endpointHitRepository.findAllByStartEnd(
                        requestDto.getStart(),
                        requestDto.getEnd()
                )
        );
    }

    private List<ViewStats> findAllByStartEndUnique(ViewStatsRequestDto requestDto) {
        return mapToViewStats(
                endpointHitRepository.findAllByStartEndUnique(
                        requestDto.getStart(),
                        requestDto.getStart()
                )
        );
    }

    private List<ViewStats> findAllByStartEndUri(ViewStatsRequestDto requestDto) {
        return mapToViewStats(
                endpointHitRepository.findAllByStartEndUri(
                        requestDto.getStart(),
                        requestDto.getEnd(),
                        requestDto.getUris()
                )
        );
    }

    private List<ViewStats> findAllByStartEndUriUnique(ViewStatsRequestDto requestDto) {
        return mapToViewStats(
                endpointHitRepository.findAllByStartEndUriUnique(
                        requestDto.getStart(),
                        requestDto.getEnd(),
                        requestDto.getUris()
                )
        );
    }


    private List<ViewStats> mapToViewStats(List<ViewStatsProjection> viewStatsProjections) {
        return viewStatsProjections.stream()
                .map(proj ->
                        ViewStats
                                .builder()
                                .hits(proj.getHits())
                                .app(proj.getApp())
                                .uri(proj.getUri())
                                .build())
                .collect(toList());
    }
}
