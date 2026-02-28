package me.cetjs2.walletbalance.exceptions;

public class WalletNotFoundException extends RuntimeException {
  public WalletNotFoundException(String message) {
    super(message);
  }
}
