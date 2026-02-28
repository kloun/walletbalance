package me.cetjs2.walletbalance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;
import me.cetjs2.walletbalance.enums.OperationType;

@Schema(description = "Запрос на изменение баланса кошелька")
public record MoneyRequest(
    @Schema(
            description = "Уникальный идентификатор кошелька",
            example = "550e8400-e29b-41d4-a716-446655440000")
        @NotNull
        UUID walletId,
    @Schema(description = "Сумма операции (должна быть положительной)", example = "150.50")
        @Positive
        BigDecimal amount,
    @Schema(description = "Тип операции (DEPOSIT или WITHDRAW)", example = "DEPOSIT") @NotNull
        OperationType operationType) {}
