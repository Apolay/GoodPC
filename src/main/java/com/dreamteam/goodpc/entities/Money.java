package com.dreamteam.goodpc.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Currency;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Money implements Comparable<Money> {
    @Column(name = "money_amount", nullable = false)
    private Long moneyAmount;

    @Pattern(regexp = "[A-Z]{3}", message = "Currency must contains 3 upper case letters!")
    @Column(nullable = false)
    private Currency currency = Currency.getInstance(Locale.US);

    public Money(Long moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    @Override
    public int compareTo(Money o) {
        //TODO: add converter
        return moneyAmount.compareTo(o.moneyAmount);
    }
}
