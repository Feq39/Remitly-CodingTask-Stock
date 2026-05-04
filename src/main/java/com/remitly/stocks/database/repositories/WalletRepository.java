package com.remitly.stocks.database.repositories;

import com.remitly.stocks.database.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByPublicWalletId(String publicWalletId);

}
