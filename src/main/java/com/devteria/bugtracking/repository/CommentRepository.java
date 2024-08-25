package com.devteria.bugtracking.repository;

import com.devteria.bugtracking.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBugId(Long bugId);
    List<Comment> findByProjectId(Long projectId);
    Optional<Comment> findCommentById(Long id);
}
