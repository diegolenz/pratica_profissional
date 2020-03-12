package lib.model.financeiro.caixa;

import lib.model.interno.Funcionario;

import java.util.Date;

public class Caixa {
    public Caixa(){
    }

    private Integer id;

    private String nome;

    private Date dataCadastro;

    private Date dataUltimaAlteraocao;

    private boolean aberto;

    private boolean ativo;

    private Funcionario funcionarioCadastro;

    private Funcionario funcionarioAtualizacao;

    public Funcionario getFuncionarioAtualizacao() {
        return funcionarioAtualizacao;
    }

    public void setFuncionarioAtualizacao(Funcionario funcionarioAtualizacao) {
        this.funcionarioAtualizacao = funcionarioAtualizacao;
    }

    public Funcionario getFuncionarioCadastro() {
        return funcionarioCadastro;
    }

    public void setFuncionarioCadastro(Funcionario funcionarioCadastro) {
        this.funcionarioCadastro = funcionarioCadastro;
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

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataUltimaAlteraocao() {
        return dataUltimaAlteraocao;
    }

    public void setDataUltimaAlteraocao(Date dataUltimaAlteraocao) {
        this.dataUltimaAlteraocao = dataUltimaAlteraocao;
    }

    public boolean isAberto() {
        return aberto;
    }

    public void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getStatusCompleto(){
        if (!ativo){
            return "Desativado";
        } else {
            if (aberto)
                return "Aberto";
            else return  "Fechado";
        }
    }

}
