package com.remitly.stocks.database.entities;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "public_wallet_id", unique = true)
    private String publicWalletId;
    @OneToMany(mappedBy = "wallet")
    private List<StockHolding> holdings = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPublicWalletId() {
        return publicWalletId;
    }

    public void setPublicWalletId(String publicWalletId) {
        this.publicWalletId = publicWalletId;
    }

    public List<StockHolding> getHoldings() {
        return holdings;
    }

    public void setHoldings(List<StockHolding> holdings) {
        this.holdings = holdings;
    }


}
