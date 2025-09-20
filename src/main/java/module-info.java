module br.com.gestorfinanceiro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens br.com.gestorfinanceiro to javafx.fxml, javafx.base;
    opens br.com.gestorfinanceiro.application.exceptions.transactions to javafx.fxml, javafx.base;
    opens br.com.gestorfinanceiro.application.usecases.transactions to javafx.fxml, javafx.base;
    opens br.com.gestorfinanceiro.application.repositories.transactions to javafx.fxml, javafx.base;
    opens br.com.gestorfinanceiro.application.dtos.transactions to javafx.fxml, javafx.base;
    opens br.com.gestorfinanceiro.infrastructure.database to javafx.fxml, javafx.base;
    opens br.com.gestorfinanceiro.infrastructure.repositories to javafx.fxml, javafx.base;
    opens br.com.gestorfinanceiro.presentation.controllers to javafx.fxml, javafx.base;
    opens br.com.gestorfinanceiro.domain.entities to javafx.fxml, javafx.base;
    opens br.com.gestorfinanceiro.domain.exceptions to javafx.fxml, javafx.base;
    exports br.com.gestorfinanceiro;
}