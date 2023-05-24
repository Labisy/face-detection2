package ru.stepchenkov.detection.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "info")
public class Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "service_number")
    private User user;
    @OneToOne
    @JoinColumn(name = "dep_id")
    private Department dep;
    @OneToOne
    @JoinColumn(name = "photo_id")
    private Photo photo;
    @OneToMany
    @JoinColumn(name = "time_id")
    @ToString.Exclude
    private List<Time> time;
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;
}
