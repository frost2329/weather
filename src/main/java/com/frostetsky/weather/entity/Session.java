package com.frostetsky.weather.entity;

import lombok.*;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Generated;

import jakarta.persistence.*;
import java.sql.Timestamp;
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
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "expires_at", insertable = false, updatable = false)
    @Generated(GenerationTime.INSERT)
    private Timestamp expiresAt;
}
