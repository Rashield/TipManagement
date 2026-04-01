package com.example.TipsManagement.controller;

import com.example.TipsManagement.model.LoggedUser;
import com.example.TipsManagement.model.Transaction;
import com.example.TipsManagement.model.dto.Request.TransactionRequest;
import com.example.TipsManagement.model.dto.Response.TransactionResponse;
import com.example.TipsManagement.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("banca/{bancaId}")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(@AuthenticationPrincipal LoggedUser usuario, @PathVariable Long bancaId, @RequestBody TransactionRequest transactionRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transactionService.deposit(usuario.getId(), bancaId, transactionRequest));
    }

//    @PostMapping("/withdraw")
}
