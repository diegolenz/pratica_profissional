package lib.model.comercial;

import lib.model.comercial.frete.TipoFrete;
import lib.model.financeiro.CondicaoPagamento.CondicaoPagamento;
import lib.model.financeiro.contas.ContaPagar;
import lib.model.interno.Funcionario;
import lib.model.pessoa.fornecedor.Fornecedor;
import util.Build;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Compra {

    public Compra() {
        this.valorFrete = 0D;
        this.valorSeguro = 0D;
        this.outrasDespesas = 0D;
    }

    private List<ItemProduto> itensProdutos ;

    private Integer numeroNota;

    private String modeloNota;

    private Integer numSerieNota;

    private boolean ativo;

    private Fornecedor fornecedor;

    private CondicaoPagamento condicaoPagamento;

    private List<ContaPagar> contas;

    private Funcionario funcionario;

    private Date dtEmisssao;

    private Date dtChegada;

    private TipoFrete tipoFrete;

    private Double valorFrete ;

    private Double valorSeguro ;

    private Double outrasDespesas;

    private String motivoCancelamento;

    public List<ItemProduto> getItensProdutos() {
        if (itensProdutos == null){
            itensProdutos = new ArrayList<>();
        }
        return itensProdutos;
    }

    public void setItensProdutos(List<ItemProduto> itensProdutos) {
        this.itensProdutos = itensProdutos;
    }

    public Integer getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(Integer numeroNota) {
        this.numeroNota = numeroNota;
    }

    public String getModeloNota() {
        return modeloNota;
    }

    public void setModeloNota(String modeloNota) {
        this.modeloNota = modeloNota;
    }

    public Integer getNumSerieNota() {
        return numSerieNota;
    }

    public void setNumSerieNota(Integer numSerieNota) {
        this.numSerieNota = numSerieNota;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Date getDtEmisssao() {
        return dtEmisssao;
    }

    public void setDtEmisssao(Date dtEmisssao) {
        this.dtEmisssao = dtEmisssao;
    }

    public Date getDtChegada() {
        return dtChegada;
    }

    public void setDtChegada(Date dtChegada) {
        this.dtChegada = dtChegada;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public CondicaoPagamento getCondicaoPagamento() {
        return condicaoPagamento;
    }

    public void setCondicaoPagamento(CondicaoPagamento condicaoPagamento) {
        this.condicaoPagamento = condicaoPagamento;
    }

    public List<ContaPagar> getContas() {
        return contas;
    }

    public void setContas(List<ContaPagar> contas) {
        this.contas = contas;
    }

    public TipoFrete getTipoFrete() {
        return tipoFrete;
    }

    public void setTipoFrete(TipoFrete tipoFrete) {
        this.tipoFrete = tipoFrete;
    }

    public Double getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(Double valorFrete) {
        this.valorFrete = valorFrete;
    }

    public Double getValorSeguro() {
        return valorSeguro;
    }

    public void setValorSeguro(Double valorSeguro) {
        this.valorSeguro = valorSeguro;
    }

    public Double getOutrasDespesas() {
        return outrasDespesas;
    }

    public void setOutrasDespesas(Double outrasDespesas) {
        this.outrasDespesas = outrasDespesas;
    }

    public Double getTotaisDescontos(){
        if (!getItensProdutos().isEmpty()){
            return this.itensProdutos.stream().mapToDouble(ItemProduto::getTotaisDescontos).sum();
        }
        return 0D;
    }

    public Double getTotaisAcrescimos(){
        if (!getItensProdutos().isEmpty()){
            return this.itensProdutos.stream().mapToDouble(ItemProduto::getTotaisAcrescimos).sum();
        }
        return 0D;
    }

    public Double getTotaisCustoUn(){
        if (!getItensProdutos().isEmpty()){
            return this.itensProdutos.stream().mapToDouble(ItemProduto::getTotaisCustoUn).sum();
        }
        return 0D;
    }

    public Double getTotalDespesas(){
        return outrasDespesas + valorSeguro  + valorFrete;
    }

    public void buildRateioItens(){
        if (tipoFrete.equals(TipoFrete.CIF))
            return;
        Double valorFinal=0D;
        Double totalItens = itensProdutos.stream().mapToDouble(ItemProduto::getTotaisCustoUn).sum();
        for (ItemProduto itemProduto : itensProdutos){
            Double perc = ((itemProduto.getValorUnitario() + itemProduto.getAcrescimoUnitario() - itemProduto.getDescontoUnitario()) * itemProduto.getQuantidade()) / totalItens;
            valorFinal = getTotalDespesas() * perc;
            valorFinal = valorFinal / itemProduto.getQuantidade();
            itemProduto.setValorRateio(Build.builDoubleDuasCasasDecimais(valorFinal));
           // itemProduto.setValorTotal(itemProduto.getValorUnitario() + valorFinal);
        }

    }

    public Double getTotalCompra(){
        Double custo = getTotaisCustoUn() + getOutrasDespesas() + getValorSeguro() + getValorFrete() ;
        return custo;
    }

    public String getMotivoCancelamento() {
        return motivoCancelamento;
    }

    public void setMotivoCancelamento(String motivoCancelamento) {
        this.motivoCancelamento = motivoCancelamento;
    }
}
