package ru.practicum.ewm.main.server.event.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.practicum.ewm.main.server.event.dto.EventSort;
import ru.practicum.ewm.main.server.event.entity.Event;
import ru.practicum.ewm.main.server.event.state.EventState;

import java.time.LocalDateTime;
import java.util.List;

@NoRepositoryBean
public interface CustomEventRepository extends JpaRepository<Event, Long> {

    List<Event> getEventsForAdmin(
            List<Long> users,
            List<EventState> states,
            List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Integer from,
            Integer size
    );

    List<Event> getEventsForPublic(
            String searchQuery,
            List<Long> categoryIds,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Boolean onlyAvailable,
            EventSort sort,
            Integer from,
            Integer size
    );
}

