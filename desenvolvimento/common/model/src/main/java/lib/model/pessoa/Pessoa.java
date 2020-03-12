package lib.model.pessoa;

import lib.model.endereco.cidade.Cidade;

import java.util.Date;


public abstract class Pessoa {
    public Pessoa(){
        this.nome = "";
        this.nomeFantasia_Apelido = "";
        this.estadocivil = lib.model.pessoa.estadocivil.SOLTEIRO;
        this.tipo = TipoPessoa.FISICA;
        this.cpfCnpj = "";
        this.rgIe = "";
        this.dataNascimento = new Date();
        this.sexo = Sexo.MASCULINO;
        this.telefone = "";
        this.email = "";
        this.telefoneAlternativo = "";
        //this.cidade = new Cidade();
        this.bairro = "";
        this.logradouro = "";
        this.complemento = "";
        this.numeroResidencial = "";
        this.cep = "";
    }

    private Integer id;

    private String nome;

    private String nomeFantasia_Apelido;

    private estadocivil estadocivil;

    private TipoPessoa tipo;

    private String cpfCnpj;

    private String rgIe;

    private Date dataNascimento;

    private Sexo sexo;

    private String telefone;

    private String email;

    private String telefoneAlternativo;

    private Cidade cidade;

    private String bairro;

    private String logradouro;

    private String complemento;

    private String numeroResidencial;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    private String cep;

    public lib.model.pessoa.estadocivil getEstadocivil() {
        return estadocivil;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getTelefoneAlternativo() {
        return telefoneAlternativo;
    }

    public void setTelefoneAlternativo(String telefoneAlternativo) {
        this.telefoneAlternativo = telefoneAlternativo;
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

    public String getNomeFantasia_Apelido() {
        return nomeFantasia_Apelido;
    }

    public void setNomeFantasia_Apelido(String nomeFantasia_Apelido) {
        this.nomeFantasia_Apelido = nomeFantasia_Apelido;
    }

    public TipoPessoa getTipo() {
        return tipo;
    }

    public String getNumeroResidencial() {
        return numeroResidencial;
    }

    public void setNumeroResidencial(String numeroResidencial) {
        this.numeroResidencial = numeroResidencial;
    }

    public void setTipo(TipoPessoa  tipo) {

        this.tipo = tipo;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getRgIe() {
        return rgIe;
    }

    public void setRgIe(String rgIe) {
        this.rgIe = rgIe;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

