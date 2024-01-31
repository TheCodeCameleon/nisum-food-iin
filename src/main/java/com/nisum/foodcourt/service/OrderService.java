package com.nisum.foodcourt.service;

import com.nisum.foodcourt.entity.Order;
import com.nisum.foodcourt.model.OrderDto;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    /**
     * persists new order against user and perform balance calculations
     *
     * @param orderDto {@link OrderDto}
     * @return creation confirmation
     */
    ResponseEntity<?> createOrder(OrderDto orderDto, Integer userId);

    /**
     * updates existing order against user only if order is in active status
     *
     * @param orderDto {@link OrderDto}
     * @return update confirmation
     */
    ResponseEntity<?> updateOrder(OrderDto orderDto);

    /**
     * fetches order detail and item details
     *
     * @return {@link OrderDto}
     */
    ResponseEntity<?> getOrderDetail(Integer userId);

    List<Order> getOrderListByDate(LocalDate createdDate, Integer floorBoyId);

    /**
     * floor boy has requested vendor statement now all the orders will be locked
     * and their order amount will be deducted from their wallet
     */
    List<Order> initiateOrdersPaymentDeduction(Integer floorBoyId);
}
