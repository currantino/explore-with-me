package ru.practicum.ewm.main.server.user.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.main.server.mapstruct.CentralMapperConfig;
import ru.practicum.ewm.main.server.user.dto.read.ReadUserDto;
import ru.practicum.ewm.main.server.user.entity.User;

@Mapper(config = CentralMapperConfig.class)
public interface ReadUserMapper {
    ReadUserDto toDto(User user);
}
