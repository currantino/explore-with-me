package ru.practicum.ewm.main.server.participationrequest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.main.server.config.mapstruct.CentralMapperConfig;
import ru.practicum.ewm.main.server.participationrequest.dto.ParticipationRequestDto;
import ru.practicum.ewm.main.server.participationrequest.entity.ParticipationRequest;

@Mapper(config = CentralMapperConfig.class)
public interface ParticipationRequestMapper {
    @Mapping(target = "event", source = "event.id")
    @Mapping(target = "requester", source = "requester.id")
    ParticipationRequestDto toDto(ParticipationRequest participationRequest);

}