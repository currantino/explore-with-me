package ru.practicum.ewm.main.server.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.server.category.entity.Category;
import ru.practicum.ewm.main.server.category.repository.CategoryRepository;
import ru.practicum.ewm.main.server.event.controller.EventShortDto;
import ru.practicum.ewm.main.server.event.dto.CreateEventRequestDto;
import ru.practicum.ewm.main.server.event.dto.EventFullDto;
import ru.practicum.ewm.main.server.event.dto.mapper.EventMapper;
import ru.practicum.ewm.main.server.event.entity.Event;
import ru.practicum.ewm.main.server.event.repository.EventRepository;
import ru.practicum.ewm.main.server.exception.CategoryNotFoundException;
import ru.practicum.ewm.main.server.exception.EventNotAccessibleException;
import ru.practicum.ewm.main.server.exception.EventNotFoundException;
import ru.practicum.ewm.main.server.exception.UserNotFoundException;
import ru.practicum.ewm.main.server.location.dto.mapper.LocationMapper;
import ru.practicum.ewm.main.server.location.entity.Location;
import ru.practicum.ewm.main.server.location.repository.LocationRepository;
import ru.practicum.ewm.main.server.user.entity.User;
import ru.practicum.ewm.main.server.user.repository.UserRepository;

import java.util.List;
import java.util.Objects;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventMapper eventMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final EventRepository eventRepository;

    public EventFullDto createEvent(
            Long userId,
            CreateEventRequestDto eventDto
    ) {
        User initiator = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Could not find the requested user."));
        Category category = categoryRepository.findById(eventDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException("Could not find the requested category."));
        Location location = createIfNotExists(
                locationMapper.toEntity(
                        eventDto.getLocation()
                )
        );

        Event event = eventMapper.toEntity(eventDto);
        event.setInitiator(initiator);
        event.setCategory(category);
        event.setLocation(location);
        event.setCreatedOn(now());
        Event saved = eventRepository.save(event);
        return eventMapper.toFullDto(saved);

    }

    private Location createIfNotExists(Location location) {
        return locationRepository
                .findByLonAndLat(
                        location.getLon(),
                        location.getLat()
                )
                .orElse(
                        locationRepository
                                .saveAndFlush(location)
                );
    }

    public List<EventShortDto> getEventsByUserId(
            Long userId,
            Integer from,
            Integer size
    ) {
        Pageable pageRequest = PageRequest.of(from, size);
        return eventRepository
                .findAllByInitiator(userId, pageRequest)
                .map(eventMapper::toShortDto)
                .getContent();
    }

    public EventFullDto getEventByUserIdAndEventId(Long userId, Long eventId) {
        return eventRepository
                .findById(eventId)
                .map(e -> {
                            checkIfRequesterIsInitiatorOfEvent(e, userId);
                            return eventMapper.toFullDto(e);
                        }
                )
                .orElseThrow(() -> new EventNotFoundException("Could not find the requested event."));
    }

    private void checkIfRequesterIsInitiatorOfEvent(Event event, Long requesterId) {
        if (!Objects.equals(event.getInitiator().getId(), requesterId)) {
            throw new EventNotAccessibleException("You do not have access to the requested event.");
        }
    }

    public EventFullDto patchEvent(Long eventId, Long userId) {
        Event event = eventRepository
                .findById(eventId)
                .map(e -> {
                            checkIfRequesterIsInitiatorOfEvent(e, userId);
                            return e;
                        }
                )
                .orElseThrow(() -> new EventNotFoundException("Could not find the requested event."));

    }
}
