package ru.practicum.ewm.main.server.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.server.comment.dto.CommentDto;
import ru.practicum.ewm.main.server.comment.dto.CreateCommentDto;
import ru.practicum.ewm.main.server.comment.dto.UpdateCommentDto;
import ru.practicum.ewm.main.server.comment.entity.Comment;
import ru.practicum.ewm.main.server.comment.entity.state.CommentState;
import ru.practicum.ewm.main.server.comment.mapper.CommentMapper;
import ru.practicum.ewm.main.server.comment.repository.CommentRepository;
import ru.practicum.ewm.main.server.event.entity.Event;
import ru.practicum.ewm.main.server.event.repository.CustomEventRepository;
import ru.practicum.ewm.main.server.exception.CommentNotAccessibleException;
import ru.practicum.ewm.main.server.exception.CommentNotFoundException;
import ru.practicum.ewm.main.server.exception.EventNotFoundException;
import ru.practicum.ewm.main.server.exception.UserNotFoundException;
import ru.practicum.ewm.main.server.user.entity.User;
import ru.practicum.ewm.main.server.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Objects;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class PrivateCommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final CustomEventRepository eventRepository;

    @Transactional
    public CommentDto createComment(
            Long eventId,
            Long authorId,
            CreateCommentDto commentDto
    ) {
        Comment comment = commentMapper.toEntity(commentDto);
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new UserNotFoundException("Could not find comment author."));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Could not find commented event."));
        comment.setAuthor(author);
        comment.setEvent(event);
        comment.setCreatedOn(now());
        comment.setState(CommentState.PENDING);
        Comment saved = commentRepository.save(comment);
        return commentMapper.toDto(saved);
    }

    @Transactional
    public CommentDto updateComment(
            Long requesterId,
            Long commentId,
            UpdateCommentDto commentDto
    ) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Could not find comment to update."));
        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new UserNotFoundException("Could not find the requester."));
        checkThatRequesterIsAuthorOfComment(requester, comment);
        Comment updated = commentMapper.partialUpdate(commentDto, comment);
        updated.setState(CommentState.PENDING);
        Comment saved = commentRepository.save(updated);
        return commentMapper.toDto(saved);
    }


    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Could not find comment to delete"));
        User requester = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Could not find the requester."));
        checkThatRequesterIsAuthorOfComment(requester, comment);
        commentRepository.deleteById(commentId);
    }

    private void checkThatRequesterIsAuthorOfComment(
            User requester,
            Comment comment
    ) {
        Long requesterId = requester.getId();
        Long commentAuthorId = comment.getAuthor().getId();
        if (!Objects.equals(commentAuthorId, requesterId)) {
            throw new CommentNotAccessibleException("You cannot delete comments of other users.");
        }
    }
}
