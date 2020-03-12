package lib.model.financeiro;

public enum StatusConta {

    ATRASADO("Atrasada"),
    QUITADA("Quitada"),
    PENDENTE("Pendete"),
    PAGA_COM_ATRASO("Paga com atraso");

    private String descricao;

    StatusConta(String descricao) {
        this.descricao=descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
