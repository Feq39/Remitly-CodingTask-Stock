package com.remitly.stocks.services;

import com.remitly.stocks.database.entities.AuditLog;
import com.remitly.stocks.database.entities.Stock;
import com.remitly.stocks.database.entities.Wallet;
import com.remitly.stocks.database.repositories.AuditLogsRepository;
import com.remitly.stocks.database.repositories.StockRepository;
import com.remitly.stocks.database.repositories.WalletRepository;
import com.remitly.stocks.dtos.ListOfTransactionsLogsDTO;
import com.remitly.stocks.dtos.TransactionLogDTO;
import com.remitly.stocks.utils.EntityToDtoConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuditLogsService {

    private final AuditLogsRepository auditLogsRepository;
    private final WalletRepository walletRepository;
    private final StockRepository stockRepository;

    public AuditLogsService(AuditLogsRepository auditLogsRepository, WalletRepository walletRepository, StockRepository stockRepository) {
        this.auditLogsRepository = auditLogsRepository;
        this.walletRepository = walletRepository;
        this.stockRepository = stockRepository;
    }

    public ListOfTransactionsLogsDTO getAllLogs() {
        List<AuditLog> allLogs = auditLogsRepository.findAll();
        List<TransactionLogDTO> logsDTOS = allLogs.stream().map(EntityToDtoConverter::getTransactionLogDtoFromEntity).toList();
        return new ListOfTransactionsLogsDTO(logsDTOS);
    }

    @Transactional
    public void logTransaction(String publicWalletId, String stockName, String type) {
        AuditLog auditLog = new AuditLog();
        Wallet wallet = walletRepository.findByPublicWalletId(publicWalletId).orElseThrow();
        Stock stock = stockRepository.findByName(stockName).orElseThrow();
        auditLog.setTypeOfOperation(type);
        auditLog.setWallet(wallet);
        auditLog.setStock(stock);
        auditLogsRepository.save(auditLog);
    }
}
