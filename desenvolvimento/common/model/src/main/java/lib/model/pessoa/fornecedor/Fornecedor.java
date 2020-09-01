package lib.model.pessoa.fornecedor;

import lib.model.financeiro.CondicaoPagamento.CondicaoPagamento;
import lib.model.interno.Funcionario;
import lib.model.pessoa.Pessoa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fornecedor extends Pessoa {
    private Date dataCadastro;

    private Date dataUltAlteracao;

    private List<CondicaoPagamento> condicoesPagamentos;

    private Boolean ativo;

    private Funcionario funcionarioCadastro;

    private Funcionario funcionarioUltimaAlteracao;

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

    public List<CondicaoPagamento> getCondicoesPagamentos() {
        if (condicoesPagamentos == null)
            condicoesPagamentos = new ArrayList<>();
        return condicoesPagamentos;
    }


    public void setCondicoesPagamentos(List<CondicaoPagamento> condicoesPagamentos) {
        this.condicoesPagamentos = condicoesPagamentos;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public void setFuncionarioCadastro(Funcionario funcionarioCadastro) {
        this.funcionarioCadastro = funcionarioCadastro;
    }

    public Funcionario getFuncionarioCadastro() {
        return funcionarioCadastro;
    }

    public Funcionario getFuncionarioUltimaAlteracao() {
        return funcionarioUltimaAlteracao;
    }

    public void setFuncionarioUltimaAlteracao(Funcionario funcionarioUltimaAlteracao) {
        this.funcionarioUltimaAlteracao = funcionarioUltimaAlteracao;
    }
}
