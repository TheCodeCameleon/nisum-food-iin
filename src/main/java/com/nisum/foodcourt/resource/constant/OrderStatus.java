package com.nisum.foodcourt.resource.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    PENDING("PENDING", "order is in pending stage"),
    IN_CART("IN_CART", "status where order are allowed to be created"),
    CONFIRMED("CONFIRMED", "status where update is locked but new can be created"),
    PURCHASED("PURCHASED", "status when vendor statement has been generated and order can not be created/updated");

    String value, description;
}
