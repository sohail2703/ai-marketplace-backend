package com.aimarketplace.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ai_tool_metrics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiToolMetrics extends BaseEntity {

    @Column(nullable = false)
    @Builder.Default
    private Long views = 0L;

    @Column(nullable = false)
    @Builder.Default
    private Long bookmarks = 0L;

    @Column(nullable = false)
    @Builder.Default
    private Long chats = 0L;

    @Column(nullable = false)
    @Builder.Default
    private Long websiteClicks = 0L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tool_id", nullable = false, unique = true)
    private AiTool aiTool;

}