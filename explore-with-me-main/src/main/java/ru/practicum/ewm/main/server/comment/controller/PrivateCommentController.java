package ru.practicum.ewm.main.server.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.server.comment.dto.CommentDto;
import ru.practicum.ewm.main.server.comment.dto.CreateCommentDto;
import ru.practicum.ewm.main.server.comment.dto.UpdateCommentDto;
import ru.practicum.ewm.main.server.comment.service.PrivateCommentService;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping
public class PrivateCommentController {
    private final PrivateCommentService commentService;

    @ResponseStatus(CREATED)
    @PostMapping("/users/{authorId}/events/{eventId}/comments")
    public CommentDto createComment(
            @PathVariable(name = "authorId")
            Long authorId,
            @PathVariable(name = "eventId")
            Long eventId,
            @Valid
            @RequestBody
            CreateCommentDto commentDto
    ) {
        return commentService.createComment(
                eventId,
                authorId,
                commentDto
        );
    }

    @PatchMapping("/users/{userId}/comments/{commentId}")
    public CommentDto updateComment(
            @PathVariable(name = "userId")
            Long requesterId,
            @PathVariable(name = "commentId")
            Long commentId,
            @Valid
            @RequestBody
            UpdateCommentDto commentDto
    ) {
        return commentService.updateComment(
                requesterId,
                commentId,
                commentDto
        );
    }

    @GetMapping("/users/comments/{commentId}")
    public CommentDto getCommentById(
            @PathVariable(name = "commentId")
            Long commentId
    ) {
        return commentService.getCommentById(commentId);
    }

    @DeleteMapping("/users/{userId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable(name = "userId")
            Long userId,
            @PathVariable(name = "commentId")
            Long commentId
    ) {
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.noContent()
                .build();
    }


}
