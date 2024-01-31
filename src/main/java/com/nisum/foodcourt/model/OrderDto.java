package com.nisum.foodcourt.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

/**
 * this class is responsible for gathering order related information
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDto {

    Integer id;

    Set<OrderItemDto> menuItemList;

    String orderDetail;

    String userNotes;

    List<Integer> poolingUserIds;
}
