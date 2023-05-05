package ru.practicum.ewm.main.server.event.repository;


import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import ru.practicum.ewm.main.server.event.dto.EventSort;
import ru.practicum.ewm.main.server.event.entity.Event;
import ru.practicum.ewm.main.server.event.entity.state.EventState;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;

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
            List<EventState> states,
            List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Integer from,
            Integer size
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> criteriaQuery = cb.createQuery(Event.class);
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
                    cb.greaterThanOrEqualTo(
                            root.get("eventDate"),
                            rangeStart
                    )
            );

        }

        if (rangeEnd != null) {
            predicates.add(
                    cb.lessThanOrEqualTo(
                            root.get("eventDate"),
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

    @Override
    public List<Event> getEventsForPublic(
            String searchQuery,
            List<Long> categoryIds,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Boolean onlyAvailable,
            EventSort sort,
            Integer from,
            Integer size
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> criteriaQuery = cb.createQuery(Event.class);
        Root<Event> root = criteriaQuery.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();

        Predicate onlyPublished = cb.equal(
                root.get("state"), EventState.PUBLISHED
        );

        predicates.add(onlyPublished);

        if (searchQuery != null && !searchQuery.isBlank()) {
            String searchQueryLowerCase = searchQuery.toLowerCase();

            Expression<String> concatenated = cb.concat(
                    root.get("description"),
                    cb.concat(
                            root.get("title"),
                            root.get("annotation")
                    )
            );

            predicates.add(
                    cb.like(
                            cb.lower(concatenated),
                            "%".concat(searchQueryLowerCase).concat("%")
                    )
            );
        }

        if (categoryIds != null && !categoryIds.isEmpty()) {
            predicates.add(
                    root.get("category").in(categoryIds)
            );
        }

        if (paid != null) {
            predicates.add(
                    cb.equal(
                            root.get("paid"),
                            paid
                    )
            );
        }

        if (onlyAvailable) {
            Predicate withParticipationLimit = cb.lessThan(
                    root.get("confirmedRequests"),
                    root.get("participationLimit")
            );
            Predicate withoutParticipationLimit = cb.equal(
                    root.get("participationLimit"), 0L
            );

            predicates.add(
                    cb.or(
                            withParticipationLimit,
                            withoutParticipationLimit
                    )
            );
        }

        if (rangeStart == null || rangeEnd == null) {
            predicates.add(
                    cb.greaterThanOrEqualTo(
                            root.get("eventDate"),
                            now()
                    )
            );
        }

        if (rangeStart != null) {
            predicates.add(
                    cb.greaterThanOrEqualTo(
                            root.get("eventDate"),
                            rangeStart
                    )
            );
        }

        if (rangeEnd != null) {
            predicates.add(
                    cb.lessThanOrEqualTo(
                            root.get("eventDate"),
                            rangeEnd
                    )
            );
        }


        Order sortingOrder;

        switch (sort) {
            case EVENT_DATE:
                sortingOrder = cb.asc(root.get("eventDate"));
                break;
            case VIEWS:
                sortingOrder = cb.desc(root.get("views"));
                break;
            default:
                sortingOrder = cb.desc(root.get("views"));
        }

        criteriaQuery
                .where(predicates.toArray(Predicate[]::new))
                .orderBy(sortingOrder);

        TypedQuery<Event> query = entityManager.createQuery(criteriaQuery)
                .setFirstResult(from)
                .setMaxResults(size);
        return query.getResultList();
    }


}