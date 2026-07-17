package com.aimarketplace.service;

import com.aimarketplace.dto.response.TransactionResponse;
import com.aimarketplace.entity.Transaction;
import com.aimarketplace.entity.User;
import com.aimarketplace.enums.TransactionStatus;
import com.aimarketplace.exception.ResourceNotFoundException;
import com.aimarketplace.mapper.TransactionMapper;
import com.aimarketplace.repository.TransactionRepository;
import com.aimarketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {


    private final TransactionRepository transactionRepository;

    private final UserRepository userRepository;

    private final TransactionMapper transactionMapper;



    public List<TransactionResponse> getMyTransactions(){

        User user = getCurrentUser();


        return transactionRepository.findByUser(user)
                .stream()
                .map(transactionMapper::toResponse)
                .toList();

    }



    public TransactionResponse updateStatus(
            Long transactionId,
            TransactionStatus status
    ){

        Transaction transaction =
                transactionRepository.findById(transactionId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Transaction not found"
                                )
                        );


        transaction.setStatus(status);


        return transactionMapper.toResponse(
                transactionRepository.save(transaction)
        );

    }



    private User getCurrentUser(){

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();


        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "User not found"
                        )
                );

    }

}