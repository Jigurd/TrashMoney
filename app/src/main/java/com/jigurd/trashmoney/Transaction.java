package com.jigurd.trashmoney;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

//contains information about one financial transaction, namely time of transfer, name of recipient,
//amount transferred and the balance afterwards.

class Transaction implements Serializable
{
    private Integer mAmount;
    private Integer mBalanceAfter;
    private String mRecipient;
    private LocalDateTime mTransferTime;

    Transaction(String recipient, int amount, int balance)
    {
        this.mTransferTime = LocalDateTime.now();
        this.mAmount = amount;
        this.mRecipient = recipient;
        this.mBalanceAfter = balance;
    }

    //returns amount formatted as a currency, aka float with two decimals
    String displayAmount()
    {
        return String.format(Locale.ENGLISH, "%.2f",(float)this.mAmount/100.0);
    }

    //returns balanceAfter formatted as a currency aka float with two decimals
    String displayBalanceAfter()
    {
        return String.format(Locale.ENGLISH, "%.2f",(float)this.mBalanceAfter/100.0);
    }

    String getRecipient()
    {
        return this.mRecipient;
    }
    //returns TransferTime
    String getTransferTime()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return this.mTransferTime.format(formatter);
    }
}
