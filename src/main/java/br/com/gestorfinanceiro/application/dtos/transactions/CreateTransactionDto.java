package br.com.gestorfinanceiro.application.dtos.transactions;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateTransactionDto {

    private String name;
    private BigDecimal value;
    private  LocalDate date;
    private boolean isInstallments;
    private Integer remainingInstallments;

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
