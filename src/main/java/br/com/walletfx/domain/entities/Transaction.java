package br.com.walletfx.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {


    private final Long id;
    private final UUID uuid;
    private final String name;
    private final BigDecimal value;
    private final LocalDate date;
    private final boolean isInstallments;
    private final Integer remainingInstallments;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Transaction(
            Long id,
            UUID uuid,
            String name,
            BigDecimal value, LocalDate date,
            boolean isInstallments,
            Integer remainingInstallments,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.value = value;
        this.date = date;
        this.isInstallments = isInstallments;
        this.remainingInstallments = remainingInstallments;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public LocalDate getDate() {
        return date;
    }
}
