package ru.practicum.ewm.main.server.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.server.user.dto.create.CreateUserRequestDto;
import ru.practicum.ewm.main.server.user.dto.create.CreateUserResponseDto;
import ru.practicum.ewm.main.server.user.dto.read.ReadUserDto;
import ru.practicum.ewm.main.server.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponseDto createUser(
            @Valid
            @RequestBody
            CreateUserRequestDto requestDto
    ) {
        return userService.createUser(requestDto);
    }

    @GetMapping()
    public List<ReadUserDto> getUsers(
            @RequestParam(
                    name = "ids",
                    required = false
            )
            List<Long> ids,
            @RequestParam(
                    name = "from",
                    defaultValue = "0"
            )
            Integer from,
            @RequestParam(
                    name = "size",
                    defaultValue = "10"
            )
            Integer size
    ) {
        return userService.getUsers(ids, from, size).getContent();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUserById(
            @PathVariable
            Long id
    ) {
        userService.removeUserById(id);
        return ResponseEntity
                .noContent()
                .build();
    }

}
