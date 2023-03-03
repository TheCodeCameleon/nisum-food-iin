package com.nisum.foodcourt.entity;

import com.nisum.foodcourt.BaseModal.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BuildingFloor")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Where(clause = "is_deleted = 0")
public class Floor extends BaseEntity {

    @Column(name = "floor_name")
    String floorName;

}
