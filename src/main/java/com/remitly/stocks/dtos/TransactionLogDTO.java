package com.remitly.stocks.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TransactionLogDTO(
        String type,
        @JsonProperty("wallet_id") String publicWalledId,
        @JsonProperty("stock_name") String stockName
) {
}
