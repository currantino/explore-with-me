package ru.practicum.stats.viewstats.projections;

public interface ViewStatsProjection {
    String getApp();

    String getUri();

    Long getHits();
}
