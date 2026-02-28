package me.cetjs2.walletbalance.exceptions;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {
  public InsufficientFundsException(String walletId, BigDecimal attempted, BigDecimal available) {
    super(
        "Insufficient funds in wallet "
            + walletId
            + ": attempted="
            + attempted
            + ", available="
            + available);
  }
}
