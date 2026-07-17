package com.aimarketplace.repository;

import com.aimarketplace.entity.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SubscriptionPlanRepository
        extends JpaRepository<SubscriptionPlan, Long> {


}