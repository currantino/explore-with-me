package ru.practicum.ewm.main.server.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.server.category.entity.Category;
import ru.practicum.ewm.main.server.category.repository.CategoryRepository;
import ru.practicum.ewm.main.server.event.dto.CreateEventRequestDto;
import ru.practicum.ewm.main.server.event.dto.EventFullDto;
import ru.practicum.ewm.main.server.event.dto.mapper.EventMapper;
import ru.practicum.ewm.main.server.event.entity.Event;
import ru.practicum.ewm.main.server.event.repository.CustomEventRepository;
import ru.practicum.ewm.main.server.exception.CategoryNotFoundException;
import ru.practicum.ewm.main.server.exception.UserNotFoundException;
import ru.practicum.ewm.main.server.location.dto.mapper.LocationMapper;
import ru.practicum.ewm.main.server.location.entity.Location;
import ru.practicum.ewm.main.server.location.repository.LocationRepository;
import ru.practicum.ewm.main.server.user.entity.User;
import ru.practicum.ewm.main.server.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventMapper eventMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final CustomEventRepository eventRepository;

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
        return eventMapper.toFullDto(eventRepository.save(event));

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
}
