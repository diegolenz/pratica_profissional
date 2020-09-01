package lib.model.os;

import lib.model.comercial.ItemProduto;
import lib.model.comercial.VendaProduto;
import lib.model.comercial.VendaServico;
import lib.model.financeiro.CondicaoPagamento.CondicaoPagamento;
import lib.model.interno.Funcionario;
import lib.model.os.veiculo.modelo.Modelo;
import lib.model.pessoa.cliente.Cliente;
import lib.model.servico.ItemServico;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdemServico {

    public OrdemServico() {
        fechada = false;
        cancelada = false;
    }

    private Integer id;

    private List<ItemServico> itensServicos;

    private List<ItemProduto> itensProdutos;

    private VendaServico vendaServico;

    private VendaProduto vendaProduto;

    private Date dataCadastro;

    private Date datUltimaAlteracao;

    private Funcionario funcionarioCadastro;

    private Funcionario funcionarioUltimaAtualizacao;

    private CondicaoPagamento condicaoPagamento;

    private Boolean cancelada;

    private Boolean fechada;

    private Cliente cliente;

    private Funcionario responsavel;

    private Modelo modelo;

    private Double km;

    private String chassis;

    private String placa;

    private Integer ano;

    private String cor;

    private String descricaoProblema;

    private String descricaoConclusao;

    private String observacao;

    public StatusOs getStatus(){
        if (cancelada)
            return StatusOs.CANCELADA;
        if (fechada)
            return StatusOs.CONCLUIDA;
        return  StatusOs.EM_ANDAMENTO;
    }

    public Double getTotal(){
        Double total = 0D;
        total += getItensProdutos().stream().mapToDouble(ItemProduto::getTotaisCustoUn).sum();
        total += getItensServicos().stream().mapToDouble(ItemServico::getTotaisCustoUn).sum();
        return total;
    }

    public Double getTotalServicos(){
        Double total = 0D;
      //  total += getItensProdutos().stream().mapToDouble(ItemProduto::getTotaisCustoUn).sum();
        total += getItensServicos().stream().mapToDouble(ItemServico::getTotaisCustoUn).sum();
        return total;
    }

    public Double getTotalProdutos(){
        Double total = 0D;
        //  total += getItensProdutos().stream().mapToDouble(ItemProduto::getTotaisCustoUn).sum();
        total = getItensProdutos().stream().mapToDouble(ItemProduto::getTotaisCustoUn).sum();
        return total;
    }



    public Double getTotalAcrscimo(){
        Double total = 0D;
        total += getItensProdutos().stream().mapToDouble(ItemProduto::getTotaisAcrescimos).sum();
        total += getItensServicos().stream().mapToDouble(ItemServico::getTotaisAcrescimos).sum();
        return total;
    }

    public Double getTotalDescontos(){
        Double total = 0D;
        total += getItensProdutos().stream().mapToDouble(ItemProduto::getTotaisDescontos).sum();
        total += getItensServicos().stream().mapToDouble(ItemServico::getTotaisDescontos).sum();
        return total;
    }


    public Boolean getFechada() {
        return fechada;
    }

    public void setFechada(Boolean fechada) {
        this.fechada = fechada;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ItemServico> getItensServicos() {
        if (itensServicos == null)
            itensServicos = new ArrayList<>();
        return itensServicos;
    }

    public void setItensServicos(List<ItemServico> itensServicos) {
        this.itensServicos = itensServicos;
    }

    public List<ItemProduto> getItensProdutos() {
        if (itensProdutos == null)
            itensProdutos = new ArrayList<>();
        return itensProdutos;
    }

    public void setItensProdutos(List<ItemProduto> itensProdutos) {
        this.itensProdutos = itensProdutos;
    }

    public VendaServico getVendaServico() {
        return vendaServico;
    }

    public void setVendaServico(VendaServico vendaServico) {
        this.vendaServico = vendaServico;
    }

    public VendaProduto getVendaProduto() {
        return vendaProduto;
    }

    public void setVendaProduto(VendaProduto vendaProduto) {
        this.vendaProduto = vendaProduto;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDatUltimaAlteracao() {
        return datUltimaAlteracao;
    }

    public void setDatUltimaAlteracao(Date datUltimaAlteracao) {
        this.datUltimaAlteracao = datUltimaAlteracao;
    }

    public Funcionario getFuncionarioCadastro() {
        return funcionarioCadastro;
    }

    public void setFuncionarioCadastro(Funcionario funcionarioCadastro) {
        this.funcionarioCadastro = funcionarioCadastro;
    }

    public Funcionario getFuncionarioUltimaAtualizacao() {
        return funcionarioUltimaAtualizacao;
    }

    public void setFuncionarioUltimaAtualizacao(Funcionario funcionarioUltimaAtualizacao) {
        this.funcionarioUltimaAtualizacao = funcionarioUltimaAtualizacao;
    }

    public CondicaoPagamento getCondicaoPagamento() {
        return condicaoPagamento;
    }

    public void setCondicaoPagamento(CondicaoPagamento condicaoPagamento) {
        this.condicaoPagamento = condicaoPagamento;
    }

    public Boolean getCancelada() {
        return cancelada;
    }

    public void setCancelada(Boolean cancelada) {
        this.cancelada = cancelada;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Funcionario responsavel) {
        this.responsavel = responsavel;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public String getChassis() {
        return chassis;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }

    public String getDescricaoConclusao() {
        return descricaoConclusao;
    }

    public void setDescricaoConclusao(String descricaoConclusao) {
        this.descricaoConclusao = descricaoConclusao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdemServico that = (OrdemServico) o;
        return id.equals(that.id) &&
                itensServicos.equals(that.itensServicos) &&
                itensProdutos.equals(that.itensProdutos) &&
                vendaServico.equals(that.vendaServico) &&
                vendaProduto.equals(that.vendaProduto) &&
                dataCadastro.equals(that.dataCadastro) &&
                datUltimaAlteracao.equals(that.datUltimaAlteracao) &&
                funcionarioCadastro.equals(that.funcionarioCadastro) &&
                funcionarioUltimaAtualizacao.equals(that.funcionarioUltimaAtualizacao) &&
                condicaoPagamento.equals(that.condicaoPagamento) &&
                cancelada.equals(that.cancelada) &&
                fechada.equals(that.fechada) &&
                cliente.equals(that.cliente) &&
                responsavel.equals(that.responsavel) &&
                modelo.equals(that.modelo);
    }
}
