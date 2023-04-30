package ru.practicum.ewm.main.server.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.main.server.event.entity.Event;

@Repository
public interface EventRepository extends CustomEventRepository {
    @Query("SELECT " +
            "   e " +
            "FROM " +
            "   Event e " +
            "WHERE " +
            "   e.initiator.id = :userId")
    Page<Event> findAllByInitiator(Long userId, Pageable pageRequest);

}
