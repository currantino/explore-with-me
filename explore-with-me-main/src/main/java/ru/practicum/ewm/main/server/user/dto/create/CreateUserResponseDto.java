package ru.practicum.ewm.main.server.user.dto.create;

import lombok.Value;

@Value
public class CreateUserResponseDto {
    Long id;
    String email;
    String name;
}
