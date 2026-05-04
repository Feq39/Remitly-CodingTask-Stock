package com.remitly.stocks.database.entities;

import jakarta.persistence.*;


@Entity
@Table(name="database_insert_lock")
public class DatabaseInsertLock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
}
