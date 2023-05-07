package ru.practicum.ewm.main.server.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.main.server.location.entity.Location;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
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
