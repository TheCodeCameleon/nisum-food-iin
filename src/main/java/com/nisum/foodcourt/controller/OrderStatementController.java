package com.nisum.foodcourt.controller;

import com.nisum.foodcourt.resource.constant.SpringHeaders;
import com.nisum.foodcourt.service.OrderStatementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order-statement")
public class OrderStatementController {

    private final OrderStatementService orderStatementService;

    @GetMapping("/vendor")
    public ResponseEntity<?> generateVendorStatement(@RequestHeader(SpringHeaders.USER_ID) Integer userId) {
        return orderStatementService.generateVendorStatement(userId);
    }

}
