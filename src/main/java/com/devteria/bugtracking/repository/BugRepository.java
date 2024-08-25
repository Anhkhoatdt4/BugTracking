package com.devteria.bugtracking.repository;

import com.devteria.bugtracking.entity.Bug;
import com.devteria.bugtracking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BugRepository extends JpaRepository<Bug, Long> {
    @Query(value = "SELECT MAX(id) FROM bugs", nativeQuery = true)
    Long findMaxPriority();
}
