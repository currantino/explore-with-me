package ru.practicum.ewm.main.server.participationrequest.entity;

import lombok.*;
import ru.practicum.ewm.main.server.event.entity.Event;
import ru.practicum.ewm.main.server.participationrequest.dto.ParticipationRequestStatus;
import ru.practicum.ewm.main.server.user.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "participation_request")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_request_id")
    private Long id;

    @Column(name = "participation_request_created")
    private LocalDateTime created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participation_request_event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participation_request_requester_id")
    private User requester;

    @Column(name = "participation_request_status")
    @Enumerated(EnumType.STRING)
    private ParticipationRequestStatus status;

}
