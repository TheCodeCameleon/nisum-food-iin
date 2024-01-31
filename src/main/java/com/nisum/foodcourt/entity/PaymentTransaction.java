package com.nisum.foodcourt.entity;

import com.nisum.foodcourt.BaseModal.BaseAuditableEntity;
import com.nisum.foodcourt.resource.constant.PaymentPurposeType;
import com.nisum.foodcourt.resource.constant.TransactionStatus;
import com.nisum.foodcourt.resource.constant.TransactionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "transaction")
@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentTransaction extends BaseAuditableEntity {

    @Column(name = "amount")
    Double amount;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    Wallet wallet;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status")
    TransactionStatus transactionStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "purpose_type")
    PaymentPurposeType paymentPurposeType;
}
