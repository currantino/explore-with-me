package ru.practicum.ewm.main.server.user.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.practicum.ewm.main.server.user.dto.create.CreateUserRequestDto;
import ru.practicum.ewm.main.server.user.dto.create.CreateUserResponseDto;
import ru.practicum.ewm.main.server.user.entity.User;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface CreateUserMapper {

    CreateUserResponseDto toResponseDto(User user);

    User toEntity(CreateUserRequestDto dto);

}
