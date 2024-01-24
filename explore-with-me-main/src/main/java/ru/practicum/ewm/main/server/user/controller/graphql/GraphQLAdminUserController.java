package ru.practicum.ewm.main.server.user.controller.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.practicum.ewm.main.server.user.dto.create.CreateUserRequestDto;
import ru.practicum.ewm.main.server.user.dto.create.CreateUserResponseDto;
import ru.practicum.ewm.main.server.user.dto.read.ReadUserDto;
import ru.practicum.ewm.main.server.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GraphQLAdminUserController {
    private final UserService userService;

    @QueryMapping
    public List<ReadUserDto> getUsers(
            @Argument
            List<Long> ids,
            @Argument
            Integer from,
            @Argument
            Integer size
    ) {
        return userService.getUsers(ids, from, size).getContent();
    }

    @MutationMapping
    public void removeUser(
            @Argument
            Long id
    ) {
        userService.removeUserById(id);
    }

    @MutationMapping
    public CreateUserResponseDto createUser(
            @Argument
            @Valid
            CreateUserRequestDto user
    ) {
        return userService.createUser(user);
    }

}
