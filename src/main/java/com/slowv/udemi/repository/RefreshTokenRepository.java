package com.slowv.udemi.repository;

import com.slowv.udemi.entity.AccountEntity;
import com.slowv.udemi.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
  Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken);

  List<RefreshTokenEntity> findByAccountAndRevoked(AccountEntity account, boolean revoked);
}