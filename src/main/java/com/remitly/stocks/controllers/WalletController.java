package com.remitly.stocks.controllers;

import com.remitly.stocks.dtos.OperationTypeDTO;
import com.remitly.stocks.dtos.WalletDTO;

import com.remitly.stocks.serviceOperationStatuses.TransactionResult;
import com.remitly.stocks.services.AuditLogsService;
import com.remitly.stocks.services.StockService;
import com.remitly.stocks.services.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping(path = "wallets")
public class WalletController {

    private final WalletService walletService;
    private final StockService stockService;
    private final AuditLogsService auditLogsService;
    public WalletController(WalletService walletService, StockService stockService, AuditLogsService auditLogsService) {
        this.walletService = walletService;
        this.stockService = stockService;
        this.auditLogsService = auditLogsService;
    }

    @GetMapping("/{wallet_id}")
    public WalletDTO getWalletState(@PathVariable(name = "wallet_id") String publicWalletId) {
        Optional<WalletDTO> wallet = walletService.getWallet(publicWalletId);
        if(wallet.isPresent()) {
            return wallet.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"wallet does not exist");
    }
    @GetMapping("/{wallet_id}/stocks/{stock_name}")
    public long getQuantityOfStockInWallet(@PathVariable(name = "wallet_id") String publicWalletId,@PathVariable(name = "stock_name") String stockName) {
        Optional<Long> result = stockService.getAmountOfStockForWallet(publicWalletId,stockName);
        if (result.isPresent()) {
            return result.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Stock or wallet does not exist");
    }
    @PostMapping("/{wallet_id}/stocks/{stock_name}")
    public void processTransaction(@PathVariable(name = "wallet_id") String publicWalletId, @PathVariable(name = "stock_name") String stockName, @RequestBody OperationTypeDTO operationTypeDTO) {
        if(operationTypeDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"body is null");
        }
        if (operationTypeDTO.type == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"type is null");
        }
        if (!operationTypeDTO.type.equals("sell") && !operationTypeDTO.type.equals("buy")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Provided operation type not supported");
        }
        TransactionResult transactionResult;
        if(operationTypeDTO.type.equals("buy")) {
            transactionResult = walletService.buyStockForWallet(publicWalletId, stockName);
        } else {
            transactionResult = walletService.sellStockFromWallet(publicWalletId,stockName);
        }
        if (transactionResult.equals(TransactionResult.STOCK_DOES_NOT_EXIST)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"stock does not exist");
        }
        if (transactionResult.equals(TransactionResult.INSUFFICIENT_STOCK_QUANTITY_IN_BANK)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"no stock left in the bank");
        }
        if (transactionResult.equals(TransactionResult.INSUFFICIENT_STOCK_QUANTITY_IN_WALLET)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"wallet does not own stock");
        }
        auditLogsService.logTransaction(publicWalletId,stockName,operationTypeDTO.type);
    }
 }
