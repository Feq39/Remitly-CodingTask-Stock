package com.remitly.stocks.dtos;

import java.util.ArrayList;
import java.util.List;

public class WalletDTO {
    public String publicWalletId;
    public List<StockDTO> stocks = new ArrayList<>();
}
