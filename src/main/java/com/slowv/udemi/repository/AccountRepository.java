package com.slowv.udemi.repository;

import com.slowv.udemi.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
  Optional<AccountEntity> findByEmail(String email);

  boolean existsByEmail(String email);
}