package ru.practicum.ewm.main.server.event.controller;

import org.mapstruct.Mapper;
import ru.practicum.ewm.main.server.mapstruct.CentralMapperConfig;
import ru.practicum.ewm.main.server.user.entity.User;

@Mapper(config = CentralMapperConfig.class)
public interface UserMapper {

    UserShortDto toDto(User user);

}