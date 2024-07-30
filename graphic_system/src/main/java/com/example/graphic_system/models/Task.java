package com.example.graphic_system.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String goal;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @NotNull
    private Integer priority;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", goal='" + goal + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}