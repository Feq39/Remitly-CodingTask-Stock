package com.remitly.stocks.database.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "stock_holdings")
public class StockHolding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;
    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;
    @Column(name = "stock_amount")
    private long stockAmount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public long getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(long stockAmount) {
        this.stockAmount = stockAmount;
    }
}
