package com.devteria.bugtracking.dto;

import com.devteria.bugtracking.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Issue {
    private String id;
    private String summary;
    private String status ;
    private String description ;
    private String labels ;
    private String priority;
    private String assignee;
    private String reporter ;
    private String project ;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
}
