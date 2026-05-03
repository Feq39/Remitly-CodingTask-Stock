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

        List<StockDTO> stocks = new ArrayList<>();
        for (StockHolding sh : wallet.getHoldings()) {
            StockDTO stockDTO = new StockDTO(sh.getStock().getName(), sh.getStockAmount());
            stocks.add(stockDTO);
        }
        return new WalletDTO(wallet.getPublicWalletId(), stocks);
    }

    public static StockDTO getStockDTOFromStockEntity(Stock stock) {
        return new StockDTO(stock.getName(), stock.getAmountLeft());
    }

    public static TransactionLogDTO getTransactionLogDtoFromEntity(AuditLog auditLog) {
        return new TransactionLogDTO(auditLog.getTypeOfOperation(), auditLog.getWallet().getPublicWalletId(), auditLog.getStock().getName());
    }
}
