package com.kapoor.ai.service.repository;

import com.kapoor.ai.service.model.UserSearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserSearchHistoryRepository extends JpaRepository<UserSearchHistory, UUID> {
    List<UserSearchHistory> findByUserIdOrderBySearchedAtDesc(String userId);

    List<UserSearchHistory> findTop5ByUserIdOrderBySearchedAtDesc(String userId);
}