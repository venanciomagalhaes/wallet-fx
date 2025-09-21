package br.com.walletfx.application.dtos.transactions;

import java.time.LocalDate;

public class GetAllInIntervalDto {
    private final LocalDate initialDate;
    private final LocalDate finallDate;

    public GetAllInIntervalDto(LocalDate initialDate, LocalDate finallDate) {
        this.initialDate = initialDate;
        this.finallDate = finallDate;
    }

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public LocalDate getFinallDate() {
        return finallDate;
    }
}
