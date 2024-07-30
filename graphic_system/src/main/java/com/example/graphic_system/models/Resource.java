package com.example.graphic_system.models;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String description;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
}