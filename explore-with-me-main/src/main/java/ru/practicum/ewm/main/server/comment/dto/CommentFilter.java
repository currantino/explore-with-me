package ru.practicum.ewm.main.server.comment.dto;

import lombok.Value;
import ru.practicum.ewm.main.server.comment.entity.state.CommentState;

@Value
public class CommentFilter {
    Long authorId;
    Long eventId;
    CommentState state;
}
