package com.nisum.foodcourt.service;

import org.springframework.http.ResponseEntity;

public interface OrderStatementService {

    ResponseEntity<?> generateVendorStatement(Integer userId);
}
