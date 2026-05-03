package com.remitly.stocks.services;

import com.remitly.stocks.database.entities.Stock;
import com.remitly.stocks.database.entities.StockHolding;
import com.remitly.stocks.database.entities.Wallet;
import com.remitly.stocks.database.repositories.StockHoldingRepository;
import com.remitly.stocks.database.repositories.StockRepository;
import com.remitly.stocks.database.repositories.WalletRepository;
import com.remitly.stocks.dtos.ListOfStocksDTO;
import com.remitly.stocks.dtos.StockDTO;
import com.remitly.stocks.utils.EntityToDtoConverter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final WalletRepository walletRepository;
    public StockService(StockRepository stockRepository,WalletRepository walletRepository) {
        this.stockRepository = stockRepository;
        this.walletRepository = walletRepository;
    }

    public ListOfStocksDTO getListOfAllBankStocks() {
        List<Stock> stockEntities = stockRepository.findAll();
        List<StockDTO> stockDTOS = new ArrayList<>();
        for (Stock s : stockEntities) {
            stockDTOS.add(EntityToDtoConverter.getStockDTOFromStockEntity(s));
        }
        ListOfStocksDTO result = new ListOfStocksDTO();
        result.stocks = stockDTOS;
        return result;
    }

    public Optional<Long> getAmountOfStockForWallet(String publicWalletId,String stockName) {
        Optional<Wallet> walletOpt = walletRepository.findByPublicWalletId(publicWalletId);
        if (walletOpt.isEmpty()) {
            return Optional.empty();
        }
        Optional<StockHolding> result =  walletOpt.get().getHoldings().stream().filter(holding -> holding.getStock().getName().equals(stockName)).findFirst();
        return result.map(StockHolding::getStockAmount).or(() -> Optional.of(0L));
    }
    @Transactional
    public void SetStockQuantity(String stockName,long quantity) {
        Optional<Stock> stockOpt = stockRepository.findByName(stockName);
        Stock stock;
        if(stockOpt.isPresent()) {
            stock = stockOpt.get();
        } else {
            stock = new Stock();
            stock.setName(stockName);
        }
        stock.setAmountLeft(quantity);
        stockRepository.save(stock);
    }

}
