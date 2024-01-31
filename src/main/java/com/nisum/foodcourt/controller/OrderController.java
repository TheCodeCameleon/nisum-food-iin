package com.nisum.foodcourt.controller;

import com.nisum.foodcourt.model.OrderDto;
import com.nisum.foodcourt.resource.constant.SpringHeaders;
import com.nisum.foodcourt.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderDto orderDto, @RequestHeader(SpringHeaders.USER_ID) Integer userId) {
        return orderService.createOrder(orderDto, userId);
    }

    @PutMapping
    public ResponseEntity<?> updateOrder(@RequestBody @Valid OrderDto orderDto) {
        return orderService.updateOrder(orderDto);
    }

    @GetMapping
    public ResponseEntity<?> getOrderDetail(@RequestHeader(SpringHeaders.USER_ID) Integer userId) {
        return orderService.getOrderDetail(userId);
    }


}
