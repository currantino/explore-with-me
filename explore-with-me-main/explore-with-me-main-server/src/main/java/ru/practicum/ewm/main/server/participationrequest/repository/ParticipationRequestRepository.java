package ru.practicum.ewm.main.server.participationrequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main.server.participationrequest.entity.ParticipationRequest;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {
}