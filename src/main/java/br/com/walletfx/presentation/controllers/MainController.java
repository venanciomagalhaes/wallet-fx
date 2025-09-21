package br.com.walletfx.presentation.controllers;

import br.com.walletfx.application.dtos.transactions.CreateTransactionDto;
import br.com.walletfx.application.dtos.transactions.GetAllInIntervalDto;
import br.com.walletfx.application.dtos.transactions.UpdateTransactionDto;
import br.com.walletfx.application.repositories.transactions.TransactionRepositoryInterface;
import br.com.walletfx.application.usecases.transactions.*;
import br.com.walletfx.domain.entities.Transaction;
import br.com.walletfx.infrastructure.database.DatabaseConnection;
import br.com.walletfx.infrastructure.database.Migrations;
import br.com.walletfx.infrastructure.repositories.TransactionRepository;
import br.com.walletfx.presentation.exceptions.InvalidArgumentException;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

public class MainController implements Initializable {

    private static final String MSG_SUCCESS = "Sucesso!";
    private static final String MSG_SUCCESS_CREATE_HEADER = "Transação criada";
    private static final String MSG_SUCCESS_CREATE_CONTENT = "A transação foi criada com sucesso.";
    private static final String MSG_SUCCESS_UPDATE_HEADER = "Transação atualizada";
    private static final String MSG_SUCCESS_COPY_HEADER = "Transação copiada";
    private static final String MSG_SUCCESS_UPDATE_CONTENT = "A transação foi atualizada com sucesso.";
    private static final String MSG_SUCCESS_COPY_CONTENT = "A transação foi copiada com sucesso.";
    private static final String MSG_SUCCESS_DELETE_HEADER = "Transação excluída";
    private static final String MSG_SUCCESS_DELETE_CONTENT = "A transação foi removida com sucesso.";
    private static final String MSG_ERROR = "Erro";
    private static final String MSG_HEADER_ERROR = "Ocorreu um erro inesperado";
    private static final String MSG_CONTENT_TRY_AGAIN = "Por favor, verifique os dados e tente novamente.";
    private static final String MSG_ERROR_OPEN_MODAL_HEADER = "Falha ao abrir o modal";
    private static final String MSG_ERROR_UPDATE_HEADER = "Falha ao atualizar";
    private static final String MSG_ERROR_COPY_HEADER = "Falha ao copiar";
    private static final String MSG_ERROR_DELETE_HEADER = "Falha ao excluir";
    private static final String MSG_DELETE_CONFIRM_TITLE = "Confirmar exclusão";
    private static final String MSG_DELETE_CONFIRM_HEADER = "Deseja realmente excluir esta transação?";
    private static final String MSG_DELETE_CONFIRM_CONTENT = "Transação: ";
    private static final String MSG_INVALID_NAME_HEADER = "Nome inválido";
    private static final String MSG_INVALID_NAME_CONTENT = "O campo Nome é obrigatório.";
    private static final String MSG_INVALID_VALUE_HEADER = "Valor inválido";
    private static final String MSG_INVALID_VALUE_CONTENT = "O campo Valor é obrigatório e deve ser numérico.";
    private static final String MSG_INVALID_INSTALLMENT_HEADER = "Opção inválida";
    private static final String MSG_INVALID_INSTALLMENT_CONTENT = "Informe a quantidade de parcelas restantes corretamente.";
    private static final String MSG_INVALID_INSTALLMENT_QUANTITY = "Somente é possível informar parcelas restantes em compras parceladas.";
    private static final String MSG_INVALID_INSTALLMENT_ZERO_CONTENT = "A quantidade de parcelas deve ser maior que zero.";
    private static final String MSG_INVALID_DATE_HEADER = "Data inválida";
    private static final String MSG_INVALID_DATE_CONTENT = "O campo Data é obrigatório.";
    private static final String MSG_LABEL_NAME = "Nome:";
    private static final String MSG_LABEL_VALUE = "Valor:";
    private static final String MSG_LABEL_INSTALLMENT = "Parcelado?";
    private static final String MSG_LABEL_REMAINING = "Parcelas restantes:";
    private static final String MSG_LABEL_DATE = "Data:";
    private static final String MSG_BUTTON_SAVE = "Salvar";
    private static final String MSG_BUTTON_EDIT = "Editar";
    private static final String MSG_BUTTON_DELETE = "Excluir";
    private static final String MSG_BUTTON_COPY = "Copiar";
    private static final String YES = "Sim";
    private static final String NO = "Não";
    private static final String TITLE_EDIT_TRANSACTION = "Editar Transação";
    private static final String TITLE_COPY_TRANSACTION = "Copiar Transação";
    private static final String MSG_TITLE_ERROR = "Ops";
    private static final String MSG_CONTENT_DB_ERROR = "Verifique a conexão com o banco de dados.";
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final String MSG_BALANCE_VALUE_ERROR = "O valor de receita deve ser maior que zero";
    private static final String MSG_INVALID_BALANCE_VALUE = "O campo de receita deve ser um número válido";
    private static final String MSG_INVALID_INITIAL_DATE_CONTENT = "O campo data inicial é obrigatório";
    private static final String MSG_INVALID_FINAL_DATE_CONTENT = "O campo data final é obrigatório";

