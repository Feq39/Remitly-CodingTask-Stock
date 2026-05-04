package com.remitly.stocks.database.repositories;

import com.remitly.stocks.database.entities.StockHolding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockHoldingRepository extends JpaRepository<StockHolding, Long> {

}
