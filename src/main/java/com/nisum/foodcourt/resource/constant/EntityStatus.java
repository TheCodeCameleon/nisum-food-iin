package com.nisum.foodcourt.resource.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum EntityStatus {

    ACTIVE((byte) 1),
    NON_ACTIVE((byte) 0);

    byte value;

}
