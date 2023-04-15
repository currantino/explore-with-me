package ru.practicum.viewstats;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class ViewStatsRequestDto {
    LocalDateTime start;
    LocalDateTime end;
    List<String> uris;
    Boolean unique;
}
