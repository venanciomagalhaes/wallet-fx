package br.com.walletfx.application.dtos.transactions;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateTransactionDto {

    private final String name;
    private final BigDecimal value;
    private final LocalDate date;
    private final boolean isInstallments;
    private final Integer remainingInstallments;

    public CreateTransactionDto(
            String name,
            BigDecimal value, LocalDate date,
            boolean isInstallments,
            Integer remainingInstallments
    ) {
        this.name = name;
        this.value = value;
        this.date = date;
        this.isInstallments = isInstallments;
        this.remainingInstallments = remainingInstallments;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }


    public boolean isInstallments() {
        return isInstallments;
    }


    public Integer getRemainingInstallments() {
        return remainingInstallments;
    }

    public LocalDate getDate() {
        return date;
    }
}
