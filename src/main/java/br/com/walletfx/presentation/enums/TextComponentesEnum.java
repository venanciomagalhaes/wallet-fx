package br.com.walletfx.presentation.enums;

public enum TextComponentesEnum {

    MSG_DELETE_CONFIRM_TITLE("Confirmar exclusão"),
    MSG_DELETE_CONFIRM_HEADER("Deseja realmente excluir esta transação?"),
    MSG_DELETE_CONFIRM_CONTENT("Transação: "),

    MSG_LABEL_NAME("Nome:"),
    MSG_LABEL_VALUE("Valor:"),
    MSG_LABEL_INSTALLMENT("Parcelado?"),
    MSG_LABEL_REMAINING("Parcelas restantes:"),
    MSG_LABEL_DATE("Data:"),

    MSG_BUTTON_SAVE("Salvar"),
    MSG_BUTTON_EDIT("Editar"),
    MSG_BUTTON_DELETE("Excluir"),
    MSG_BUTTON_COPY("Copiar"),

    YES("Sim"),
    NO("Não"),
    TITLE_EDIT_TRANSACTION("Editar Transação"),
    TITLE_COPY_TRANSACTION("Copiar Transação"),
    MSG_TITLE_ERROR("Ops"),
    MSG_CONTENT_DB_ERROR("Verifique a conexão com o banco de dados.");

    private final String value;

    TextComponentesEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
