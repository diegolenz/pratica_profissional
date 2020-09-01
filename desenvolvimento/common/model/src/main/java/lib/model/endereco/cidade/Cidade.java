package lib.model.endereco.cidade;

import lib.model.endereco.estado.Estado;
import lib.model.interno.Funcionario;

import java.util.Date;


public class Cidade implements Comparable<Cidade> {


    private Integer id;

    private String nome;

    private String codigoIbge;

    private String DDD;

    private Estado estado;

    private Boolean ativo;


    private Date dataCadastro;

    private Date dataUltimaAlteracao;

    private Funcionario funcionarioUltimaAtualizacao;

    private Funcionario funcionarioCadastro;

    public Cidade() {
        ativo = true;
        nome = "";
        DDD = "";
        estado = new Estado();
        codigoIbge = "";
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
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

    public String getCodigoIbge() {
        return codigoIbge;
    }

    public void setCodigoIbge(String codigoIbge) {
        this.codigoIbge = codigoIbge;
    }

    public String getDDD() {
        return DDD;
    }

    public void setDDD(String ddd) {
        this.DDD = ddd;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getNomeCompleto() {
        return nome + ", " + estado.getSigla();
    }


    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataUltimaAlteracao() {
        return dataUltimaAlteracao;
    }

    public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
        this.dataUltimaAlteracao = dataUltimaAlteracao;
    }

    public Funcionario getFuncionarioUltimaAtualizacao() {
        return funcionarioUltimaAtualizacao;
    }

    public void setFuncionarioUltimaAtualizacao(Funcionario funcionarioUltimaAtualizacao) {
        this.funcionarioUltimaAtualizacao = funcionarioUltimaAtualizacao;
    }

    public Funcionario getFuncionarioCadastro() {
        return funcionarioCadastro;
    }

    public void setFuncionarioCadastro(Funcionario funcionarioCadastro) {
        this.funcionarioCadastro = funcionarioCadastro;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        Cidade other = (Cidade) obj;
        if (this.getId() == null || other.getId() == null) {
            return super.equals(obj);
        }

        return this.getId().equals(other.getId());

    }

    @Override
    public String toString() {
        return this.nome;
    }

    @Override
    public int compareTo(Cidade o) {
        return nome.compareTo(o.nome);
    }
}


