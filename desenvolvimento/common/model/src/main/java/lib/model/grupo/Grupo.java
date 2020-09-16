package lib.model.grupo;


import lib.model.interno.Funcionario;
import lib.model.interno.PermissaoAcesso;

import java.util.Date;
import java.util.List;

public class Grupo {

    private static final Long serialVersionUID = 1L;

    private Integer id;

    private String nome;

    private Boolean ativo;

    private Date dataUltimaAlteracao;

    private Date dataCadastro;

    private Funcionario funcionarioUltimaAtualizacao;

    private Funcionario funcionarioCadastro;

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

    List<PermissaoAcesso> permissaoAcessos;

    public static Long getSerialVersionUID() {
        return serialVersionUID;
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

    public Boolean getAtivo() {
        return ativo;
    }

    public List<PermissaoAcesso> getPermissaoAcessos() {
        return permissaoAcessos;
    }

    public void setPermissaoAcessos(List<PermissaoAcesso> permissaoAcessos) {
        this.permissaoAcessos = permissaoAcessos;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
