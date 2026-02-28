package me.cetjs2.walletbalance.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wallets")
public class Wallet {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "uid", updatable = false, nullable = false)
  private UUID uid;

  @Column(name = "balance", precision = 19, scale = 2, nullable = false)
  @NotNull
  @Builder.Default
  private BigDecimal balance = BigDecimal.ZERO;

  @Column(name = "owner", nullable = false)
  @NotNull
  private String owner;
}
