package ru.practicum.ewm.main.server.comment.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.practicum.ewm.main.server.comment.entity.state.CommentState;
import ru.practicum.ewm.main.server.event.entity.Event;
import ru.practicum.ewm.main.server.user.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comment")
@RequestMapping
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    private Event event;

    @ManyToOne
    private User author;

    @Column(name = "comment_text")
    private String text;

    @Column(name = "comment_created_on")
    private LocalDateTime createdOn;

    @Enumerated(EnumType.STRING)
    private CommentState state;

}
