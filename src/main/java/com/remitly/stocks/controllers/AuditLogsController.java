package com.remitly.stocks.controllers;

import com.remitly.stocks.dtos.ListOfTransactionsLogsDTO;
import com.remitly.stocks.services.AuditLogsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("logs")
public class AuditLogsController {
    private final AuditLogsService auditLogsService;

    public AuditLogsController(AuditLogsService auditLogsService) {
        this.auditLogsService = auditLogsService;
    }

    @GetMapping
    public ListOfTransactionsLogsDTO getAllTransactionsLogs() {
        return auditLogsService.getAllLogs();
    }
}
