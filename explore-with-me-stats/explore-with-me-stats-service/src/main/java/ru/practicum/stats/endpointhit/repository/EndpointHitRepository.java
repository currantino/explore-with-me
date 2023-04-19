package ru.practicum.stats.endpointhit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.stats.endpointhit.entity.EndpointHit;
import ru.practicum.stats.viewstats.projections.ViewStatsProjection;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {
    @Query("SELECT " +
           "    COUNT(hit) AS hits, " +
           "    hit.app AS app, " +
           "    hit.uri AS uri " +
           "FROM " +
           "    EndpointHit AS hit " +
           "WHERE " +
           "    hit.timestamp BETWEEN ?1 AND ?2 " +
           "GROUP BY " +
           "    hit.app," +
           "    hit.uri " +
           "ORDER BY " +
           "    hits DESC")
    List<ViewStatsProjection> findAllByStartEnd(LocalDateTime start, LocalDateTime end);

    @Query("SELECT " +
           "    COUNT(distinct hit.ip) AS hits, " +
           "    hit.app AS app, " +
           "    hit.uri AS uri " +
           "FROM " +
           "    EndpointHit AS hit " +
           "WHERE " +
           "    hit.timestamp BETWEEN ?1 AND ?2 " +
           "GROUP BY " +
           "    hit.app," +
           "    hit.uri " +
           "ORDER BY " +
           "    hits DESC")
    List<ViewStatsProjection> findAllByStartEndUnique(LocalDateTime start, LocalDateTime end);

    @Query("SELECT " +
           "    COUNT(hit) AS hits, " +
           "    hit.app AS app, " +
           "    hit.uri AS uri " +
           "FROM " +
           "    EndpointHit AS hit " +
           "WHERE " +
           "    (hit.timestamp BETWEEN ?1 AND ?2) " +
           "    AND hit.uri IN ?3 " +
           "GROUP BY " +
           "    hit.app," +
           "    hit.uri " +
           "ORDER BY " +
           "    hits DESC")
    List<ViewStatsProjection> findAllByStartEndUri(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT " +
           "    COUNT(distinct hit.ip) AS hits, " +
           "    hit.app AS app, " +
           "    hit.uri AS uri " +
           "FROM " +
           "    EndpointHit AS hit " +
           "WHERE " +
           "    (hit.timestamp BETWEEN ?1 AND ?2) " +
           "    AND hit.uri IN ?3 " +
           "GROUP BY " +
           "    hit.app," +
           "    hit.uri " +
           "ORDER BY " +
           "    hits DESC")
    List<ViewStatsProjection> findAllByStartEndUriUnique(LocalDateTime start, LocalDateTime end, List<String> uris);


}
