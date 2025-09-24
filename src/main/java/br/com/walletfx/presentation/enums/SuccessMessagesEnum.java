package br.com.walletfx.presentation.enums;

public enum SuccessMessagesEnum {

    MSG_SUCCESS("Sucesso!"),
    MSG_SUCCESS_CREATE_HEADER("Transação criada"),
    MSG_SUCCESS_CREATE_CONTENT("A transação foi criada com sucesso."),
    MSG_SUCCESS_UPDATE_HEADER("Transação atualizada"),
    MSG_SUCCESS_UPDATE_CONTENT("A transação foi atualizada com sucesso."),
    MSG_SUCCESS_COPY_HEADER("Transação copiada"),
    MSG_SUCCESS_COPY_CONTENT("A transação foi copiada com sucesso."),
    MSG_SUCCESS_DELETE_HEADER("Transação excluída"),
    MSG_SUCCESS_DELETE_CONTENT("A transação foi removida com sucesso.");


    private final String value;

    SuccessMessagesEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
