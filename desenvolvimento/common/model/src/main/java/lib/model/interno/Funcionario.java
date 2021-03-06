package lib.model.interno;

import lib.model.pessoa.Pessoa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Funcionario extends Pessoa {

    private static final Long serialVersionUID = 1L;

    private Integer id;

    private String usuario;

    private String senha;

    private Date dataCadastro;

    private Date dataUltimaAlteracao;

    private Funcionario funcionarioUltimaAtualizacao;

    private Funcionario funcionarioCadastro;

    private boolean ativo;

    private boolean alterarSenhaProximoLogin;

    private List<GrupoFuncionario> grupos;

    private NivelAcessoOperador nivelAcesso;

    private Date dataAdmissao;

    private Date dataDemissao;

    private Boolean primeiroLogin;

    public Date getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(Date dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public Date getDataDemissao() {
        return dataDemissao;
    }

    public void setDataDemissao(Date dataDemissao) {
        this.dataDemissao = dataDemissao;
    }

    public NivelAcessoOperador getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(NivelAcessoOperador nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    public boolean isPermissaoConcedida(ModuloSistema modulo, NivelAcessoModulo nivel) {

        if (nivelAcesso == NivelAcessoOperador.SUPORTE) {
            return true;
        }

        if (grupos == null || grupos.isEmpty()) {
            return false;
        }

        return getGrupos()
                .stream()
                .anyMatch(grupoFuncionario ->
                        grupoFuncionario.getPermissoes().stream().anyMatch(permissaoAcesso -> permissaoAcesso.getModulo().equals(modulo)
                                && (permissaoAcesso.getNivelAcesso().equals(nivel) || nivel.equals(NivelAcessoModulo.SOMENTE_LEITURA)))
                );

    }

    public List<GrupoFuncionario> getGrupos() {
        if (grupos == null) {
            grupos = new ArrayList<>();
        }
        return grupos;
    }

    public void setGrupos(List<GrupoFuncionario> grupos) {
        this.grupos = grupos;
    }


    public Funcionario() {
        this.nivelAcesso = NivelAcessoOperador.RESPEITAR_PERMISSOES;
        this.ativo = true;
        this.alterarSenhaProximoLogin = true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return (senha);
    }

    public void setSenha(String senha) {
        this.senha = (senha);
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean isAlterarSenhaProximoLogin() {
        return alterarSenhaProximoLogin;
    }

    public void setAlterarSenhaProximoLogin(boolean alterarSenhaProximoLogin) {
        this.alterarSenhaProximoLogin = alterarSenhaProximoLogin;
    }

    public static Long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Date getDataUltimaAlteracao() {
        return dataUltimaAlteracao;
    }

    public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
        this.dataUltimaAlteracao = dataUltimaAlteracao;
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

    public Boolean getPrimeiroLogin() {
        return primeiroLogin;
    }

    public void setPrimeiroLogin(Boolean primeiroLogin) {
        this.primeiroLogin = primeiroLogin;
    }
}
