package com.frostetsky.weather.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "locations",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "user_city_unique",
                        columnNames = {"name", "user_id"}
                )
        })
public class Location implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double latitude;
    private double longitude;
    @Column(name = "user_id")
    private Long userId;
}
