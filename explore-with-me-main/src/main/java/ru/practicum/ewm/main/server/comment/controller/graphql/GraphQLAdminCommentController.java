package ru.practicum.ewm.main.server.comment.controller.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import ru.practicum.ewm.main.server.comment.dto.CommentDto;
import ru.practicum.ewm.main.server.comment.entity.state.CommentState;
import ru.practicum.ewm.main.server.comment.service.AdminCommentService;

import java.util.List;

@Validated
@Controller
@RequiredArgsConstructor
public class GraphQLAdminCommentController {
    private final AdminCommentService commentService;

    @MutationMapping
    public CommentDto updateCommentState(
            @Argument
            Long commentId,
            @Argument
            CommentState commentState
    ) {
        return commentService.updateCommentState(commentId, commentState);
    }

    @MutationMapping
    public void deleteCommentById(
            @Argument
            Long commentId
    ) {
        commentService.deleteCommentById(commentId);
    }

    @QueryMapping
    public List<CommentDto> getPendingComments() {
        return commentService.getPendingComments();
    }
}
