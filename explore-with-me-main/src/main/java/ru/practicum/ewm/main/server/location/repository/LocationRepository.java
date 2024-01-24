package ru.practicum.ewm.main.server.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.graphql.data.GraphQlRepository;
import ru.practicum.ewm.main.server.location.entity.Location;

import java.util.Optional;

@GraphQlRepository
public interface LocationRepository extends JpaRepository<Location, Long>, QuerydslPredicateExecutor<Location> {
    @Query(
            "SELECT " +
            "   l " +
            "FROM " +
            "   Location l " +
            "WHERE " +
            "   ABS(l.lon - :lon) < 0.0001 " +
            "   AND ABS(l.lat - :lat) < 0.0001"
    )
    Optional<Location> findByLonAndLat(Double lon, Double lat);
}
