package com.nisum.foodcourt.entity;

import com.nisum.foodcourt.BaseModal.BaseAuditableEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Table(name = "OrderLedger")
@Entity
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderLedger extends BaseAuditableEntity {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    Set<User> user;
}
