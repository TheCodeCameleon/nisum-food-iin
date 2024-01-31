package com.nisum.foodcourt.entity;

import com.nisum.foodcourt.BaseModal.BaseAuditableEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "TransactedOrderItems")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactedOrderItem extends BaseAuditableEntity {

    @Column(name = "menu_item")
    String menuName;

    @Column(name = "price")
    Double price;

    @Column(name = "quantity")
    int quantity;

}
