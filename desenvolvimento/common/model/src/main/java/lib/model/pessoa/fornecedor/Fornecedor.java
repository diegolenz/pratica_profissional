package lib.model.pessoa.fornecedor;

import lib.model.financeiro.CondicaoPagamento.CondicaoPagamento;
import lib.model.pessoa.Pessoa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fornecedor extends Pessoa {
    private Date dataCadastro;

    private Date dataUltAlteracao;

    private List<CondicaoPagamento> condicoesPagamentos;

    private Boolean ativo;

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
}
