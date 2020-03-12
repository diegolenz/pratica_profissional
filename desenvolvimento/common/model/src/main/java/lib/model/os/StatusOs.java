package lib.model.os;

public enum StatusOs {
    CANCELADA("Cancelada"),
    EM_ANDAMENTO("Em andamento"),
    CONCLUIDA("Concluida");
    private final String descricao;

    StatusOs(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }


}
