package com.nisum.foodcourt.entity;

import com.nisum.foodcourt.BaseModal.BaseAuditableEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "MenuItem")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuItem extends BaseAuditableEntity {

    @Column(name = "menu_item")
    String menuName;

    @Column(name = "price")
    Double price;

    @Column(name = "availability")
    int availability = 1;

}
