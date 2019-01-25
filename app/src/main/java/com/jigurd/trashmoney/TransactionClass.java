package com.jigurd.trashmoney;

import java.time.LocalDateTime;

public class TransactionClass
{
    private String recipient = "";
    private int amount = 0;
    private int balanceAfter = 0;
    private LocalDateTime transferTime;

    public void TransactionClass(String recipient, int amount, int balance)
    {
        //transferTime = LocalDateTime.now();
        this.amount = amount;
    }
}
