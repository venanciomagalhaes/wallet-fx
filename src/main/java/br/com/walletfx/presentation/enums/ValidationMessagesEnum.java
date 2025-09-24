package br.com.walletfx.presentation.enums;

public enum ValidationMessagesEnum {

    MSG_INVALID_NAME_HEADER("Nome inválido"),
    MSG_INVALID_NAME_CONTENT("O campo Nome é obrigatório."),
    MSG_INVALID_VALUE_HEADER("Valor inválido"),
    MSG_INVALID_VALUE_CONTENT("O campo Valor é obrigatório e deve ser numérico."),
    MSG_INVALID_INSTALLMENT_HEADER("Opção inválida"),
    MSG_INVALID_INSTALLMENT_CONTENT("Informe a quantidade de parcelas restantes corretamente."),
    MSG_INVALID_INSTALLMENT_QUANTITY("Somente é possível informar parcelas restantes em compras parceladas."),
    MSG_INVALID_INSTALLMENT_ZERO_CONTENT("A quantidade de parcelas deve ser maior que zero."),
    MSG_INVALID_DATE_HEADER("Data inválida"),
    MSG_INVALID_DATE_CONTENT("O campo Data é obrigatório."),
    MSG_INVALID_INITIAL_DATE_CONTENT("O campo data inicial é obrigatório"),
    MSG_INVALID_FINAL_DATE_CONTENT("O campo data final é obrigatório"),
    MSG_INVALID_BALANCE_VALUE("O campo de receita deve ser um número válido"),
    MSG_BALANCE_VALUE_ERROR("O valor de receita deve ser maior que zero");

    private final String value;

    ValidationMessagesEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
