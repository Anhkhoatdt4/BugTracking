package com.devteria.bugtracking.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
public class Comment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String text;

        @Transient
        private String timeElapsed;

        public String getTimeElapsed() {
                return getTimeElapsedSinceCreation(this.createdAt);
        }
        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @ManyToOne
        @JoinColumn(name = "bug_id", nullable = false)
        private Bug bug;

        @ManyToOne
        @JoinColumn(name = "project_id", nullable = false)
        private Project project;

        @Column(nullable = false, updatable = false)
        private LocalDateTime createdAt = LocalDateTime.now();

        private String getTimeElapsedSinceCreation(LocalDateTime createdAt) {
                LocalDateTime now = LocalDateTime.now();
                Duration duration = Duration.between(createdAt, now);

                long seconds = duration.getSeconds();
                long minutes = seconds / 60;
                long hours = minutes / 60;
                long days = hours / 24;

                if (days > 0) {
                        return days + " days ago";
                } else if (hours > 0) {
                        return hours + " hours ago";
                } else if (minutes > 0) {
                        return minutes + " minutes ago";
                } else {
                        return seconds + " seconds ago";
                }
        }
}