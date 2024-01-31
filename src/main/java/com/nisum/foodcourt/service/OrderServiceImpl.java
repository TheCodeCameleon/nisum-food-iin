package com.nisum.foodcourt.service;

import com.nisum.foodcourt.entity.MenuItem;
import com.nisum.foodcourt.entity.Order;
import com.nisum.foodcourt.entity.TransactedOrderItem;
import com.nisum.foodcourt.entity.User;
import com.nisum.foodcourt.model.OrderDto;
import com.nisum.foodcourt.model.OrderItemDto;
import com.nisum.foodcourt.repository.OrderRepository;
import com.nisum.foodcourt.resource.constant.OrderStatus;
import com.nisum.foodcourt.resource.constant.ResponseStatusMessage;
import com.nisum.foodcourt.resource.util.ConversionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final MenuItemService menuItemService;

    private final PaymentTransactionService paymentTransactionService;

    private final ConversionUtil conversionUtil;

    @Override
    @Transactional
    public ResponseEntity<?> createOrder(OrderDto orderDto, Integer userId) {
        // still neeed to code for credit amount
        User user = conversionUtil.getReferentialEntity(userId, User.class);
        Set<TransactedOrderItem> lineItemList = calculateAndBuildOrderItems(orderDto.getMenuItemList());
        Order order = Order.builder()
                .extraDetail(orderDto.getOrderDetail())
                .userNotes(orderDto.getUserNotes())
                .orderAmount(lineItemList.stream().map(TransactedOrderItem::getPrice).mapToDouble(Double::doubleValue).sum())
                .user(user)
                .menuItemList(lineItemList)
                .status(OrderStatus.IN_CART)
                .build();

        orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseStatusMessage.ORDER_CREATED_MESSAGE);
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateOrder(OrderDto orderDto) {
        Order existingOrder = orderRepository.findByIdAndStatus(orderDto.getId(), OrderStatus.IN_CART)
                .orElseThrow(() -> new RuntimeException(""));
        existingOrder.setExtraDetail(orderDto.getOrderDetail());
        existingOrder.setUserNotes(orderDto.getUserNotes());
        orderDto.getMenuItemList().forEach(orderItem -> {

            MenuItem menuItem = menuItemService.getMenuItemListCache().get(orderItem.getMenuId());
            existingOrder.getMenuItemList()
                    .stream()
                    .filter(existingOrderItem -> existingOrder.getId().equals(orderItem.getId()))
                    .findAny().ifPresentOrElse(existingOrderItem -> {
                        existingOrderItem.setQuantity(orderItem.getQuantity());
                    }, () -> {
                        TransactedOrderItem newItem = TransactedOrderItem.builder()
                                .menuName(menuItem.getMenuName())
                                .quantity(orderItem.getQuantity())
                                .price(orderItem.getQuantity() * menuItem.getPrice()).build();
                        existingOrder.getMenuItemList().add(newItem);
                    });
        });

        orderRepository.save(existingOrder);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ResponseStatusMessage.ORDER_UPDATED_MESSAGE);
    }

    @Override
    public ResponseEntity<?> getOrderDetail(Integer userId) {
        Order order = orderRepository.findByUserIdAndCreatedAt(userId, LocalDate.now()).orElseThrow(() -> new RuntimeException(""));
        OrderDto orderDto = conversionUtil.mapEntityToModel(order, OrderDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @Override
    public List<Order> getOrderListByDate(LocalDate createdDate, Integer floorBoyId) {
        return orderRepository.findAllByFloorBoyIdAndCreatedAt(floorBoyId, createdDate);
    }

    @Override
    @Transactional
    public List<Order> initiateOrdersPaymentDeduction(Integer floorBoyId) {
        List<Order> orderList = orderRepository.findAllByFloorBoyIdAndCreatedAt(floorBoyId, LocalDate.now());
        orderList.forEach(order -> {
            order.setStatus(OrderStatus.PURCHASED);
            paymentTransactionService.createOrderPaymentTransaction(order.getOrderAmount(), order.getUser());
        });

        return orderRepository.saveAll(orderList);
    }


    private Set<TransactedOrderItem> calculateAndBuildOrderItems(Set<OrderItemDto> orderItemDtoList) {
        Map<Integer, MenuItem> menuItemsList = menuItemService.getMenuItemListCache();
        Set<TransactedOrderItem> lineItemList = new HashSet<>();
        orderItemDtoList.forEach(orderItemDto -> {
            MenuItem menuItem = menuItemsList.get(orderItemDto.getMenuId());
            TransactedOrderItem transactedItem = TransactedOrderItem.builder()
                    .menuName(menuItem.getMenuName())
                    .quantity(orderItemDto.getQuantity())
                    .price(orderItemDto.getQuantity() * menuItem.getPrice())
                    .build();
            lineItemList.add(transactedItem);
        });

        return lineItemList;
    }
}
