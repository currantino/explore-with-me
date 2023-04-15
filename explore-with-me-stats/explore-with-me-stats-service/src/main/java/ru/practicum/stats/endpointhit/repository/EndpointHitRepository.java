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
    @Query("select " +
           "    count(distinct(hit.ip)) as hits, " +
           "    hit.app as app, " +
           "    hit.uri as uri " +
           "from " +
           "    EndpointHit hit " +
           "where " +
           "    hit.timestamp > ?1 and hit.timestamp < ?2 and hit.uri = ?3 " +
           "group by " +
           "    hit.uri, hit.ip")
    ViewStatsProjection findByStartEndUriUnique(LocalDateTime start, LocalDateTime end, String uri);

    @Query("select " +
           "    count(hit) as hits, " +
           "    hit.app as app, " +
           "    hit.uri as uri " +
           "from " +
           "    EndpointHit hit " +
           "where " +
           "    (hit.timestamp between ?1 and ?2) and hit.uri = ?3")
    ViewStatsProjection findByStartEndUri(LocalDateTime start, LocalDateTime end, String uri);

    @Query("select " +
           "    count(hit) as hits, " +
           "    hit.app as app, " +
           "    hit.uri as uri " +
           "from " +
           "    EndpointHit hit " +
           "where " +
           "    hit.timestamp between ?1 and ?2 " +
           "group by " +
           "    hit.uri " +
           "order by " +
           "    hits desc")
    List<ViewStatsProjection> findAllByStartEnd(LocalDateTime start, LocalDateTime end);

    @Query("select " +
           "    count(distinct(hit.ip)) as hits, " +
           "    hit.app as app, " +
           "    hit.uri as uri " +
           "from " +
           "    EndpointHit hit " +
           "group by " +
           "    hit.uri, hit.ip " +
           "order by " +
           "    hits desc")
    List<ViewStatsProjection> findAllByStartEndUnique(LocalDateTime start, LocalDateTime end);


}
