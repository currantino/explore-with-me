package ru.practicum.ewm.main.server.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.server.user.dto.create.CreateUserRequestDto;
import ru.practicum.ewm.main.server.user.dto.create.CreateUserResponseDto;
import ru.practicum.ewm.main.server.user.dto.read.ReadUserDto;
import ru.practicum.ewm.main.server.user.dto.read.ReadUserMapper;
import ru.practicum.ewm.main.server.user.entity.User;
import ru.practicum.ewm.main.server.user.mapper.CreateUserMapper;
import ru.practicum.ewm.main.server.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CreateUserMapper createUserMapper;
    private final ReadUserMapper readUserMapper;

    public CreateUserResponseDto createUser(CreateUserRequestDto requestDto) {
        User user = createUserMapper.toEntity(requestDto);
        userRepository.save(user);
        return createUserMapper.toResponseDto(user);
    }

    public void removeUserById(Long id) {
        userRepository.deleteById(id);
    }

    public Page<ReadUserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        Pageable pageRequest = PageRequest.of(from, size);
        if (ids == null) {
            return userRepository.findAll(pageRequest)
                    .map(readUserMapper::toDto);
        }
        return userRepository.findAllByIdIn(ids, pageRequest)
                .map(readUserMapper::toDto);
    }
}
