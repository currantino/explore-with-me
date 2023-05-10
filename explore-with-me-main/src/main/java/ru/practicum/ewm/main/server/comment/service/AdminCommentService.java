package ru.practicum.ewm.main.server.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.server.comment.dto.CommentDto;
import ru.practicum.ewm.main.server.comment.entity.Comment;
import ru.practicum.ewm.main.server.comment.entity.state.CommentState;
import ru.practicum.ewm.main.server.comment.mapper.CommentMapper;
import ru.practicum.ewm.main.server.comment.repository.CommentRepository;
import ru.practicum.ewm.main.server.exception.CommentNotFoundException;
import ru.practicum.ewm.main.server.exception.InvalidStateActionException;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AdminCommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Transactional
    public CommentDto updateCommentState(Long commentId, CommentState state) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Could not find the requested comment."));
        if (!CommentState.PENDING.equals(comment.getState())) {
            throw new InvalidStateActionException("You cannot change state of a comment if it is not PENDING");
        }
        comment.setState(state);
        Comment saved = commentRepository.save(comment);
        return commentMapper.toDto(saved);
    }

    public void deleteCommentById(Long commentId) {
        int deleted = commentRepository.deleteByIdAndGetNumberOfDeletedRows(commentId);
        if (deleted == 0) {
            throw new CommentNotFoundException("Could not find comment for deletion.");
        }
    }
}
