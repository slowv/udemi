package com.slowv.udemi.repository;

import com.slowv.udemi.entity.AccountEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
  String ACCOUNT_BY_EMAIL_CACHE = "accountByEmail";

  @Cacheable(ACCOUNT_BY_EMAIL_CACHE)
  Optional<AccountEntity> findByEmail(String email);

  boolean existsByEmail(String email);
}