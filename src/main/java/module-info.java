module br.com.walletfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens br.com.walletfx to javafx.fxml, javafx.base;
    opens br.com.walletfx.application.exceptions.transactions to javafx.fxml, javafx.base;
    opens br.com.walletfx.application.usecases.transactions to javafx.fxml, javafx.base;
    opens br.com.walletfx.application.repositories.transactions to javafx.fxml, javafx.base;
    opens br.com.walletfx.application.dtos.transactions to javafx.fxml, javafx.base;
    opens br.com.walletfx.infrastructure.database to javafx.fxml, javafx.base;
    opens br.com.walletfx.infrastructure.repositories to javafx.fxml, javafx.base;
    opens br.com.walletfx.presentation.controllers to javafx.fxml, javafx.base;
    opens br.com.walletfx.domain.entities to javafx.fxml, javafx.base;
    opens br.com.walletfx.domain.exceptions to javafx.fxml, javafx.base;
    exports br.com.walletfx;
}