    @FXML public DatePicker dateValue;
    @FXML public Button btnFilterTransactions;
    @FXML public DatePicker initialDate;
    @FXML public DatePicker finalDate;
    @FXML public Button calculateAmount;
    @FXML public Button btnFilterClear;
    @FXML private TableColumn<Transaction, Void> columnActions;
    @FXML private TableColumn<Transaction, Long> columnId;
    @FXML private TableColumn<Transaction, String> columnDescription;
    @FXML private TableColumn<Transaction, BigDecimal> columnValue;
    @FXML private TableColumn<Transaction, Boolean> columnIsInstallment;
    @FXML private TableColumn<Transaction, LocalDate> columnDate;
    @FXML private TableColumn<Transaction, Integer> columnRemaningInstallment;
    @FXML private ChoiceBox<String> isInstallmentChoiceBox;
    @FXML private TextField nameField;
    @FXML private TextField remainingInstallments;
    @FXML private TableView<Transaction> transactionsTable;
    @FXML private TextField valueField;
    @FXML private TextField totalInPeriodValue;
    @FXML public TextField budgetValue;
    @FXML public Label finalAmountText;

    private Connection connection;
    private CreateTransactionUseCase createTransactionUseCase;
    private GetAllTransactionsUseCase getAllTransactionsUseCase;
    private DeleteTransactionUseCase deleteTransactionUseCase;
    private UpdateTransactionUseCase updateTransactionUseCase;
    private GetAllInIntervalUseCase getAllInIntervalUseCase;

