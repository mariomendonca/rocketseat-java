package com.example.todolist.task;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String description;
    @Column(length = 50)
    private String title;
    private String priority;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private UUID userId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setTitle(String title) throws Exception {
        if (title.length() > 50) {
            throw new Exception("Title must be max 50");
        }
        this.title = title;
    }
}
