package com.nisum.foodcourt.repository;

import com.nisum.foodcourt.entity.Order;
import com.nisum.foodcourt.resource.constant.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Optional<Order> findByIdAndStatus(Integer id, OrderStatus status);

    Optional<Order> findByUserIdAndCreatedAt(Integer userId, LocalDate createdDate);

    List<Order> findAllByFloorBoyIdAndCreatedAt(Integer floorBoyId, LocalDate createdDate);


}
