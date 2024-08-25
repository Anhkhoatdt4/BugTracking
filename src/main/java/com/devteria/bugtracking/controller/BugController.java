package com.devteria.bugtracking.controller;

import com.devteria.bugtracking.entity.Bug;
import com.devteria.bugtracking.entity.Comment;
import com.devteria.bugtracking.entity.User;
import com.devteria.bugtracking.service.BugService;
import com.devteria.bugtracking.service.CommentService;
import com.devteria.bugtracking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bugs")
@RequiredArgsConstructor
public class BugController {

    private final BugService bugService;
    private final UserService userService;
    private final CommentService commentService;

    @PostMapping
    public Bug createBug(@RequestBody Bug bug) {
        return bugService.createBug(bug);
    }
    @GetMapping("/edit/{id}")
    public String editBug(@PathVariable Long id, Model model) {
        Bug bug = bugService.getBugById(id);
        List<User> users = userService.getAllUsers();
        List<Comment> comments = commentService.findCommentsByBugId(id);
        model.addAttribute("bug", bug);
        model.addAttribute("users", users);
        model.addAttribute("comments", comments);
        return "bug-edit"; // Tên file Thymeleaf
    }

    @GetMapping
    public List<Bug> getAllBugs() {
        return bugService.getAllBugs();
    }

    @PostMapping("/saveBugPro")
    public String saveBug(
                          @RequestParam(value = "description") String description ,
                          @RequestParam(value = "comments", required = false) String comments,
                          @RequestParam(value = "priority") String priority ,
                          @RequestParam("projectId") Long projectId) {
        if (comments != null && !comments.trim().isEmpty()) {
            List<Comment> commentsList = commentService.findCommentsByProjectId(projectId);
            Comment comment;
            if (commentsList.isEmpty()) {
                // Tạo comment mới nếu không có comment nào trong project
                comment = new Comment();
            } else {
                // Lấy comment đầu tiên từ danh sách
                comment = commentsList.get(0);
            }
            comment.setText(comments);
            commentService.saveComment(comment);
        }
        return "redirect:/project/id=" + projectId;
    }


}
