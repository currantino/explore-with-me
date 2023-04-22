package ru.practicum.ewm.main.server.user.dto.read;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.practicum.ewm.main.server.user.entity.User;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface ReadUserMapper {
    ReadUserDto toDto(User user);
}
