package com.ajay.eommerce.Model;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class PaymentInformation {
    @Column(name = "cardholder_name")
    private String cardHolderName;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "expiration_Date")
    private LocalDate expirationDate;
    @Column(name = "cvv")
    private String cvv;

}
