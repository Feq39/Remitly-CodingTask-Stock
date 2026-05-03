package com.remitly.stocks.controllers;

import com.remitly.stocks.dtos.ListOfStocksDTO;
import com.remitly.stocks.dtos.StockDTO;
import com.remitly.stocks.services.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ListOfStocksDTO getStateOfBank() {
        return stockService.getListOfAllBankStocks();
    }

    @PostMapping
    public void ChangeStockQuantity(@RequestBody ListOfStocksDTO listOfStocksDTO) {
        if (listOfStocksDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "body is null");
        }
        if (listOfStocksDTO.stocks() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "list is null");
        }
        for (StockDTO stockDTO : listOfStocksDTO.stocks()) {
            if (stockDTO.quantity() < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Negative Quantity not allowed");
            }
        }
        for (StockDTO stockDTO : listOfStocksDTO.stocks()) {
            stockService.SetStockQuantity(stockDTO.name(), stockDTO.quantity());
        }
    }


}
