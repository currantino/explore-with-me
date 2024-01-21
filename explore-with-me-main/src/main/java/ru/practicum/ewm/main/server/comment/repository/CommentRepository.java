package ru.practicum.ewm.main.server.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.graphql.data.GraphQlRepository;
import ru.practicum.ewm.main.server.comment.entity.Comment;
import ru.practicum.ewm.main.server.comment.entity.state.CommentState;

import java.util.List;

@GraphQlRepository
public interface CommentRepository extends JpaRepository<Comment, Long>, QuerydslPredicateExecutor<Comment> {

    @Modifying
    @Query("DELETE " +
            "FROM " +
            "   Comment c " +
            "WHERE " +
            "   c.id = :commentId")
    int deleteByIdAndGetNumberOfDeletedRows(Long commentId);

    @Query("SELECT " +
            "   c " +
            "FROM" +
            "   Comment c " +
            "WHERE " +
            "   c.state = :commentState " +
            "ORDER BY " +
            "   c.createdOn ASC")
    List<Comment> findAllByState(CommentState commentState);
}
