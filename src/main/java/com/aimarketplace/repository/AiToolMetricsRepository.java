package com.aimarketplace.repository;

import com.aimarketplace.entity.AiToolMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AiToolMetricsRepository
        extends JpaRepository<AiToolMetrics, Long> {


}