package br.com.walletfx.presentation.enums;

public enum ErrorMessagesEnum {

    MSG_ERROR("Erro"),
    MSG_HEADER_ERROR("Ocorreu um erro inesperado"),
    MSG_CONTENT_TRY_AGAIN("Por favor, verifique os dados e tente novamente."),
    MSG_ERROR_OPEN_MODAL_HEADER("Falha ao abrir o modal"),
    MSG_ERROR_UPDATE_HEADER("Falha ao atualizar"),
    MSG_ERROR_COPY_HEADER("Falha ao copiar"),
    MSG_ERROR_DELETE_HEADER("Falha ao excluir");

    private final String value;

    ErrorMessagesEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