    private final ObservableList<Transaction> transactionObservableList = FXCollections.observableArrayList();
    private final ObjectProperty<BigDecimal> totalInPeriod = new SimpleObjectProperty<>(BigDecimal.ZERO);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.createDependencies();
            this.runMigrations();
            this.prepareChoiceBox();
            this.budgetValue.setText("");
            this.prepareTableView();
            this.configureDatePickers();
            this.prepareActionsColumn();
            this.bindTotalToTextField();
            this.updateTransactionsDataTable();
        } catch (SQLException e) {
            showAlert(MSG_TITLE_ERROR, MSG_HEADER_ERROR, MSG_CONTENT_DB_ERROR, Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert(MSG_TITLE_ERROR, MSG_HEADER_ERROR, MSG_CONTENT_TRY_AGAIN, Alert.AlertType.ERROR);
        }
    }

    private void createDependencies() throws SQLException, IOException {
        this.connection = DatabaseConnection.getInstance();
        TransactionRepositoryInterface transactionRepository = new TransactionRepository(this.connection);
        this.createTransactionUseCase = new CreateTransactionUseCase(transactionRepository);
        this.getAllTransactionsUseCase = new GetAllTransactionsUseCase(transactionRepository);
        this.deleteTransactionUseCase = new DeleteTransactionUseCase(transactionRepository);
        this.updateTransactionUseCase = new UpdateTransactionUseCase(transactionRepository);
        this.getAllInIntervalUseCase = new GetAllInIntervalUseCase(transactionRepository);
    }

    private void prepareTableView() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnDate.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
            }
        });
        columnValue.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(BigDecimal item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : "R$ " + item.setScale(2, RoundingMode.HALF_UP).toString().replace(".", ","));
            }
        });
        columnIsInstallment.setCellValueFactory(new PropertyValueFactory<>("installments"));
        columnIsInstallment.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : (item ? YES : NO));
            }
        });
        columnRemaningInstallment.setCellValueFactory(new PropertyValueFactory<>("remainingInstallments"));
        columnRemaningInstallment.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : (item > 0 ? item.toString() : "N/A"));
            }
        });
        transactionsTable.setItems(transactionObservableList);
        transactionObservableList.addListener((ListChangeListener<Transaction>) change -> {
            BigDecimal sum = BigDecimal.ZERO;
            for (Transaction t : transactionObservableList) {
                sum = sum.add(t.getValue());
            }
            totalInPeriod.set(sum);
        });
    }

    private void bindTotalToTextField() {
        totalInPeriodValue.textProperty().bind(
                Bindings.createStringBinding(
                        () -> "R$ " + totalInPeriod.get().setScale(2, RoundingMode.HALF_UP).toString().replace(".", ","),
                        totalInPeriod
                )
        );
    }

    private void configureDatePickers() {
        Locale.setDefault(new Locale("pt", "BR"));
        javafx.util.StringConverter<LocalDate> dateConverter = new javafx.util.StringConverter<>() {
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? dateFormatter.format(date) : "";
            }
            @Override
            public LocalDate fromString(String string) {
                return (string == null || string.isEmpty()) ? null : LocalDate.parse(string, dateFormatter);
            }
        };
        dateValue.setPromptText(DATE_PATTERN);
        dateValue.setConverter(dateConverter);
        initialDate.setPromptText(DATE_PATTERN);
        initialDate.setConverter(dateConverter);
        finalDate.setPromptText(DATE_PATTERN);
        finalDate.setConverter(dateConverter);
    }

    private void prepareChoiceBox() {
        isInstallmentChoiceBox.getItems().addAll(YES, NO);
        isInstallmentChoiceBox.setValue(NO);
    }

    private void prepareActionsColumn() {
        columnActions.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button(MSG_BUTTON_EDIT);
            private final Button deleteButton = new Button(MSG_BUTTON_DELETE);
            private final Button copyButton = new Button(MSG_BUTTON_COPY);
            private final HBox container = new HBox(5, editButton, deleteButton, copyButton);

            {
                container.setAlignment(Pos.CENTER);
                editButton.setOnAction(event -> openEditModal(getTableView().getItems().get(getIndex())));
                copyButton.setOnAction(event -> openCopyModal(getTableView().getItems().get(getIndex())));
                deleteButton.setOnAction(event -> showDeleteConfirmation(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : container);
            }
        });
    }

    private void showDeleteConfirmation(Transaction transaction) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(MSG_DELETE_CONFIRM_TITLE);
        alert.setHeaderText(MSG_DELETE_CONFIRM_HEADER);
        alert.setContentText(MSG_DELETE_CONFIRM_CONTENT + transaction.getName());
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deleteTransaction(transaction);
            }
        });
    }

    private void deleteTransaction(Transaction transaction) {
        try {
            deleteTransactionUseCase.handle(transaction.getUuid());
            updateTransactionsDataTable();
            showAlert(MSG_SUCCESS, MSG_SUCCESS_DELETE_HEADER, MSG_SUCCESS_DELETE_CONTENT, Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert(MSG_ERROR, MSG_ERROR_DELETE_HEADER, MSG_CONTENT_TRY_AGAIN, Alert.AlertType.ERROR);
        }
    }

    private void openEditModal(Transaction transaction) {
        try {
            Stage stage = new Stage();
            stage.setTitle(TITLE_EDIT_TRANSACTION);
            stage.initModality(Modality.APPLICATION_MODAL);

            TextField editNameField = new TextField(transaction.getName());
            TextField editValueField = new TextField(transaction.getValue().toString());
            DatePicker editDatePicker = new DatePicker(transaction.getDate());
            ChoiceBox<String> editIsInstallmentChoice = new ChoiceBox<>();
            editIsInstallmentChoice.getItems().addAll(YES, NO);
            editIsInstallmentChoice.setValue(transaction.isInstallments() ? YES : NO);
            TextField editRemainingInstallments = new TextField(String.valueOf(transaction.getRemainingInstallments()));

            Button saveButton = new Button(MSG_BUTTON_SAVE);
            saveButton.setOnAction(e -> {
                try {
                    UUID uuid = transaction.getUuid();
                    String name = editNameField.getText();
                    LocalDate date = editDatePicker.getValue();
                    BigDecimal value = validateValue(editValueField.getText().replace(",", "."));
                    boolean isInstallment = editIsInstallmentChoice.getValue().equals(YES);
                    int remaining = isInstallment ? validateRemaining(editRemainingInstallments.getText()) : 0;
                    if (!isInstallment && !editRemainingInstallments.getText().isEmpty() &&
                            Integer.parseInt(editRemainingInstallments.getText()) > 0) {
                        throw new InvalidArgumentException(MSG_INVALID_INSTALLMENT_CONTENT);
                    }

                    validateField(name.isBlank(), MSG_INVALID_NAME_HEADER, MSG_INVALID_NAME_CONTENT);
                    validateField(date == null, MSG_INVALID_DATE_HEADER, MSG_INVALID_DATE_CONTENT);

                    UpdateTransactionDto dto = new UpdateTransactionDto(uuid, name, value, date, isInstallment, remaining);
                    updateTransactionUseCase.handle(dto);
                    updateTransactionsDataTable();
                    showAlert(MSG_SUCCESS, MSG_SUCCESS_UPDATE_HEADER, MSG_SUCCESS_UPDATE_CONTENT, Alert.AlertType.INFORMATION);
                    stage.close();
                } catch (Exception ex) {
                    if (!(ex instanceof InvalidArgumentException)) {
                        showAlert(MSG_ERROR, MSG_ERROR_UPDATE_HEADER, MSG_CONTENT_TRY_AGAIN, Alert.AlertType.ERROR);
                    }
                }
            });

            VBox vbox = new VBox(10,
                    new Label(MSG_LABEL_NAME), editNameField,
                    new Label(MSG_LABEL_VALUE), editValueField,
                    new Label(MSG_LABEL_DATE), editDatePicker,
                    new Label(MSG_LABEL_INSTALLMENT), editIsInstallmentChoice,
                    new Label(MSG_LABEL_REMAINING), editRemainingInstallments,
                    saveButton
            );
            vbox.setPadding(new Insets(10));
            stage.setScene(new Scene(vbox));
            stage.sizeToScene();
            stage.showAndWait();
        } catch (Exception e) {
            showAlert(MSG_ERROR, MSG_ERROR_OPEN_MODAL_HEADER, e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private void openCopyModal(Transaction transaction) {
        try {
            Stage stage = new Stage();
            stage.setTitle(TITLE_COPY_TRANSACTION);
            stage.initModality(Modality.APPLICATION_MODAL);

            TextField copyNameField = new TextField(transaction.getName());
            TextField copyValueField = new TextField(transaction.getValue().toString());
            DatePicker copyDatePicker = new DatePicker(transaction.getDate());
            ChoiceBox<String> copyIsInstallmentChoice = new ChoiceBox<>();
            copyIsInstallmentChoice.getItems().addAll(YES, NO);
            copyIsInstallmentChoice.setValue(transaction.isInstallments() ? YES : NO);
            TextField copyRemainingInstallments = new TextField(String.valueOf(transaction.getRemainingInstallments()));

            Button copyButton = new Button(MSG_BUTTON_COPY);
            copyButton.setOnAction(e -> {
                try {
                    UUID uuid = transaction.getUuid();
                    String name = copyNameField.getText();
                    LocalDate date = copyDatePicker.getValue();
                    BigDecimal value = validateValue(copyValueField.getText().replace(",", "."));
                    boolean isInstallment = copyIsInstallmentChoice.getValue().equals(YES);
                    int remaining = isInstallment ? validateRemaining(copyRemainingInstallments.getText()) : 0;
                    if (!isInstallment && !copyRemainingInstallments.getText().isEmpty() &&
                            Integer.parseInt(copyRemainingInstallments.getText()) > 0) {
                        throw new InvalidArgumentException(MSG_INVALID_INSTALLMENT_CONTENT);
                    }

                    validateField(name.isBlank(), MSG_INVALID_NAME_HEADER, MSG_INVALID_NAME_CONTENT);
                    validateField(date == null, MSG_INVALID_DATE_HEADER, MSG_INVALID_DATE_CONTENT);

                    CreateTransactionDto dto = new CreateTransactionDto(name, value, date, isInstallment, remaining);
                    createTransactionUseCase.handle(dto);
                    updateTransactionsDataTable();
                    showAlert(MSG_SUCCESS, MSG_SUCCESS_COPY_HEADER, MSG_SUCCESS_COPY_CONTENT, Alert.AlertType.INFORMATION);
                    stage.close();
                } catch (Exception ex) {
                    if (!(ex instanceof InvalidArgumentException)) {
                        showAlert(MSG_ERROR, MSG_ERROR_COPY_HEADER, MSG_CONTENT_TRY_AGAIN, Alert.AlertType.ERROR);
                    }
                }
            });

            VBox vbox = new VBox(10,
                    new Label(MSG_LABEL_NAME), copyNameField,
                    new Label(MSG_LABEL_VALUE), copyValueField,
                    new Label(MSG_LABEL_DATE), copyDatePicker,
                    new Label(MSG_LABEL_INSTALLMENT), copyIsInstallmentChoice,
                    new Label(MSG_LABEL_REMAINING), copyRemainingInstallments,
                    copyButton
            );
            vbox.setPadding(new Insets(10));
            stage.setScene(new Scene(vbox));
            stage.sizeToScene();
            stage.showAndWait();
        } catch (Exception e) {
            showAlert(MSG_ERROR, MSG_ERROR_OPEN_MODAL_HEADER, e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    public void createTransaction() {
        try {
            String name = nameField.getText();
            String valueTextString = valueField.getText().replace(",", ".");
            boolean isInstallment = isInstallmentChoiceBox.getValue().equals(YES);
            String remainingTextString = remainingInstallments.getText();
            LocalDate date = dateValue.getValue();

            validateField(name.isBlank(), MSG_INVALID_NAME_HEADER, MSG_INVALID_NAME_CONTENT);
            validateField(valueTextString.isEmpty(), MSG_INVALID_VALUE_HEADER, MSG_INVALID_VALUE_CONTENT);
            validateField(isInstallment && remainingTextString.isBlank(), MSG_INVALID_INSTALLMENT_HEADER, MSG_INVALID_INSTALLMENT_CONTENT);
            validateField(date == null, MSG_INVALID_DATE_HEADER, MSG_INVALID_DATE_CONTENT);

            BigDecimal value = validateValue(valueTextString);
            int remaining = isInstallment ? validateRemaining(remainingTextString) : 0;
            if (!isInstallment && !remainingTextString.isEmpty()) {
                throw new InvalidArgumentException(MSG_INVALID_INSTALLMENT_QUANTITY);
            }

            CreateTransactionDto dto = new CreateTransactionDto(name, value, date, isInstallment, remaining);
            createTransactionUseCase.handle(dto);
            updateTransactionsDataTable();
            showAlert(MSG_SUCCESS, MSG_SUCCESS_CREATE_HEADER, MSG_SUCCESS_CREATE_CONTENT, Alert.AlertType.INFORMATION);
            nameField.setText("");
            valueField.setText("");
            isInstallmentChoiceBox.setValue(NO);
            remainingInstallments.setText("");
            dateValue.setValue(null);
        } catch (InvalidArgumentException e) {
            showAlert(MSG_ERROR, MSG_TITLE_ERROR, e.getMessage(), Alert.AlertType.ERROR);
        }catch (Exception e) {
            showAlert(MSG_ERROR, MSG_HEADER_ERROR, MSG_CONTENT_TRY_AGAIN, Alert.AlertType.ERROR);
        }
    }

    private int validateRemaining(String remainingTextString) {
        try {
            int remaining = Integer.parseInt(remainingTextString);
            if (remaining <= 0) throw new InvalidArgumentException(MSG_INVALID_INSTALLMENT_ZERO_CONTENT);
            return remaining;
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException(MSG_INVALID_INSTALLMENT_CONTENT);
        }
    }

    private BigDecimal validateValue(String valueTextString) {
        try {
            return new BigDecimal(valueTextString);
        } catch (Exception e) {
            throw new InvalidArgumentException(MSG_INVALID_VALUE_CONTENT);
        }
    }

    private void validateField(boolean condition, String header, String content) {
        if (condition) throw new InvalidArgumentException(content);
    }

    private void showAlert(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void updateTransactionsDataTable() throws SQLException {
        if (initialDate.getValue() == null || finalDate.getValue() == null){
            List<Transaction> transactionList = getAllTransactionsUseCase.handle();
            transactionObservableList.setAll(transactionList);
            String budgetInput = budgetValue.getText().replace(",", ".");
            BigDecimal budgetAmount = new BigDecimal(budgetInput.isBlank() ? "0" : budgetInput);
            if (budgetAmount.compareTo(BigDecimal.ZERO) > 0){
                this.calculateFinalAmount();
            }
            return;
        }
        this.getTransactionsBetween();

    }

    public void clearTransactionsDataTable() throws SQLException {
        initialDate.setValue(null);
        finalDate.setValue(null);
        List<Transaction> transactionList = getAllTransactionsUseCase.handle();
        transactionObservableList.setAll(transactionList);
        String budgetInput = budgetValue.getText().replace(",", ".");
        BigDecimal budgetAmount = new BigDecimal(budgetInput.isBlank() ? "0" : budgetInput);
        if (budgetAmount.compareTo(BigDecimal.ZERO) > 0){
            this.calculateFinalAmount();
        }
    }

    @FXML
    private void calculateFinalAmount() {
        try {
            String budgetInput = budgetValue.getText().replace(",", ".");
            BigDecimal budgetAmount = new BigDecimal(budgetInput.isBlank() ? "0" : budgetInput);
            validateField(budgetAmount.compareTo(BigDecimal.ZERO) <= 0, MSG_TITLE_ERROR, MSG_BALANCE_VALUE_ERROR);
            BigDecimal finalAmount = budgetAmount.subtract(totalInPeriod.get());
            finalAmountText.setText("R$ " + finalAmount.setScale(2, RoundingMode.HALF_UP).toString().replace(".", ","));
        } catch (NumberFormatException e) {
            showAlert(MSG_ERROR, MSG_TITLE_ERROR, MSG_INVALID_BALANCE_VALUE, Alert.AlertType.ERROR);
        } catch (InvalidArgumentException e) {
            showAlert(MSG_ERROR, MSG_TITLE_ERROR, e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void getTransactionsBetween()  {
        try {
            validateField(initialDate.getValue() == null, MSG_INVALID_DATE_HEADER, MSG_INVALID_INITIAL_DATE_CONTENT);
            validateField(finalDate.getValue() == null, MSG_INVALID_DATE_HEADER, MSG_INVALID_FINAL_DATE_CONTENT);

            GetAllInIntervalDto dto = new GetAllInIntervalDto(initialDate.getValue(), finalDate.getValue());
            List<Transaction> transactionList = this.getAllInIntervalUseCase.handle(dto);
            transactionObservableList.setAll(transactionList);
            String budgetInput = budgetValue.getText().replace(",", ".");
            BigDecimal budgetAmount = new BigDecimal(budgetInput.isBlank() ? "0" : budgetInput);
            if (budgetAmount.compareTo(BigDecimal.ZERO) > 0){
                this.calculateFinalAmount();
            }
        } catch (NumberFormatException e) {
            showAlert(MSG_ERROR, MSG_TITLE_ERROR, MSG_INVALID_BALANCE_VALUE, Alert.AlertType.ERROR);
        } catch (InvalidArgumentException e) {
            showAlert(MSG_ERROR, MSG_TITLE_ERROR, e.getMessage(), Alert.AlertType.ERROR);
        }catch (Exception e) {
            showAlert(MSG_ERROR, MSG_HEADER_ERROR, MSG_CONTENT_TRY_AGAIN, Alert.AlertType.ERROR);
        }

    }

    private void runMigrations() throws SQLException {
        Migrations migrations = new Migrations(connection);
        migrations.run();
    }
}
