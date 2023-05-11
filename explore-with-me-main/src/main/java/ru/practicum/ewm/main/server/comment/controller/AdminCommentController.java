package ru.practicum.ewm.main.server.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.server.comment.dto.CommentDto;
import ru.practicum.ewm.main.server.comment.entity.state.CommentState;
import ru.practicum.ewm.main.server.comment.service.AdminCommentService;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
public class AdminCommentController {
    private final AdminCommentService commentService;

    @PatchMapping("/admin/comments/{commentId}/{commentState}")
    public CommentDto updateCommentState(
            @PathVariable(name = "commentId")
            Long commentId,
            @PathVariable(name = "commentState")
            CommentState commentState
    ) {
        return commentService.updateCommentState(commentId, commentState);
    }

    @DeleteMapping("/admin/comments/{commentId}")
    public ResponseEntity<Void> deleteCommentById(
            @PathVariable(name = "commentId")
            Long commentId
    ) {
        commentService.deleteCommentById(commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin/comments/{commentId}")
    public CommentDto getCommentById(
            @PathVariable(name = "commentId")
            Long commentId
    ) {
        return commentService.getCommentById(commentId);
    }

    @GetMapping("/admin/comments/pending")
    public List<CommentDto> getPendingComments() {
        return commentService.getPendingComments();
    }
}
