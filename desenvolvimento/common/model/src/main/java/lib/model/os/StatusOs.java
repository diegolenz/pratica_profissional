package lib.model.os;

public enum StatusOs {
    TODOS("Todos"),
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

    @Override
    public String toString() {
        return descricao;
    }
}
