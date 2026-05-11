package com.kapoor.ai.service.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_search_history",indexes = {
        @Index(name = "idx_user_search_history_user_id", columnList = "userId"),
        @Index(name = "idx_user_search_history_searched_at", columnList = "searchedAt")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String query;

    @Column(columnDefinition = "text")
    private String hotelContext;

    @Builder.Default
    private LocalDateTime searchedAt = LocalDateTime.now();
}