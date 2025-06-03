package com.frostetsky.weather.db.entity;

import lombok.*;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Setter
@Getter
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
    private Timestamp expiresAt = new Timestamp(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
}
