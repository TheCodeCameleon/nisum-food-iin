package com.nisum.foodcourt.entity;

import com.nisum.foodcourt.BaseModal.BaseAuditableEntity;
import com.nisum.foodcourt.resource.constant.UserPaymentCycleType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Table(name = "wallet")
@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Wallet extends BaseAuditableEntity {

    @Column(name = "balance")
    Double balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_cycle")
    UserPaymentCycleType paymentCycleType;

    @Column(name = "cycle_start_date")
    LocalDate cycleStartDate;

}
