package com.frostetsky.weather.db.entity;

import lombok.*;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Setter
@Getter
@ToString(exclude = "user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sessions")
public class Session implements BaseEntity<UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "user_id")
    private Long userId;
    @Builder.Default
    @Column(name = "expires_at")
    private Timestamp expiresAt = Timestamp.from(Instant.now().plus(1, ChronoUnit.DAYS));
}
