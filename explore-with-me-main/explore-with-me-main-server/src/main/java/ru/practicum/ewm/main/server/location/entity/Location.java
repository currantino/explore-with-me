package ru.practicum.ewm.main.server.location.entity;

import javax.persistence.*;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue
    @Column(name = "location_id")
    private Long id;
    @Column(name = "latitude")
    private Double lat;
    @Column(name = "longitude")
    private Double lon;
}
