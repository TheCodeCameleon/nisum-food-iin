package com.nisum.foodcourt.entity;

import com.nisum.foodcourt.BaseModal.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@Table(name = "Order")
@Entity
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order extends BaseEntity {



    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "UserOrder",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    Set<User> user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "OrderItem",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id"))
    Set<MenuItem> menuItem;

    @Column(name = "ordered_at")
    Timestamp orderedAt;






}
