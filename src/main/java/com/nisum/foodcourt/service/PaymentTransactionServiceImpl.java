package com.nisum.foodcourt.service;

import com.nisum.foodcourt.entity.PaymentTransaction;
import com.nisum.foodcourt.entity.User;
import com.nisum.foodcourt.entity.Wallet;
import com.nisum.foodcourt.repository.PaymentTransactionRepository;
import com.nisum.foodcourt.repository.WalletRepository;
import com.nisum.foodcourt.resource.constant.PaymentPurposeType;
import com.nisum.foodcourt.resource.constant.TransactionStatus;
import com.nisum.foodcourt.resource.constant.TransactionType;
import com.nisum.foodcourt.resource.constant.UserPaymentCycleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentTransactionServiceImpl implements PaymentTransactionService {

    private final PaymentTransactionRepository paymentTransactionRepository;

    private final WalletRepository walletRepository;

    private static final double ZERO_BALANCE = (double) 0;

    @Override
    @Transactional
    public PaymentTransaction createOrderPaymentTransaction(double orderAmount, User user) {

        Wallet userWallet = user.getWallet();
        userWallet.setBalance(userWallet.getBalance() - orderAmount);
        walletRepository.save(userWallet);

        PaymentTransaction paymentTransaction = PaymentTransaction.builder()
                .amount(orderAmount)
                .paymentPurposeType(PaymentPurposeType.LUNCH_ORDER)
                .transactionType(TransactionType.DEBIT)
                .transactionStatus(TransactionStatus.SUCCESS)
                .wallet(userWallet)
                .build();

        return paymentTransactionRepository.save(paymentTransaction);
    }

    @Override
    @Transactional
    public Wallet createUserWallet() {

        Wallet wallet = Wallet.builder()
                .balance(ZERO_BALANCE)
                .cycleStartDate(LocalDate.now())
                .paymentCycleType(UserPaymentCycleType.DAILY)
                .build();

        return walletRepository.save(wallet);
    }

}
