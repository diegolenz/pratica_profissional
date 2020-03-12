package lib.model.endereco.pais;

public class Pais {

    private Integer id;

    private String nome;
    private String ddi;
    private Boolean ativo;

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativado) {
        this.ativo = ativado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDdi(String ddi) {
        this.ddi = ddi;
    }

    public String getDdi() {
        return ddi;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
