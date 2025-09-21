package br.com.gestorfinanceiro.application.dtos.transactions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class UpdateTransactionDto {

    private Long id;
    private UUID uuid;
    private String name;
    private BigDecimal value;
    private  LocalDate date;
    private boolean isInstallments;
    private Integer remainingInstallments;

    public UpdateTransactionDto(
            Long id,
            UUID uuid,
            String name,
            BigDecimal value, LocalDate data,
            boolean isInstallments,
            Integer remainingInstallments
    ) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.value = value;
        this.date = data;
        this.isInstallments = isInstallments;
        this.remainingInstallments = remainingInstallments;
    }

    public UpdateTransactionDto(UUID uuid, String name, BigDecimal value, LocalDate data, boolean isInstallment, int remaining) {
        this.uuid = uuid;
        this.name = name;
        this.value = value;
        this.date = data;
        this.isInstallments = isInstallment;
        this.remainingInstallments = remaining;
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

    public LocalDate getDate() {
        return date;
    }
}
