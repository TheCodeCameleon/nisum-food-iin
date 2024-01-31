package com.nisum.foodcourt.service;

import com.nisum.foodcourt.entity.PaymentTransaction;
import com.nisum.foodcourt.entity.User;
import com.nisum.foodcourt.entity.Wallet;


public interface PaymentTransactionService {

    PaymentTransaction createOrderPaymentTransaction(double orderAmount, User user);

    Wallet createUserWallet();
}
