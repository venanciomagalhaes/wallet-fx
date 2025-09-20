package br.com.gestorfinanceiro.application.dtos.transactions;

import java.math.BigDecimal;

public class CreateTransactionDto {

    private String name;
    private BigDecimal value;
    private boolean isInstallments;
    private Integer remainingInstallments;

    public CreateTransactionDto(
            String name,
            BigDecimal value,
            boolean isInstallments,
            Integer remainingInstallments
    ) {
        this.name = name;
        this.value = value;
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

}
