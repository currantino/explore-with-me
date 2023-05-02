package ru.practicum.ewm.main.server.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query("SELECT " +
            "   e.initiator.id " +
            "FROM " +
            "   Event e " +
            "WHERE " +
            "   e.id = :eventId")
    Long findInitiatorIdByEventId(Long eventId);

    @Modifying
    @Query("UPDATE " +
            "   Event e " +
            "SET " +
            "   e.views = e.views + 1 " +
            "WHERE " +
            "   e.id IN :ids")
    void addViewByIdIn(Iterable<Long> ids);

    @Modifying
    @Query("UPDATE " +
            "   Event e " +
            "SET " +
            "   e.views = e.views + 1 " +
            "WHERE " +
            "   e.id = :eventId")
    void addViewById(Long eventId);
}
