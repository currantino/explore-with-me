package ru.practicum.ewm.main.server.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.main.server.comment.entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT " +
            "    c " +
            "FROM " +
            "   Comment c " +
            "WHERE " +
            "   c.event.id = :eventId " +
            "ORDER BY " +
            "   c.createdOn DESC")
    List<Comment> findAllByEventId(Long eventId);

    @Modifying
    @Query("DELETE " +
            "FROM " +
            "   Comment c " +
            "WHERE " +
            "   c.id = :commentId")
    int deleteByIdAndGetNumberOfDeletedRows(Long commentId);
}