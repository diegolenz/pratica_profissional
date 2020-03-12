package lib.model.endereco.estado;


import lib.model.endereco.pais.Pais;


public  class Estado implements Comparable<Estado> {


        private Integer id;

        private String nome;

        private String sigla;

        private Pais pais;

        private Boolean ativo;





    public Estado() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }


    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        Estado other = (Estado) obj;
        if (this.getId() == null || other.getId() == null) {
            return super.equals(obj);
        }

        return this.getId().equals(other.getId());

    }

    @Override
    public String toString() {
        return nome;
    }


    @Override
    public int compareTo(Estado o) {
        return nome.compareTo(o.nome);
    }
}
