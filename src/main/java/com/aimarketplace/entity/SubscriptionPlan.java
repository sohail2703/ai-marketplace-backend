package com.aimarketplace.entity;

import com.aimarketplace.enums.PlanTier;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subscription_plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionPlan extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanTier tier;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer durationInDays;

    @OneToMany(mappedBy = "subscriptionPlan", cascade = CascadeType.ALL)
    @Builder.Default
    private List<UserSubscription> userSubscriptions = new ArrayList<>();

}