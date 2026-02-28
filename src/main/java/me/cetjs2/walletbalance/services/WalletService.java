package me.cetjs2.walletbalance.services;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.cetjs2.walletbalance.dto.CreateWalletRequest;
import me.cetjs2.walletbalance.dto.MoneyRequest;
import me.cetjs2.walletbalance.dto.SucessActionResponse;
import me.cetjs2.walletbalance.enums.OperationType;
import me.cetjs2.walletbalance.exceptions.InsufficientFundsException;
import me.cetjs2.walletbalance.exceptions.WalletNotFoundException;
import me.cetjs2.walletbalance.models.Wallet;
import me.cetjs2.walletbalance.repositories.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletService {

  private final WalletRepository repository;

  public SucessActionResponse getBalance(UUID walletId) {
    Wallet w =
        repository
            .findById(walletId)
            .orElseThrow(() -> new WalletNotFoundException("Wallet not found: " + walletId));
    return new SucessActionResponse("Wallet balance is: " + w.getBalance(), LocalDateTime.now());
  }

  @Transactional
  public SucessActionResponse doWalletOperations(MoneyRequest mRequest) {
    if (mRequest.amount() == null || mRequest.amount().signum() <= 0)
      throw new IllegalArgumentException("amount must be positive");
    var wallet =
        repository
            .findById(mRequest.walletId())
            .orElseThrow(
                () -> new WalletNotFoundException("Wallet not found: " + mRequest.walletId()));
    var available = wallet.getBalance();
    Wallet updated = null;
    if (mRequest.operationType() == OperationType.WITHDRAW) {
      if (available.compareTo(mRequest.amount()) < 0) {
        throw new InsufficientFundsException(
            mRequest.walletId().toString(), mRequest.amount(), available);
      }
      updated = wallet.toBuilder().balance(available.subtract(mRequest.amount())).build();

    } else if (mRequest.operationType() == OperationType.DEPOSIT) {
      updated = wallet.toBuilder().balance(available.add(mRequest.amount())).build();
    }

    repository.save(updated);
    return new SucessActionResponse("Operation completed successfully", LocalDateTime.now());
  }

  public SucessActionResponse createWallet(CreateWalletRequest wr) {
    if (wr.initialBalance().signum() < 0) {
      throw new IllegalArgumentException("initial balance must be positive or zero");
    }

    var newWallet = Wallet.builder().balance(wr.initialBalance()).owner(wr.owner()).build();
    var walletUid = repository.save(newWallet).getUid().toString();
    return new SucessActionResponse(
        "Operation completed successfully! New wallet UID is " + walletUid, LocalDateTime.now());
  }
}
