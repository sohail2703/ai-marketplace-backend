package com.aimarketplace.controller;


import com.aimarketplace.dto.response.TransactionResponse;
import com.aimarketplace.enums.TransactionStatus;
import com.aimarketplace.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController extends BaseController {


    private final TransactionService transactionService;



    @GetMapping("/my")
    public ResponseEntity<List<TransactionResponse>> myTransactions(){

        return ok(
                transactionService.getMyTransactions()
        );

    }



    @PutMapping("/{id}/status")
    public ResponseEntity<TransactionResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam TransactionStatus status
    ){

        return ok(
                transactionService.updateStatus(
                        id,
                        status
                )
        );

    }

}