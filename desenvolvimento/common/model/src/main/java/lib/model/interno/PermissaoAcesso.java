/**
 * (C) 2015-2018 JL INFORMATICA LTDA ME
 * CNPJ 14.928.793/0001-18
 * www.jlinformatica.net.br
 * (45) 3559-1534
 */
package lib.model.interno;

import java.io.Serializable;

public class PermissaoAcesso implements Serializable {

    private static final Long serialVersionUID = 1L;
    private Integer id;

    private ModuloSistema modulo;

    private NivelAcessoModulo nivelAcesso;

    private GrupoFuncionario grupoFuncionario;

    public GrupoFuncionario getGrupoFuncionario() {
        return grupoFuncionario;
    }

    public void setGrupoFuncionario(GrupoFuncionario grupoFuncionario) {
        this.grupoFuncionario = grupoFuncionario;
    }

    public PermissaoAcesso() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ModuloSistema getModulo() {
        return modulo;
    }

    public void setModulo(ModuloSistema modulo) {
        this.modulo = modulo;
    }

    public NivelAcessoModulo getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(NivelAcessoModulo nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    public boolean isNivelAcessoAceitavel(ModuloSistema moduloSolicitado, NivelAcessoModulo nivelAcessoSolicitado) {
        if (modulo == moduloSolicitado) {
            
            if (nivelAcessoSolicitado == NivelAcessoModulo.SOMENTE_LEITURA) {
                return true;
            } else {
                return nivelAcesso == NivelAcessoModulo.LEITURA_GRAVACAO;
            }
            
        } else {
            return false;
        }
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        PermissaoAcesso other = (PermissaoAcesso) obj;
        if (this.getId() == null || other.getId() == null) {
            return super.equals(obj);
        }

        return this.getId().equals(other.getId());

    }

    @Override
    public String toString() {
        return "socius.lib.model.PermissaoAcesso[ id=" + id + " ]";
    }


}
