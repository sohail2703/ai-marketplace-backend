package com.aimarketplace.repository;

import com.aimarketplace.entity.Transaction;
import com.aimarketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {


    List<Transaction> findByUser(User user);

}