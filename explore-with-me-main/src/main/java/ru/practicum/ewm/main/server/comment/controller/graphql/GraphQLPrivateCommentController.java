package ru.practicum.ewm.main.server.comment.controller.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.practicum.ewm.main.server.comment.dto.CommentDto;
import ru.practicum.ewm.main.server.comment.dto.CommentFilter;
import ru.practicum.ewm.main.server.comment.dto.CreateCommentDto;
import ru.practicum.ewm.main.server.comment.dto.UpdateCommentDto;
import ru.practicum.ewm.main.server.comment.service.PrivateCommentService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GraphQLPrivateCommentController {
    private final PrivateCommentService commentService;

    @MutationMapping
    public CommentDto createComment(
            @Argument
            Long eventId,
            @Argument
            Long authorId,
            @Argument
            @Valid
            CreateCommentDto comment
    ) {
        return commentService.createComment(
                eventId,
                authorId,
                comment
        );
    }

    @MutationMapping
    public CommentDto updateComment(
            @Argument
            Long userId,
            @Argument
            Long commentId,
            @Argument
            @Valid
            UpdateCommentDto comment
    ) {
        return commentService.updateComment(
                userId,
                commentId,
                comment
        );
    }

    @MutationMapping
    public void deleteComment(
            @Argument
            Long userId,
            @Argument
            Long commentId
    ) {
        commentService.deleteComment(commentId, userId);
    }

    @QueryMapping
    public List<CommentDto> comments(
            @Argument
            Integer from,
            @Argument
            Integer size,
            @Argument
            CommentFilter filter
    ) {
        return commentService.getComments(from, size, filter);
    }


}
