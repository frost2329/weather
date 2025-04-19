package com.frostetsky.weather.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"locations", "sessions"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "public")
public class User implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String login;
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Location> locations = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Session> sessions = new ArrayList<>();
}
