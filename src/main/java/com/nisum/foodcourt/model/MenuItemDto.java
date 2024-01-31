package com.nisum.foodcourt.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuItemDto {

    Integer id;

    String menuName;

    int price;

    int availability;
}
