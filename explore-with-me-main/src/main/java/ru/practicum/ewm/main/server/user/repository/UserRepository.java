package ru.practicum.ewm.main.server.user.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.main.server.participationrequest.entity.ParticipationRequest;
import ru.practicum.ewm.main.server.user.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAllByIdIn(List<Long> ids, Pageable pageable);

    @Query("SELECT " +
            "   pr " +
            "FROM " +
            "   ParticipationRequest  pr " +
            "WHERE " +
            "   pr.requester.id = :userId " +
            "ORDER BY " +
            "   pr.event.eventDate ASC ")
    List<ParticipationRequest> findAllByRequesterId(Long userId);
}
