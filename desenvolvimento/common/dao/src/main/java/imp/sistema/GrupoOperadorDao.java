package imp.sistema;

import imp.AbstractDao;
import lib.model.interno.GrupoFuncionario;
import lib.model.interno.ModuloSistema;
import lib.model.interno.NivelAcessoModulo;
import lib.model.interno.PermissaoAcesso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GrupoOperadorDao extends AbstractDao {
    public Integer getId() throws SQLException {
        ResultSet rs = this.st.executeQuery("Select * from grupo_funcionario order by ID desc limit 1");
        Integer id;
        if (rs.next()) {
            id = rs.getInt("id") + 1;
            if (getByID(id) == null) {
                return id;
            }
        }
        return 1;
    }

    public GrupoFuncionario save(GrupoFuncionario grupo) throws SQLException {
        //Salva grupo
        String sql = "Insert into grupo_funcionario ( " +
                "nome, data_cadastro," +
                " data_ultima_alteracao," +
                " ativo, funcionario_cadastro, " +
                " funcionario_cadastro, " +
                "funcionario_ultima_alteracao) values ( " +
                "'" + grupo.getNome() + "', " +
                "'" + grupo.getDataCadastro() + "'," +
                " " + grupo.getFuncionarioCadastro().getId() +
                " '" + grupo.getFuncionarioUltimaAlteracao().getId() + "', " +
                "" + grupo.getAtivo() + " );";
        this.st.getConnection().prepareStatement(sql).executeUpdate();
        // this.st.getConnection().commit();
        grupo.setId(this.getGrupoByUltimoId().getId());

        //salva permissões
        for (PermissaoAcesso permissaoAcesso : grupo.getPermissoes()) {
            String sqlPermissoes = "insert into permissao_acesso (grupo_funcionario_id, modulo, nivel_acesso) values " +
                    "(" + grupo.getId() + ", '" + permissaoAcesso.getModulo() + "', '" + permissaoAcesso.getNivelAcesso() + "' );";
            this.st.executeUpdate(sqlPermissoes);
        }
        return grupo;
    }

    public GrupoFuncionario update(GrupoFuncionario grupoFuncionario) throws SQLException {
        String sql = "UPDATE grupo_funcionario set nome = '" + grupoFuncionario.getNome() + "'," +
                "ativo = " + grupoFuncionario.getAtivo() + ", data_ultima_alteracao = now(), funcionario_ultima_alteracao = " + grupoFuncionario.getFuncionarioUltimaAlteracao().getId() + " ;";
        this.st.executeUpdate(sql);
        String sqlDeletePermisoes = "delete from permissao_acesso where grupo_funcionario_id = " + grupoFuncionario.getId() + " ;";
        this.st.executeUpdate(sqlDeletePermisoes);
        for (PermissaoAcesso permissaoAcesso : grupoFuncionario.getPermissoes()) {
            String sqlPermissoes = "insert into permissao_acesso (grupo_funcionario_id, modulo, nivel_acesso) values " +
                    "(" + grupoFuncionario.getId() + ", '" + permissaoAcesso.getModulo() + "', '" + permissaoAcesso.getNivelAcesso() + "' );";
            this.st.executeUpdate(sqlPermissoes);
        }
        return grupoFuncionario;

    }

    public void delete(GrupoFuncionario grupoFuncionario) throws SQLException {
        this.st.getConnection().prepareStatement("delete from grupo_funcionario where id = " + grupoFuncionario.getId() + " ;").executeUpdate();
        this.st.getConnection().prepareStatement("delete from permissao_acesso where grupo_funcionario_id = " + grupoFuncionario.getId() + " ;").executeUpdate();
    }

    public GrupoFuncionario getGrupoByUltimoId() throws SQLException {
        ResultSet rs = this.st.executeQuery("Select * from grupo_funcionario order by ID desc limit 1");
        if (rs.next())
            return getByID(rs.getInt("id"));
        return null;
    }

    public List<GrupoFuncionario> getAll(String termo) throws SQLException {
        ResultSet rs = this.st.executeQuery("select * from grupo_funcionario where upper(nome) like('%" + termo + "%') ;");
        List<GrupoFuncionario> grupos = new ArrayList<>();
        while (rs.next()) {
            grupos.add(getByID(rs.getInt("id")));
        }
        return grupos;
    }

    public List<PermissaoAcesso> getPermissaoByIdGrupo(Integer id) throws SQLException {
        ResultSet rs = st.getConnection().prepareStatement("select * from Permissao_Acesso where grupo_funcionario_id = " + id + " ;").executeQuery();
        List<PermissaoAcesso> permissoes = new ArrayList<>();
        while (rs.next()) {
            PermissaoAcesso permissaoAcesso = new PermissaoAcesso();
            permissaoAcesso.setId(rs.getInt("id"));
            String idModulo = rs.getString("modulo");
            //System.out.println(ModuloSistema.ENDERECO.toString());
            //  Arrays.stream(ModuloSistema.values()).forEach(moduloSistema -> System.out.println(moduloSistema));

            permissaoAcesso.setModulo(Arrays.stream(ModuloSistema.values()).filter(moduloSistema -> moduloSistema.toString().equalsIgnoreCase(idModulo)).findAny().get());
            String idNivelAcesso = rs.getString("nivel_acesso");
            permissaoAcesso.setNivelAcesso(Arrays.stream(NivelAcessoModulo.values()).filter(nivelAcessoModulo -> nivelAcessoModulo.toString().equalsIgnoreCase(idNivelAcesso)).findAny().get());
            permissoes.add(permissaoAcesso);
        }
        return permissoes;
    }

    public GrupoFuncionario getByID(Integer id) throws SQLException {
        ResultSet rs = st.getConnection().prepareStatement("select * from grupo_funcionario where id = " + id + " ;").executeQuery();
        GrupoFuncionario grupoFuncionario = null;
        if (rs.next()) {
            grupoFuncionario = new GrupoFuncionario();
            grupoFuncionario.setId(rs.getInt("id"));
            grupoFuncionario.setNome(rs.getString("nome"));
            grupoFuncionario.setAtivo(rs.getBoolean("ativo"));
            grupoFuncionario.setDataCadastro(rs.getDate("data_cadastro"));
            grupoFuncionario.setDataUltimaAlteracao(rs.getDate("data_ultima_alteracao"));
            grupoFuncionario.setFuncionarioCadastro(new FuncionarioDao().getNomeAndId(rs.getInt("funcionario_cadastro")));
            grupoFuncionario.setFuncionarioUltimaAlteracao(new FuncionarioDao().getNomeAndId(rs.getInt("funcionario_ultima_alteracao")));
            grupoFuncionario.setPermissoes(getPermissaoByIdGrupo(grupoFuncionario.getId()));
        }
        return grupoFuncionario;
    }

}
