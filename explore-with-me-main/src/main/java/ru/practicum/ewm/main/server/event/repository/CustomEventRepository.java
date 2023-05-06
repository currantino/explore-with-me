package ru.practicum.ewm.main.server.event.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.practicum.ewm.main.server.event.dto.filter.AdminEventFilterQuery;
import ru.practicum.ewm.main.server.event.dto.filter.EventFilterQuery;
import ru.practicum.ewm.main.server.event.entity.Event;

import java.util.List;

@NoRepositoryBean
public interface CustomEventRepository extends JpaRepository<Event, Long> {

    List<Event> getEventsForAdmin(AdminEventFilterQuery filterQuery);

    List<Event> getEventsForPublic(EventFilterQuery query);
}

