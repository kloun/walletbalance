package me.cetjs2.walletbalance.controllers;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.cetjs2.walletbalance.dto.CreateWalletRequest;
import me.cetjs2.walletbalance.dto.MoneyRequest;
import me.cetjs2.walletbalance.dto.SucessActionResponse;
import me.cetjs2.walletbalance.services.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class WalletController {
  private final WalletService walletService;

  @GetMapping("/wallets/{walletId}")
  public ResponseEntity<SucessActionResponse> getBalance(@PathVariable UUID walletId) {
    SucessActionResponse response = walletService.getBalance(walletId);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/wallet")
  public ResponseEntity<SucessActionResponse> performOperation(@RequestBody MoneyRequest request) {
    SucessActionResponse response = walletService.doWalletOperations(request);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping("/wallet/new")
  public ResponseEntity<SucessActionResponse> create(@RequestBody CreateWalletRequest request) {
    SucessActionResponse response = walletService.createWallet(request);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
