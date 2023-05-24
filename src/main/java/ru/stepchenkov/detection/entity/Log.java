package ru.stepchenkov.detection.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "time", nullable = false)
    private LocalDateTime time;
    @Column(name = "info", nullable = false)
    private String info;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User personId;
}
