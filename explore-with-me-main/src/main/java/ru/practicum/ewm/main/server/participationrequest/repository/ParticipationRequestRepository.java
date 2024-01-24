package ru.practicum.ewm.main.server.participationrequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.graphql.data.GraphQlRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.server.participationrequest.dto.ParticipationRequestStatus;
import ru.practicum.ewm.main.server.participationrequest.entity.ParticipationRequest;

import java.util.List;

@GraphQlRepository
public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long>, QuerydslPredicateExecutor<ParticipationRequest> {
    @Transactional
    @Modifying
    @Query("UPDATE " +
            "   ParticipationRequest pr " +
            "SET " +
            "   pr.status = :newStatus " +
            "WHERE " +
            "   pr.event.id = :eventId" +
            "   AND pr.status = 'PENDING' ")
    int updateStatusByEventIdAndPending(
            Long eventId,
            ParticipationRequestStatus newStatus
    );

    @Query("SELECT " +
            "   pr " +
            "FROM " +
            "   ParticipationRequest pr " +
            "WHERE " +
            "   pr.event.id = :eventId")
    List<ParticipationRequest> findAllByEventId(Long eventId);
}