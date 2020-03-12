package imp.servico;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import imp.AbstractDao;
import imp.grupo.GrupoDao;
import lib.model.grupo.Grupo;

import lib.model.servico.Servico;


public class ServicoDao<T> extends AbstractDao<T> {


    public void save(Servico servico) throws SQLException {
        String sql = "INSERT INTO servico (nome, ativo, valor, data_ultima_alteracao, data_cadastro, grupo_id) values ('"+servico.getNome()+"', "+ servico.isAtivo() +
                ", "+ servico.getValor() +", '"+servico.getDt_ult_alteracao() +"', '"+servico.getDt_cadastro() +"', "+ servico.getGrupo().getId() +
                " ) ; ";
        this.st.executeUpdate(sql);
    }

    public void update(Servico servico) throws SQLException {
        String sql = "update servico set nome = '"+ servico.getNome() +"', ativo = "+ servico.isAtivo() +", data_ultima_alteracao = '"+servico.getDt_ult_alteracao()  +
                "', data_cadastro = '"+ servico.getDt_cadastro() +"'" +
                ", grupo_id = "+servico.getGrupo().getId()+" where id = "+ servico.getId() + " ;";
        this.st.executeUpdate(sql);
    }

    public List getAll(String termo) throws SQLException {
        String sql ="";
        if (termo.length() == 0)
            sql = "SELECT * FROM servico ;";
        else if ((!termo.matches("[0-9]")))
            sql = "Select * from servico where id = "+ termo +";";
        else
            sql = "SELECT * FROM servico WHERE nome ="+ termo +";";

        ResultSet rs = this.st.executeQuery(sql);
        List servicos = new ArrayList();
        while (rs.next()){
            servicos.add(getByID(rs.getInt("id")));
        }
        return servicos;
    }



    public List getAllAtivos(String termo)throws SQLException{
        String sql = "";
        if (termo.length() == 0)
            sql = "SELECT * FROM servico where ativo = true ;";
        else if ((!termo.matches("[0-9]")))
            sql = "Select * from servico where id = "+ termo +" and ativo = true ;";
        else
            sql = "SELECT * FROM servico WHERE nome like '%"+ termo +"%' and ativo = true";
        ResultSet rs = this.st.executeQuery(sql);
        List servicos = new ArrayList();
        while (rs.next()){
            servicos.add(getByID(rs.getInt("id")));
        }
        return servicos;
    }

    public Object getByID(Integer id)throws SQLException{
        String sql = "Select * from servico where id ="+id+" ;";
        ResultSet rs = this.st.getConnection().prepareStatement(sql).executeQuery();
        Servico servico=null;
        while (rs.next()){
            servico=new Servico();
            servico.setId(rs.getInt("id"));
            servico.setAtivo(rs.getBoolean("ativo"));
            servico.setNome(rs.getString("nome"));
            servico.setGrupo((Grupo) new GrupoDao<Grupo>().getByID(rs.getInt("grupo_id")).get());
            servico.setValor(rs.getDouble("valor"));
            servico.setDt_ult_alteracao(rs.getDate("data_ultima_alteracao"));
            servico.setDt_ult_alteracao(rs.getDate("data_cadastro"));
        }
        return servico;
    }

    public void deleteByID(Integer id)throws SQLException{
        String sql = "DELETE  FROM servico WHERE id = "+id+" ;";
        this.st.executeUpdate(sql);
    }
}