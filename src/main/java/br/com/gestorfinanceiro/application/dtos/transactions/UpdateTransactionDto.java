package br.com.gestorfinanceiro.application.dtos.transactions;

import java.math.BigDecimal;
import java.util.UUID;

public class UpdateTransactionDto {

    private Long id;
    private UUID uuid;
    private String name;
    private BigDecimal value;
    private boolean isInstallments;
    private Integer remainingInstallments;

    public UpdateTransactionDto(
            Long id,
            UUID uuid,
            String name,
            BigDecimal value,
            boolean isInstallments,
            Integer remainingInstallments
    ) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.value = value;
        this.isInstallments = isInstallments;
        this.remainingInstallments = remainingInstallments;
    }

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
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
