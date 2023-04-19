package ru.practicum.stats.endpointhit.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "endpoint_hit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "endpoint_hit_id")
    private Long id;
    @Column(name = "app_name")
    private String app;

    @Column(name = "uri")
    private String uri;
    @Column(name = "client_ip_address")
    private String ip;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}
