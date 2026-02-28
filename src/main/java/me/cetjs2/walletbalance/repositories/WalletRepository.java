package me.cetjs2.walletbalance.repositories;

import java.util.Optional;
import java.util.UUID;
import me.cetjs2.walletbalance.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {

  Optional<Wallet> findByOwner(String owner);

  boolean existsByOwner(String owner);
}
