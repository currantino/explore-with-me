package ru.practicum.ewm.main.server.comment.dto;

import lombok.Value;
import ru.practicum.ewm.main.server.comment.entity.state.CommentState;
import ru.practicum.ewm.main.server.user.dto.read.UserShortDto;

import java.time.LocalDateTime;

@Value
public class CommentDto {
    Long id;
    Long eventId;
    UserShortDto author;
    String text;
    LocalDateTime createdOn;
    CommentState state;
}
