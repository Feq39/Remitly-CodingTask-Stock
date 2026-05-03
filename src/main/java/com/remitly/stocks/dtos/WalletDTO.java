package com.remitly.stocks.dtos;


import java.util.List;

public record WalletDTO(
        String publicWalletId,
        List<StockDTO> stocks
) {
}
