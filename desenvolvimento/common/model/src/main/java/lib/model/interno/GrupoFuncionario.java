/**
 * (C) 2015-2018 JL INFORMATICA LTDA ME
 * CNPJ 14.928.793/0001-18
 * www.jlinformatica.net.br
 * (45) 3559-1534
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.model.interno;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Classe de entidade de grupo de operador
 * 
 * @author Diego
 * @since 1.3.1
 */

public class GrupoFuncionario implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String nome;

    private List<PermissaoAcesso> permissoes;

    private List<Funcionario> funcionarios;

    private Funcionario funcionarioCadastro;

    private Funcionario funcionarioUltimaAlteracao;

    private Date dataCadastro;

    private Date dataUltimaAlteracao;

    private Boolean ativo;

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<PermissaoAcesso> getPermissoes() {
        if (permissoes == null)
            permissoes = new ArrayList<>();
        return permissoes;
    }

    public void setPermissoes(List<PermissaoAcesso> permissoes) {
        this.permissoes = permissoes;
    }

    public GrupoFuncionario() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
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

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataUltimaAlteracao() {
        return dataUltimaAlteracao;
    }

    public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
        this.dataUltimaAlteracao = dataUltimaAlteracao;
    }



    @Override
    public boolean equals(Object obj) {
        
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        GrupoFuncionario other = (GrupoFuncionario) obj;
        if (this.getId() == null || other.getId() == null) {
            return super.equals(obj);
        }
        
        return this.getId().equals(other.getId());
        
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


}
