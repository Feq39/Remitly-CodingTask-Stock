package com.remitly.stocks.database.repositories;

import com.remitly.stocks.database.entities.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogsRepository extends JpaRepository<AuditLog,Long> {
}
