package com.nisum.foodcourt.resource.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatusMessage {

    ORDER_UPDATED_MESSAGE("Order updated Successfully"),
    ORDER_CREATED_MESSAGE("Order created Successfully");

    final String value;
}
