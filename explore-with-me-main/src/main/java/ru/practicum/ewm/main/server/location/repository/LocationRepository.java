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
                    "   l.lon = :lon " +
                    "   AND l.lat = :lat"
    )
    Optional<Location> findByLonAndLat(Double lon, Double lat);
}
