package com.slowv.udemi.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Setter
@ToString
@Getter
@Entity
@Table(name = "accounts", indexes = {
        @Index(columnList = "email", unique = true)
})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountEntity extends AbstractAuditingEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "account_seq")
    Long id;

    @Column(name = "email", unique = true, nullable = false, updatable = false)
    String email;

    @Column(name = "password", nullable = false)
    String password;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    List<RoleEntity> roles = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "account")
    @JoinColumn(name = "account_info_id", referencedColumnName = "id")
    AccountInfoEntity accountInfo;

    public void addRole(RoleEntity role) {
        if (ObjectUtils.isEmpty(roles)) {
            this.roles = new ArrayList<>();
        }
        roles.add(role);
    }
}