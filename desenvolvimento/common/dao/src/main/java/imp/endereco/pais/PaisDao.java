package imp.endereco.pais;

import imp.AbstractDao;
import imp.sistema.FuncionarioDao;
import lib.model.endereco.pais.Pais;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaisDao extends AbstractDao {

    private Pais pais;

    public void save(Object obj) throws Exception {
        Pais pais = (Pais) obj;
        String sql = "";
        sql = ("INSERT INTO pais (nome, ddi, ativo,  data_cadastro, data_ultima_alteracao, funcionario_cadastro, funcionario_ultima_alteracao ) values (" +
                "'" + pais.getNome() + "','" + pais.getDdi() + "', " + pais.getAtivo() +
                ", now(), now(), + " +
                " " + pais.getFuncionarioCadastro() + ", " + pais.getFuncionarioUltimaAtualizacao()+ " );");

        this.st.executeUpdate(sql);
    }

    public Optional<Pais> getLast() throws Exception {
        String sql = "Select id from pais order by ID desc limit 1;";
        ResultSet resultSet = this.st.executeQuery(sql);
        Integer id = null;
        if (resultSet.next()) {
            id = resultSet.getInt("id");
        }
        Optional<Pais> estado = Optional.ofNullable(this.getByID(id));
        return estado;
    }

    public Pais getPaisByNomeExato(String nome) throws SQLException {
        String sql = "select * from pais where upper(nome) = upper('" + nome + "')";
        ResultSet rs = this.st.executeQuery(sql);
        Pais pais = null;
        if (rs.next()) {
            pais = getByID(rs.getInt("id"));
        }
        return pais;
    }


    public void deleteByID(Object id) throws Exception {
        String sql = "DELETE FROM pais WHERE id = " + id + " ;";
        this.st.executeUpdate(sql);
    }


    public List getAll(String termo) throws SQLException {
        String sql = "";
        if (termo.length() == 0)
            sql = "SELECT * FROM pais  ;";
        else if ((termo.matches("[0-9]")))
            sql = "Select * from pais where id like %" + termo + "% ;";
        else
            sql = "SELECT * FROM pais WHERE nome like '%" + termo + "%' ;";
        ResultSet rs = this.st.executeQuery(sql);
        List<Pais> paises = new ArrayList<>();
        while (rs.next()) {
            Pais pais = getByID(rs.getInt("id"));
            paises.add(pais);
        }
        return paises;
    }

    public List getAllAtivos(String termo) throws SQLException {
        String sql = "";
        if (termo.length() == 0)
            sql = "SELECT * FROM pais where ativo = true ;";
        else if ((termo.matches("[0-9]")))
            sql = "Select * from pais where id = " + termo + " and ativo = true ;";
        else
            sql = "SELECT * FROM pais WHERE nome like '%" + termo + "%' and ativo = true";
        ResultSet rs = this.st.executeQuery(sql);
        List<Pais> paises = new ArrayList<>();
        while (rs.next()) {
            Pais pais = getByID(rs.getInt("id"));
            paises.add(pais);
        }
        return paises;
    }

    public Pais getByID(Integer id) throws SQLException {
        PreparedStatement preparedStatement = st.getConnection().prepareStatement("SELECT * FROM pais WHERE id = " + id + ";");
        //    ResultSet rs = this.st.executeQuery("SELECT * FROM pais WHERE ID = "+id+";");
        ResultSet rs = preparedStatement.executeQuery();
        Pais pais = new Pais();

        while (rs.next()) {
            pais.setId(rs.getInt("id"));
            pais.setNome(rs.getString("nome"));
            pais.setDdi(rs.getString("ddi"));
            pais.setAtivo(rs.getBoolean("ativo"));
            pais.setFuncionarioCadastro(rs.getInt("funcionario_cadastro"));
            pais.setFuncionarioUltimaAtualizacao(rs.getInt("funcionario_ultima_alteracao"));
            pais.setDataCadastro(rs.getDate("data_cadastro"));
            pais.setDataUltimaAlteracao(rs.getDate("data_ultima_alteracao"));
        }
        return pais;
    }

    public void update(Object obj) throws SQLException {
        pais = (Pais) obj;
        String sql = "UPDATE pais SET nome = '" + pais.getNome() + "', ddi = '" + pais.getDdi() + "', ativo=" + pais.getAtivo() + " " +
                ", funcionario_ultima_alteracao = " + pais.getFuncionarioUltimaAtualizacao() + ", data_ultima_alteracao = now() WHERE id = " + pais.getId() + " ;";
        this.st.executeUpdate(sql);
    }

}
