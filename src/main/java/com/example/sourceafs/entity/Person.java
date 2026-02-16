package com.example.sourceafs.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Correct auto-increment
    private Long id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(nullable = false)
    private byte[] fingerprintTemplate; // Store fingerprint template as BLOB
}
