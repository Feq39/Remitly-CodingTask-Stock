package com.remitly.stocks.services;

import com.remitly.stocks.database.entities.Stock;
import com.remitly.stocks.database.entities.StockHolding;
import com.remitly.stocks.database.entities.Wallet;
import com.remitly.stocks.database.repositories.StockHoldingRepository;
import com.remitly.stocks.database.repositories.StockRepository;
import com.remitly.stocks.database.repositories.WalletRepository;
import com.remitly.stocks.dtos.WalletDTO;
import com.remitly.stocks.serviceOperationStatuses.TransactionResult;
import com.remitly.stocks.utils.EntityToDtoConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;


@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final StockRepository stockRepository;
    private final StockHoldingRepository stockHoldingRepository;
    public WalletService(WalletRepository walletRepository,StockRepository stockRepository,StockHoldingRepository stockHoldingRepository) {
        this.walletRepository = walletRepository;
        this.stockRepository = stockRepository;
        this.stockHoldingRepository = stockHoldingRepository;
    }

    public Optional<WalletDTO> getWallet(String publicWalletId) {
        Optional<Wallet> wallet = walletRepository.findByPublicWalletId(publicWalletId);
        return wallet.map(EntityToDtoConverter::getWalletDTOFromWalletEntity);
    }

    @Transactional
    public TransactionResult buyStockForWallet(String publicWalletId, String stockName) {
        Optional<Stock> stockOpt = stockRepository.findByName(stockName);
        if (stockOpt.isEmpty()) {
            return TransactionResult.STOCK_DOES_NOT_EXIST;
        }
        Stock stock = stockOpt.get();
        if(stock.getAmountLeft() == 0) {
            return TransactionResult.INSUFFICIENT_STOCK_QUANTITY_IN_BANK;
        }
        Optional<Wallet> walletOpt = walletRepository.findByPublicWalletId(publicWalletId);
        Wallet wallet;
        if(walletOpt.isPresent()) {
            wallet = walletOpt.get();
        } else {
            wallet = new Wallet();
            wallet.setPublicWalletId(publicWalletId);
            wallet.setHoldings(new ArrayList<>());
            walletRepository.save(wallet);
        }
        Optional <StockHolding> stockHoldingOpt = getHoldingOfStockFromWallet(wallet,stock);
        StockHolding stockHolding;
        if (stockHoldingOpt.isEmpty()) {
            stockHolding = new StockHolding();
            stockHolding.setStockAmount(1);
        } else {
            stockHolding = stockHoldingOpt.get();
            stockHolding.setStockAmount(stockHolding.getStockAmount() + 1);
        }
        stock.setAmountLeft(stock.getAmountLeft() - 1);
        stockRepository.save(stock);
        stockHolding.setWallet(wallet);
        stockHolding.setStock(stock);

        stockHoldingRepository.save(stockHolding);
        return TransactionResult.SUCCESS;
    }
    @Transactional
    public TransactionResult sellStockFromWallet(String publicWalletId,String stockName) {
        Optional<Stock> stockOpt = stockRepository.findByName(stockName);
        if (stockOpt.isEmpty()) {
            return TransactionResult.STOCK_DOES_NOT_EXIST;
        }
        Stock stock = stockOpt.get();
        Optional<Wallet> walletOpt = walletRepository.findByPublicWalletId(publicWalletId);
        if(walletOpt.isEmpty()) {
            return TransactionResult.INSUFFICIENT_STOCK_QUANTITY_IN_WALLET;
        }
        Wallet wallet = walletOpt.get();
        Optional<StockHolding> holdingOpt = getHoldingOfStockFromWallet(wallet,stock);
        if (holdingOpt.isEmpty()) {
            return TransactionResult.INSUFFICIENT_STOCK_QUANTITY_IN_WALLET;
        }
        StockHolding holding = holdingOpt.get();
        if (holding.getStockAmount() == 0) {
            return TransactionResult.INSUFFICIENT_STOCK_QUANTITY_IN_WALLET;
        }
        stock.setAmountLeft(stock.getAmountLeft() + 1);
        holding.setStockAmount(holding.getStockAmount() - 1);
        stockRepository.save(stock);
        stockHoldingRepository.save(holding);
        return TransactionResult.SUCCESS;
    }

    private Optional<StockHolding>getHoldingOfStockFromWallet(Wallet wallet,Stock stock) {
        return wallet.getHoldings().stream().filter(holding -> holding.getStock().equals(stock)).findFirst();
    }
}
