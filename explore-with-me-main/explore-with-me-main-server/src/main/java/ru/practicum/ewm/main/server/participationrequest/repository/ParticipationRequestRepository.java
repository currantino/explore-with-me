package ru.practicum.ewm.main.server.participationrequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.main.server.participationrequest.entity.ParticipationRequest;

import java.util.List;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {
    @Query("SELECT " +
            "   pr " +
            "FROM " +
            "   ParticipationRequest pr " +
            "WHERE " +
            "   pr.event.id = :eventId")
    List<ParticipationRequest> findAllByEventId(Long eventId);
}