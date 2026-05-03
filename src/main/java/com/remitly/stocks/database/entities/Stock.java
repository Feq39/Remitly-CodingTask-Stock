package com.remitly.stocks.database.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "stocks")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "amount_left")
    private long amountLeft;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAmountLeft() {
        return amountLeft;
    }

    public void setAmountLeft(long amountLeft) {
        this.amountLeft = amountLeft;
    }

}
