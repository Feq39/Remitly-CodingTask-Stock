package com.remitly.stocks.database.repositories;

import com.remitly.stocks.database.entities.DatabaseInsertLock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.Repository;


import java.util.Optional;


public interface DatabaseInsertLockRepository extends Repository<DatabaseInsertLock,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<DatabaseInsertLock> findById(long id);

    long count();
}
