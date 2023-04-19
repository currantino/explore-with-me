package ru.practicum.stats.endpointhit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.ewm.stats.dto.endpointhit.CreateEndPointHitDto;
import ru.practicum.stats.endpointhit.entity.EndpointHit;

@Mapper
public interface CreateEndPointHitMapper {
    CreateEndPointHitMapper INSTANCE = Mappers.getMapper(CreateEndPointHitMapper.class);

    EndpointHit toEntity(CreateEndPointHitDto dto);

}
