package ru.practicum.ewm.main.server.user.dto.create;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
public class CreateUserRequestDto {
    @Email
    @NotBlank
    String email;

    @NotBlank
    String name;
}
