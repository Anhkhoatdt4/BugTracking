package com.devteria.bugtracking.controller;

import com.devteria.bugtracking.entity.Bug;
import com.devteria.bugtracking.entity.Comment;
import com.devteria.bugtracking.service.BugService;
import com.devteria.bugtracking.service.CommentService;
import com.devteria.bugtracking.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BugService bugService;
    @Autowired
    private ProjectService projectService;

    @GetMapping("/bugs/{id}/comments")
    public String getCommentsByBugId(@PathVariable Long id, Model model) {
        List<Comment> comments = commentService.findCommentsByBugId(id);
        model.addAttribute("comments", comments);
        return "commentsList"; // Thymeleaf template name
    }

    @PostMapping("/comments/update/{id}")
    public ResponseEntity<Void> updateComment(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String updatedText = request.get("text");
        Optional<Comment> optionalComment = commentService.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setText(updatedText);
            commentService.saveComment(comment);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/comments/delete/{id}")
    public String deleteComment(@PathVariable Long id , @RequestParam("redirectUrl") String redirectUrl , RedirectAttributes redirectAttributes) {
            System.out.println("Id 1321321: " + id);
            commentService.deleteComment(id);
            return "redirect:" + redirectUrl;
    }

    @PostMapping("/addComment")
    public String addComment(
            @RequestParam("commentInput") String commentInput,
            @RequestParam("redirectUrl") String redirectUrl,
            @RequestParam("bugId") Long bugId,
            @RequestParam("projectId") Long projectId,
            RedirectAttributes redirectAttributes) {
        try {
            Bug bug = bugService.getBugById(bugId);
            System.out.println("Comment Input: " + commentInput);
            System.out.println("Redirect URL: " + redirectUrl);
            System.out.println("Bug ID: " + bugId);
            System.out.println("Project ID: " + projectId);
            System.out.println("Project ID: " + projectId);

            Comment comment = new Comment();
            comment.setBug(bug);
            comment.setProject(projectService.getProjectById(projectId));
            comment.setCreatedAt(LocalDateTime.now());
            comment.setText(commentInput);
            comment.setUser(bug.getAssignedTo());
            commentService.saveComment(comment);
            redirectAttributes.addFlashAttribute("message", "Comment added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add comment.");
        }
        return "redirect:" + redirectUrl;
    }


}