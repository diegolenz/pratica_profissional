package lib.model.endereco.pais;

import lib.model.interno.Funcionario;

import java.util.Date;

public class Pais {

    private Integer id;

    private String nome;
    private String ddi;
    private Boolean ativo;


    private Date dataCadastro;

    private Date dataUltimaAlteracao;

    private Integer funcionarioUltimaAtualizacao;

    private Integer funcionarioCadastro;

    public Integer getFuncionarioUltimaAtualizacao() {
        return funcionarioUltimaAtualizacao;
    }

    public void setFuncionarioUltimaAtualizacao(Integer funcionarioUltimaAtualizacao) {
        this.funcionarioUltimaAtualizacao = funcionarioUltimaAtualizacao;
    }

    public Integer getFuncionarioCadastro() {
        return funcionarioCadastro;
    }

    public void setFuncionarioCadastro(Integer funcionarioCadastro) {
        this.funcionarioCadastro = funcionarioCadastro;
    }

    //    private Funcionario funcionarioUltimaAtualizacao;
//
//    private Funcionario funcionarioCadastro;

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

//    public Funcionario getFuncionarioUltimaAtualizacao() {
//        return funcionarioUltimaAtualizacao;
//    }
//
//    public void setFuncionarioUltimaAtualizacao(Funcionario funcionarioUltimaAtualizacao) {
//        this.funcionarioUltimaAtualizacao = funcionarioUltimaAtualizacao;
//    }
//
//    public Funcionario getFuncionarioCadastro() {
//        return funcionarioCadastro;
//    }
//
//    public void setFuncionarioCadastro(Funcionario funcionarioCadastro) {
//        this.funcionarioCadastro = funcionarioCadastro;
//    }

}
