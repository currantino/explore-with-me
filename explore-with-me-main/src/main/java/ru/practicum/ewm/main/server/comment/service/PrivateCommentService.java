package ru.practicum.ewm.main.server.comment.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.server.comment.dto.CommentDto;
import ru.practicum.ewm.main.server.comment.dto.CommentFilter;
import ru.practicum.ewm.main.server.comment.dto.CreateCommentDto;
import ru.practicum.ewm.main.server.comment.dto.UpdateCommentDto;
import ru.practicum.ewm.main.server.comment.entity.Comment;
import ru.practicum.ewm.main.server.comment.entity.state.CommentState;
import ru.practicum.ewm.main.server.comment.mapper.CommentMapper;
import ru.practicum.ewm.main.server.comment.repository.CommentRepository;
import ru.practicum.ewm.main.server.event.entity.Event;
import ru.practicum.ewm.main.server.event.entity.state.EventState;
import ru.practicum.ewm.main.server.event.repository.CustomEventRepository;
import ru.practicum.ewm.main.server.exception.*;
import ru.practicum.ewm.main.server.user.entity.User;
import ru.practicum.ewm.main.server.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static java.time.LocalDateTime.now;
import static ru.practicum.ewm.main.server.comment.entity.QComment.comment;

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
        checkThatEventIsPublished(event);
        comment.setAuthor(author);
        comment.setEvent(event);
        comment.setCreatedOn(now());
        comment.setState(CommentState.PENDING);
        Comment saved = commentRepository.save(comment);
        return commentMapper.toDto(saved);
    }

    private void checkThatEventIsPublished(Event event) {
        if (!EventState.PUBLISHED.equals(event.getState())) {
            throw new EventNotPublishedException("You cannot comment event that is not published yet.");
        }
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
    public void deleteComment(
            Long commentId,
            Long userId
    ) {
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

    public CommentDto getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .map(commentMapper::toDto)
                .orElseThrow(() -> new CommentNotFoundException("Could not find the requested comment."));
    }

    public List<CommentDto> getComments(
            Integer from,
            Integer size,
            CommentFilter filter
    ) {
        BooleanBuilder predicate = new BooleanBuilder();
        if (filter.getAuthorId() != null) {
            predicate.and(comment.author.id.eq(filter.getAuthorId()));
        }
        if (filter.getEventId() != null) {
            predicate.and(comment.event.id.eq(filter.getEventId()));
        }
        if (filter.getState() != null) {
            predicate.and(comment.state.eq(filter.getState()));
        }

        Pageable pageRequest = PageRequest.of(from, size);
        return commentRepository
                .findAll(predicate, pageRequest)
                .map(commentMapper::toDto)
                .getContent();
    }
}
