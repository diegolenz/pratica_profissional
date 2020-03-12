package lib.model.pessoa.cliente;

import lib.model.financeiro.CondicaoPagamento.CondicaoPagamento;
import lib.model.pessoa.Pessoa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Cliente extends Pessoa {

    private Date dataCadastro;

    private Date dataUltAlteracao;

    private List<CondicaoPagamento> condicaoPagamentos;

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

    public List<CondicaoPagamento> getCondicaoPagamentos() {
        if (condicaoPagamentos==null){
            condicaoPagamentos = new ArrayList<>();
        }
        return condicaoPagamentos;
    }

    public void setCondicaoPagamentos(List<CondicaoPagamento> condicaoPagamentos) {
        this.condicaoPagamentos = condicaoPagamentos;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
