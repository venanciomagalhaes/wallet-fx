module br.com.gestorfinanceiro {
    requires javafx.controls;
    requires javafx.fxml;


    opens br.com.gestorfinanceiro to javafx.fxml;
    exports br.com.gestorfinanceiro;
}