package com.slowv.udemi.repository;


import com.slowv.udemi.entity.RoleEntity;
import com.slowv.udemi.entity.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(ERole eRole);
}