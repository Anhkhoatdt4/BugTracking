package com.devteria.bugtracking.repository;

import com.devteria.bugtracking.entity.Project;
import com.devteria.bugtracking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByName(String name);
}
