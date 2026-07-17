package com.aimarketplace.repository;

import com.aimarketplace.entity.AiTool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AiToolRepository extends JpaRepository<AiTool, Long> {


    List<AiTool> findByNameContainingIgnoreCase(String keyword);


}