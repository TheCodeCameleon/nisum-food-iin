package com.nisum.foodcourt.service;

import com.nisum.foodcourt.entity.Order;
import com.nisum.foodcourt.entity.TransactedOrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class OrderStatementServiceImpl implements OrderStatementService {

    private final OrderService orderService;

    @Override
    public ResponseEntity<?> generateVendorStatement(Integer userId) {
        List<Order> orderList = orderService.initiateOrdersPaymentDeduction(userId);
        double totalOrdersAmount = orderList.stream().map(Order::getOrderAmount).mapToDouble(Double::doubleValue).sum();

        AtomicInteger totalItemWisePrice = new AtomicInteger(0);
        Map<String, TransactedOrderItem> vendorOrderStatement = new HashMap<>();
        orderList.stream()
                .flatMap(order -> order.getMenuItemList().stream())
                .forEach(orderItem -> {
                    generateVendorOrdersMap(totalItemWisePrice, vendorOrderStatement, orderItem);
                });

        if (totalOrdersAmount != totalItemWisePrice.get()) {
            throw new RuntimeException();
        }

        return ResponseEntity.status(HttpStatus.OK).body(vendorOrderStatement.values());
    }

    private static void generateVendorOrdersMap(AtomicInteger totalItemWisePrice,
                                                Map<String, TransactedOrderItem> vendorOrderStatement, TransactedOrderItem orderItem) {
        vendorOrderStatement.compute(orderItem.getMenuName(), (k, existingVendorItem) -> {
            TransactedOrderItem vendorItem = (Objects.nonNull(existingVendorItem))
                    ? existingVendorItem : new TransactedOrderItem();

            if (Objects.isNull(existingVendorItem)) {
                vendorItem.setMenuName(orderItem.getMenuName());
            }
            vendorItem.setQuantity(vendorItem.getQuantity() + orderItem.getQuantity());
            vendorItem.setPrice(vendorItem.getPrice() + orderItem.getPrice());
            totalItemWisePrice.addAndGet(orderItem.getPrice().intValue());
            return vendorItem;
        });
    }


}

