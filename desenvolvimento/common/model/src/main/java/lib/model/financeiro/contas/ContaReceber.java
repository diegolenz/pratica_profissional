package lib.model.financeiro.contas;

import lib.model.comercial.VendaProduto;
import lib.model.comercial.VendaServico;
import lib.model.financeiro.StatusConta;
import lib.model.financeiro.formaPagamento.FormaPagamento;
import lib.model.interno.Funcionario;
import lib.model.pessoa.cliente.Cliente;

import java.util.Calendar;
import java.util.Date;


public class ContaReceber {
    private Integer id;

    private VendaProduto venda;

    private VendaServico vendaServico;

    private String descricao;

    private Cliente recebedor;

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

    public String getDescricao() {
        return descricao;
    }


    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public VendaProduto getVenda() {
        return venda;
    }

    public void setVenda(VendaProduto venda) {
        this.venda = venda;
    }

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

    public VendaServico getVendaServico() {
        return vendaServico;
    }

    public void setVendaServico(VendaServico vendaServico) {
        this.vendaServico = vendaServico;
    }

    public StatusConta getStatusConta() {
        Calendar dtAtual = Calendar.getInstance();
        Calendar dtVencimento = Calendar.getInstance();
        Calendar dtPagamento = Calendar.getInstance();
        if (dataVencimento != null)
            dtVencimento.set(dataVencimento.getDate(), dataVencimento.getMonth(), dataVencimento.getDay());
        if (dataPagamento != null)
            dtPagamento.set(dataPagamento.getDate(), dataPagamento.getMonth(), dataPagamento.getDay());
        if (!paga) {
            if (dtVencimento.getTime() != null || dtVencimento.getTime().compareTo(dtAtual.getTime()) >= 0)
                return StatusConta.PENDENTE;
            else return StatusConta.ATRASADO;
        } else {
            if (paga && (dtVencimento.getTime() == null || dtVencimento.getTime().compareTo(dtPagamento.getTime()) >= 0))
                return StatusConta.QUITADA;
            else return StatusConta.PAGA_COM_ATRASO;
        }
    }

}
