package com.nisum.foodcourt.entity;

import com.nisum.foodcourt.BaseModal.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "MenuItem")
@Entity
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuItem extends BaseEntity {

    String name;

    int price;

    int availability = 1;

}
