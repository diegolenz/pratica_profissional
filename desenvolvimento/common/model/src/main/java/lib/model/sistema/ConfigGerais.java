package lib.model.sistema;

import lib.model.endereco.cidade.Cidade;
import lib.model.financeiro.CondicaoPagamento.CondicaoPagamento;
import lib.model.financeiro.formaPagamento.FormaPagamento;
import lib.model.interno.Funcionario;

import java.util.Date;

public class ConfigGerais {
    private Integer id;

    private String razaoSocial;

    private String cnpj;

    private String nomeFantasia;

    private Cidade cidade;

    private String logradouro;

    private String numero;

    private String cep;

    private String complemento;

    private CondicaoPagamento condPadrao;

    private Funcionario funcionarioUltimaAtualizacao;

    private Funcionario funcionarioCadastro;

    private Date dataUltimaAlteracao;

    private Date dataCadastro;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public CondicaoPagamento getCondPadrao() {
        return condPadrao;
    }

    public void setCondPadrao(CondicaoPagamento condPadrao) {
        this.condPadrao = condPadrao;
    }

    public Funcionario getFuncionarioUltimaAtualizacao() {
        return funcionarioUltimaAtualizacao;
    }

    public void setFuncionarioUltimaAtualizacao(Funcionario funcionarioUltimaAtualizacao) {
        this.funcionarioUltimaAtualizacao = funcionarioUltimaAtualizacao;
    }

    public Funcionario getFuncionarioCadastro() {
        return funcionarioCadastro;
    }

    public void setFuncionarioCadastro(Funcionario funcionarioCadastro) {
        this.funcionarioCadastro = funcionarioCadastro;
    }

    public Date getDataUltimaAlteracao() {
        return dataUltimaAlteracao;
    }

    public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
        this.dataUltimaAlteracao = dataUltimaAlteracao;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
