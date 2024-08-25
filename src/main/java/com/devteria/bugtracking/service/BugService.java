package com.devteria.bugtracking.service;

import com.devteria.bugtracking.dto.Issue;
import com.devteria.bugtracking.dto.UpdateFieldRequest;
import com.devteria.bugtracking.entity.Bug;
import com.devteria.bugtracking.entity.Project;
import com.devteria.bugtracking.entity.User;
import com.devteria.bugtracking.repository.BugRepository;
import com.devteria.bugtracking.repository.ProjectRepository;
import com.devteria.bugtracking.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class BugService {
    UserRepository userRepository;
    BugRepository bugRepository;
    private final ProjectRepository projectRepository;

    public Bug createBug(Bug bug) {
        try {
            bug.setCreatedDate(LocalDateTime.now());
            bug.setLastUpdatedDate(LocalDateTime.now());
            return bugRepository.save(bug);
        } catch (Exception e) {
            log.error("Error creating bug: {}", e.getMessage());
            throw new RuntimeException("Error creating bug", e);
        }
    }

    public Bug updateBug(Bug bug) {
        if (!bugRepository.existsById(bug.getId())) {
            throw new RuntimeException("Bug not found");
        }
        bug.setLastUpdatedDate(LocalDateTime.now());
        return bugRepository.save(bug);
    }

    public void deleteBug(Long id) {
        if (!bugRepository.existsById(id)) {
            throw new RuntimeException("Bug not found");
        }
        bugRepository.deleteById(id);
    }

    public Bug getBugById(Long id) {
        return bugRepository.findById(id).orElseThrow(() -> new RuntimeException("Bug not found"));
    }

    public List<Bug> getAllBugs() {
        return bugRepository.findAll();
    }

    public void updateField(UpdateFieldRequest request)
    {
        Optional<Bug> optionalBug = bugRepository.findById(request.getId());
        if (optionalBug.isPresent()) {
            Bug bug = optionalBug.get();
            // Cập nhật giá trị theo field
            switch (request.getField()) {
                case "summary":
                    bug.setTitle(request.getValue());
                    break;
                case "assignee":
                    try {
                        Long assigneeId = Long.parseLong(request.getValue());
                        bug.setAssignedTo(userRepository.findById(assigneeId).get());
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid assignee ID: " + request.getValue());
                    }
                    break;
            }
            bugRepository.save(bug);
        } else {
            throw new EntityNotFoundException("Bug not found");
        }
    }

    public void createIssue(Issue issue) {
        User assignee = userRepository.findByUsername(issue.getAssignee());
        if (assignee == null) {
            throw new IllegalArgumentException("Người dùng không tìm thấy: " + issue.getAssignee());
        }
        Project project = projectRepository.findByName(issue.getProject());
        System.err.println(issue.getProject());
        if (project == null) {
            throw new IllegalArgumentException("Dự án không tìm thấy: " + issue.getProject());
        }

        Bug bug = Bug.builder()
                .title(issue.getSummary())
                .description(issue.getDescription())
                .priority(issue.getPriority())
                .status(issue.getStatus())
                .assignedTo(assignee)
                .project(project)
                .createdDate(LocalDateTime.now())
                .lastUpdatedDate(LocalDateTime.now())
                .reporter(issue.getReporter())
                .labels(issue.getLabels())
                .build();

        bugRepository.save(bug);
    }


}