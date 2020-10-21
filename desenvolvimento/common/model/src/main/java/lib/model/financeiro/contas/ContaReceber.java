package lib.model.financeiro.contas;

import lib.model.comercial.VendaProduto;
import lib.model.comercial.VendaServico;
import lib.model.financeiro.StatusConta;
import lib.model.financeiro.formaPagamento.FormaPagamento;
import lib.model.interno.Funcionario;
import lib.model.pessoa.cliente.Cliente;
import lib.model.pessoa.fornecedor.Fornecedor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;


public class ContaReceber {

    //pk
    private Integer numNota;
    //pk
    private Integer serie;
    //pk
    private String modelo;
    //pk
    private Integer numParcela;
    //pk
    private Cliente recebedor;

//    private VendaProduto venda;
//
//    private VendaServico vendaServico;

    private Boolean isVendaServico;

    private Boolean isVendaProduto;

    private boolean ativo;

   // private Double

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Cliente getRecebedor() {
        return recebedor;
    }

    public void setRecebedor(Cliente recebedor) {
        this.recebedor = recebedor;
    }

    public Integer getNumNota() {
        return numNota;
    }

    public void setNumNota(Integer numNota) {
        this.numNota = numNota;
    }

    public Integer getSerie() {
        return serie;
    }

    public void setSerie(Integer serie) {
        this.serie = serie;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    // private Parcela parcela;

    private Double valor;

    private Date dataLancamento;

    private Date dataVencimento;

    private FormaPagamento formaPagamento;

    private Double desconto;

    private Double juros;

    private Double multa;

    private boolean paga;

    private Date dataPagamento;

    private Funcionario funcionarioPagamento;

    private Double valorPago;

    private Date dataCadastro;

    private Date dataUltAlteracao;

    private Funcionario funcionarioCadastro;

    private Funcionario funcionarioUltimaAlteracao;

    public Funcionario getFuncionarioPagamento() {
        return funcionarioPagamento;
    }

    public void setFuncionarioPagamento(Funcionario funcionarioPagamento) {
        this.funcionarioPagamento = funcionarioPagamento;
    }

    public ContaReceber() {
        this.ativo = true;
        this.paga = false;
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

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public boolean isPaga() {
        return paga;
    }

    public void setPaga(boolean paga) {
        this.paga = paga;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

//    public VendaProduto getVenda() {
//        return venda;
//    }
//
//    public void setVenda(VendaProduto venda) {
//        this.venda = venda;
//    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getJuros() {
        return juros;
    }

    public void setJuros(Double juros) {
        this.juros = juros;
    }

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }

//    public VendaServico getVendaServico() {
//        return vendaServico;
//    }
//
//    public void setVendaServico(VendaServico vendaServico) {
//        this.vendaServico = vendaServico;
//    }

    public Integer getNumParcela() {
        return numParcela;
    }

    public void setNumParcela(Integer numParcela) {
        this.numParcela = numParcela;
    }

    public StatusConta getStatusConta() {
        if (!ativo) {
            return StatusConta.CANCELADA;
        }
        LocalDate hoje = LocalDate.now();
        if (dataVencimento == null) {
            return StatusConta.PENDENTE;
        }
        LocalDate vencimento = Instant.ofEpochMilli(this.dataVencimento.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate pagamento = null;
        if (dataPagamento != null)
            pagamento = Instant.ofEpochMilli(this.dataPagamento.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        if (!paga) {
            if (this.dataVencimento != null && vencimento.compareTo(hoje) >= 0)
                return StatusConta.PENDENTE;
            else return StatusConta.ATRASADO;
        } else {
            if (paga && (vencimento != null && vencimento.compareTo(pagamento) >= 0))
                return StatusConta.QUITADA;
            else return StatusConta.PAGA_COM_ATRASO;
        }
    }

    public void setVendaServico(Boolean vendaServico) {
        isVendaServico = vendaServico;
    }

    public Boolean getVendaServico() {
        return isVendaServico;
    }

    public Boolean getVendaProduto() {
        return isVendaProduto;
    }

    public void setVendaProduto(Boolean vendaProduto) {
        isVendaProduto = vendaProduto;
    }
}
