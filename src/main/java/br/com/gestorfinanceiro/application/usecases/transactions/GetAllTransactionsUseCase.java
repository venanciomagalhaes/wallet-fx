package br.com.gestorfinanceiro.application.usecases.transactions;

import br.com.gestorfinanceiro.application.repositories.transactions.TransactionRepositoryInterface;
import br.com.gestorfinanceiro.domain.entities.Transaction;

import java.util.List;

public class GetAllTransactionsUseCase {
    private final TransactionRepositoryInterface repository;

    public GetAllTransactionsUseCase(TransactionRepositoryInterface repository) {
        this.repository = repository;
    }

    public List<Transaction> handle(){
        return this.repository.getAll();
    }

}
