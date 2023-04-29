package ru.practicum.ewm.main.server.event.repository;


import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import ru.practicum.ewm.main.server.event.entity.Event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomEventRepositoryImpl extends SimpleJpaRepository<Event, Long> implements CustomEventRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public CustomEventRepositoryImpl(
            JpaEntityInformation<Event, ?> entityInformation,
            EntityManager entityManager
    ) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public List<Event> getEventsForAdmin(
            List<Long> users,
            List<String> states,
            List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Integer from,
            Integer size
    ) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);
        Root<Event> root = criteriaQuery.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();

        if (users != null && !users.isEmpty()) {
            predicates.add(
                    root.get("initiator").in(users)
            );
        }

        if (states != null && !states.isEmpty()) {
            predicates.add(
                    root.get("state").in(states)
            );
        }

        if (categories != null && !categories.isEmpty()) {
            predicates.add(
                    root.get("category").in(categories)
            );
        }

        if (rangeStart != null) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(
                            root.get("event_date"),
                            rangeStart
                    )
            );

        }

        if (rangeEnd != null) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(
                            root.get("event_date"),
                            rangeEnd
                    )
            );
        }

        criteriaQuery.where(predicates.toArray(Predicate[]::new));
        TypedQuery<Event> query = entityManager.createQuery(criteriaQuery)
                .setFirstResult(from)
                .setMaxResults(size);
        return query.getResultList();
    }

}