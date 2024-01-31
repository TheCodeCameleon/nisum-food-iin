package com.nisum.foodcourt.entity;

import com.nisum.foodcourt.BaseModal.BaseAuditableEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "floor")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Where(clause = "is_deleted = 0")
public class Floor extends BaseAuditableEntity {

    @Column(name = "floor_name")
    String floorName;

    String extension;

}
