package com.remitly.stocks.utils;

import com.remitly.stocks.database.entities.AuditLog;
import com.remitly.stocks.database.entities.Stock;
import com.remitly.stocks.database.entities.StockHolding;
import com.remitly.stocks.database.entities.Wallet;
import com.remitly.stocks.dtos.StockDTO;
import com.remitly.stocks.dtos.TransactionLogDTO;
import com.remitly.stocks.dtos.WalletDTO;

import java.util.ArrayList;
import java.util.List;

public class EntityToDtoConverter {
    public static WalletDTO getWalletDTOFromWalletEntity(Wallet wallet) {
        WalletDTO result = new WalletDTO();
        List<StockDTO> stocks = new ArrayList<>();
        for (StockHolding sh : wallet.getHoldings()) {
            StockDTO stockDTO = new StockDTO();
            stockDTO.name = sh.getStock().getName();
            stockDTO.quantity = sh.getStockAmount();
            stocks.add(stockDTO);
        }
        result.publicWalletId = wallet.getPublicWalletId();
        result.stocks = stocks;
        return result;
    }
    public static StockDTO getStockDTOFromStockEntity(Stock stock) {
        StockDTO result = new StockDTO();
        result.name = stock.getName();
        result.quantity = stock.getAmountLeft();
        return result;
    }

    public static TransactionLogDTO getTransactionLogDtoFromEntity(AuditLog auditLog) {
        TransactionLogDTO result = new TransactionLogDTO();
        result.type = auditLog.getTypeOfOperation();
        result.walled_id = auditLog.getWallet().getPublicWalletId();
        result.stock_name = auditLog.getStock().getName();
        return result;
    }
}
