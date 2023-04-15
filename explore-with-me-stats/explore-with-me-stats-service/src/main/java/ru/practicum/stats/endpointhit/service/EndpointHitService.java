package ru.practicum.stats.endpointhit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.dto.endpointhit.CreateEndPointHitDto;
import ru.practicum.stats.endpointhit.entity.EndpointHit;
import ru.practicum.stats.endpointhit.mapper.CreateEndPointHitMapper;
import ru.practicum.stats.endpointhit.repository.EndpointHitRepository;


@Service
@RequiredArgsConstructor
public class EndpointHitService {
    private final EndpointHitRepository endpointHitRepository;

    public void createEndpointHit(CreateEndPointHitDto createEndPointHitDto) {
        EndpointHit hit = CreateEndPointHitMapper.INSTANCE.toEntity(createEndPointHitDto);
        endpointHitRepository.save(hit);
    }
}
