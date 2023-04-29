package ru.practicum.ewm.main.server.event.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.practicum.ewm.main.server.event.entity.Event;

import java.time.LocalDateTime;
import java.util.List;

@NoRepositoryBean
public interface CustomEventRepository extends JpaRepository<Event, Long> {
    List<Event> getEventsForAdmin(List<Long> users, List<String> states, List<Long> categories,
                                  LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

}

