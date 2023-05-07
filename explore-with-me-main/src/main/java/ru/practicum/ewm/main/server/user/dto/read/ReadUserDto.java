package ru.practicum.ewm.main.server.user.dto.read;

import lombok.Value;

@Value
public class ReadUserDto {
    Long id;
    String name;
    String email;
}
