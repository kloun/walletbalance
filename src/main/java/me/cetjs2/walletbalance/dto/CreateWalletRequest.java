package me.cetjs2.walletbalance.dto;

import java.math.BigDecimal;

public record CreateWalletRequest(String owner, BigDecimal initialBalance) {}
