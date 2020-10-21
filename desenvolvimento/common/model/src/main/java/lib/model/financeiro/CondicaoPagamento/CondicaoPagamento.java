package lib.model.financeiro.CondicaoPagamento;

import lib.model.financeiro.parcela.Parcela;
import lib.model.interno.Funcionario;

import java.util.Date;
import java.util.List;

public class CondicaoPagamento {
    Integer id;

    private String nome;

    private Integer quantidadeParcelas;

    private List<Parcela> parcelas;

    private Boolean ativo;

    private Double desconto;

    private Double multa;

    private Double juros;

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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Parcela> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<Parcela> parcelas) {
        this.parcelas = parcelas;
    }

    public Integer getQuantidadeParcelas() {
        return parcelas.size();
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public void setQuantidadeParcelas(Integer quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }

    public Double getJuros() {
        return juros;
    }

    public void setJuros(Double juros) {
        this.juros = juros;
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
}
