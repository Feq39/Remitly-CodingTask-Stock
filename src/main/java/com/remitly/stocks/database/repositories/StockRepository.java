package com.remitly.stocks.database.repositories;

import com.remitly.stocks.database.entities.Stock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByName(String name);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Stock> findWithLockingByName(String name);
}
