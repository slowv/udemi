package com.slowv.udemi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Comment;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(
        name = "account_info"
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountInfoEntity extends AbstractAuditingEntity<Long> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "account_info_seq")
    Long id;

    @Comment("Tên")
    @Column(name = "first_name", nullable = false)
    String firstName;

    @Comment("Họ và đệm")
    @Column(name = "last_name", nullable = false)
    String lastName;

    @Comment("Giới thiệu")
    @Column(name = "introduce", columnDefinition = "TEXT")
    String introduce;

    @Column(name = "phone")
    String phone;

    @OneToOne
    AccountEntity account;
}
