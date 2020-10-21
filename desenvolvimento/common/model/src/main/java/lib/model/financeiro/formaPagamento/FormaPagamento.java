package lib.model.financeiro.formaPagamento;


import lib.model.interno.Funcionario;

import java.util.Date;

public class FormaPagamento {

    Integer id;

    private String nome;

    private Boolean ativo;

    private Date dataCadastro;

    private Date dataUltAlteracao;

    private Funcionario funcionarioCadastro;

    private Funcionario funcionarioUltimaAlteracao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataUltAlteracao() {
        return dataUltAlteracao;
    }

    public void setDataUltAlteracao(Date dataUltAlteracao) {
        this.dataUltAlteracao = dataUltAlteracao;
    }

    public Funcionario getFuncionarioCadastro() {
        return funcionarioCadastro;
    }

    public void setFuncionarioCadastro(Funcionario funcionarioCadastro) {
        this.funcionarioCadastro = funcionarioCadastro;
    }

    public Funcionario getFuncionarioUltimaAlteracao() {
        return funcionarioUltimaAlteracao;
    }

    public void setFuncionarioUltimaAlteracao(Funcionario funcionarioUltimaAlteracao) {
        this.funcionarioUltimaAlteracao = funcionarioUltimaAlteracao;
    }

    @Override
    public String toString() {
        return  nome ;
    }
}
