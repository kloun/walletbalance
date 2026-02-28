package me.cetjs2.walletbalance.dto;

import java.time.LocalDateTime;

public record ErrorResponse(int status, String error, LocalDateTime timestamp) {}
