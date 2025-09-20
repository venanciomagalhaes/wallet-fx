package br.com.gestorfinanceiro.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Transaction {

    private Long id;
    private UUID uuid;
    private String name;
    private BigDecimal value;
    private boolean isInstallments;
    private Integer remainingInstallments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Transaction(
            Long id,
            UUID uuid,
            String name,
            BigDecimal value,
            boolean isInstallments,
            Integer remainingInstallments,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.value = value;
        this.isInstallments = isInstallments;
        this.remainingInstallments = remainingInstallments;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Transaction(
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public boolean isInstallments() {
        return isInstallments;
    }

    public void setInstallments(boolean installments) {
        isInstallments = installments;
    }

    public Integer getRemainingInstallments() {
        return remainingInstallments;
    }

    public void setRemainingInstallments(Integer remainingInstallments) {
        this.remainingInstallments = remainingInstallments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", isInstallments=" + isInstallments +
                ", remainingInstallments=" + remainingInstallments +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
