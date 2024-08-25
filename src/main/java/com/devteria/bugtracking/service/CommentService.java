package com.devteria.bugtracking.service;


import com.devteria.bugtracking.entity.Comment;
import com.devteria.bugtracking.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> findCommentsByBugId(Long bugId) {
        return commentRepository.findByBugId(bugId);
    }
    public List<Comment> findCommentsByProjectId(Long projectId) {
        return commentRepository.findByBugId(projectId);
    }

    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }
    public void deleteComment(Long id)
    {
        commentRepository.deleteById(id);
    }

}