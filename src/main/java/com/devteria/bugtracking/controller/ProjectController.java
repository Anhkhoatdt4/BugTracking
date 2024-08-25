package com.devteria.bugtracking.controller;

import com.devteria.bugtracking.entity.Comment;
import com.devteria.bugtracking.service.BugService;
import com.devteria.bugtracking.service.CommentService;
import com.devteria.bugtracking.service.ProjectService;
import com.devteria.bugtracking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import com.devteria.bugtracking.entity.Project;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final BugService bugService;
    private final UserService userService;
    private final CommentService commentService;

    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @GetMapping("/id={id}")
    public String viewProjectDetails(@PathVariable("id") Long id, Model model) {
        Project project = projectService.getProjectById(id);
        if (project == null) {
            return "error/404";
        }
        model.addAttribute("project", project);
        model.addAttribute("comments", commentService.findCommentsByBugId(id).stream()
                .map(comment -> {
                    comment.setTimeElapsed(comment.getTimeElapsed());
                    return comment;
                }).collect(Collectors.toList()));
        System.err.println(commentService.findCommentsByBugId(id).stream());
        model.addAttribute("Bug", bugService.getBugById(id));
        model.addAttribute("users", userService.getAllUsers());
        List<Comment> comments = commentService.findCommentsByProjectId(id);
        String allComments = comments.stream()
                .map(Comment::getText)
                .collect(Collectors.joining("\n"));

        model.addAttribute("allComments", allComments);
        return "project-details";
    }

    @PutMapping
    public Project updateProject(@RequestBody Project project) {
        return projectService.updateProject(project);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }


}