package imp.endereco.estado;

import imp.AbstractDao;
import imp.endereco.pais.PaisDao;
import imp.sistema.FuncionarioDao;
import lib.model.endereco.estado.Estado;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class
EstadoDao extends AbstractDao {

    private Estado estado;
    private PaisDao paisService;

    public EstadoDao() {
        this.estado = new Estado();
        this.paisService = new PaisDao();
        //  this.estadoDao = new EstadoDao();

    }

    public void save(Object obj) throws Exception {
        Estado estado = (Estado) obj;
        String sql = "";
        sql = ("INSERT INTO estado (nome, sigla, pais_id, ativo, data_cadastro,data_ultima_alteracao, funcionario_cadastro, funcionario_ultima_alteracao ) values (" +
                "'" + estado.getNome() + "','" + estado.getSigla() + "', " + estado.getPais().getId() + ", " + estado.getAtivo() +
                ", now(), now(), " + estado.getFuncionarioCadastro().getId() + ", " + estado.getFuncionarioUltimaAtualizacao().getId() + " );");

        this.st.executeUpdate(sql);
    }

    public void deleteByID(Object id) throws Exception {
        String sql = "DELETE FROM estado WHERE id = " + id + " ;";
        this.st.executeUpdate(sql);
    }

    public Estado getByNomeAndPaisExato(Estado estado) throws SQLException {
        String sql = "select * from estado where upper(nome) = '" + estado.getNome() + "' and pais_id = " + estado.getPais().getId() + " ;";
        ResultSet resultSet = this.st.executeQuery(sql);
        Estado estado1 = null;
        if (resultSet.next()) {
            estado1 = getByID(resultSet.getInt("id"));
        }
        return estado1;
    }

    public Optional<Estado> getLast() throws Exception {
        String sql = "Select id from estado order by ID desc limit 1;";
        ResultSet resultSet = this.st.executeQuery(sql);
        Integer id = null;
        if (resultSet.next()) {
            id = resultSet.getInt("id");
        }
        Optional<Estado> estado = Optional.ofNullable(this.getByID(id));
        return estado;
    }

    public List getAll(String termo) throws Exception {
        String sql = "";
        if (termo.length() == 0)
            sql = "SELECT * FROM estado  ";
        else if ((termo.matches("[0-9]")))
            sql = "Select * from estado where id like %" + termo + "% ";
        else
            sql = "SELECT * FROM estado WHERE UPPER(nome) like UPPER('%" + termo + "%') ";
        sql += " order by nome ;";
        ResultSet rs = this.st.executeQuery(sql);
        List<Estado> estados = new ArrayList<>();

        while (rs.next()) {
            Estado estado = this.getByID(rs.getInt("id"));
            estados.add(estado);
        }
        return estados;
    }

    public List getAllAtivos(String termo) throws Exception {

        String sql = "";
        if (termo.length() == 0)
            sql = "SELECT * FROM estado where ativo = true ";
        else if ((termo.matches("[0-9]")))
            sql = "Select * from estado where id like %" + termo + "% and ativo = true ";
        else
            sql = "SELECT * FROM estado WHERE UPPER(nome) like UPPER('%" + termo + "%') and ativo = true";
        sql += " order by nome ;";
        ResultSet rs = this.st.executeQuery(sql);
        List<Estado> estados = new ArrayList<>();

        while (rs.next()) {
            Estado estado = this.getByID(rs.getInt("pais_id"));
            estados.add(estado);
        }
        return estados;
    }


    public void update(Object obj) throws SQLException {
        estado = (Estado) obj;
        String sql = "UPDATE estado SET nome = '" + estado.getNome() + "', sigla = '" + estado.getSigla() + "', pais_id = " + estado.getPais().getId() + ", ativo= " + estado.getAtivo() + "" +
                ", funcionario_ultima_alteracao = " + estado.getFuncionarioUltimaAtualizacao().getId() +", data_ultima_alteracao = now() WHERE id = " + estado.getId() + " ;";
        this.st.executeUpdate(sql);
    }

    public List<Estado> getByPais(Integer idPais) throws Exception {

        ResultSet rs = this.st.executeQuery("SELECT * FROM estado where pais_id" + idPais + " ;");
        List<Estado> estados = new ArrayList<>();
        while (rs.next()) {
            Estado estado = this.getByID(rs.getInt("pais_id"));
            estados.add(estado);
        }
        return estados;
    }

    public Estado getByID(Integer id) throws SQLException {
        PreparedStatement preparedStatement = st.getConnection().prepareStatement("SELECT * FROM estado WHERE ID = " + id + ";");
        ResultSet rs = preparedStatement.executeQuery();
        Estado estado = new Estado();
        while (rs.next()) {
            estado.setId(rs.getInt("id"));
            estado.setNome(rs.getString("nome"));
            estado.setSigla(rs.getString("sigla"));
            estado.setAtivo(rs.getBoolean("ativo"));
            estado.setFuncionarioCadastro(new FuncionarioDao().getNomeAndId(rs.getInt("funcionario_cadastro")));
            estado.setFuncionarioUltimaAtualizacao(new FuncionarioDao().getNomeAndId(rs.getInt("funcionario_ultima_alteracao")));
            estado.setDataCadastro(rs.getDate("data_cadastro"));
            estado.setDataUltimaAlteracao(rs.getDate("data_ultima_alteracao"));
            estado.setPais(paisService.getByID(rs.getInt("pais_id")));
        }
        return estado;
    }

}
