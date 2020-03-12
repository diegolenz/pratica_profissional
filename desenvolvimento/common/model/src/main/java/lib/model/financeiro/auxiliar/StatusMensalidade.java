package lib.model.financeiro.auxiliar;

public enum StatusMensalidade {

    ATRASADO("ATRASADO"),
    QUITADA("QUITADA"),
    CANCELADO("CANCELADO"),
    PENDENTE("PENDENTE");



    private String descricao;

    StatusMensalidade(String descricao) {
        this.descricao=descricao;
    }
}
