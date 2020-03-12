package imp.grupo;

import imp.AbstractDao;
import lib.model.grupo.Grupo;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GrupoDao <T> extends AbstractDao<T> {


    public void save(Grupo grupo)throws Exception {
        String sql = "INSERT INTO grupo (nome, ativo) values ('"+grupo.getNome()+"', "+ grupo.getAtivo() +") ; ";
        this.st.executeUpdate(sql);
    }

    public void update(Grupo grupo)throws Exception{
        String sql = "update grupo set nome = '"+ grupo.getNome() +"', ativo = "+ grupo.getAtivo() +" where id = "+ grupo.getId() + " ;";
        this.st.executeUpdate(sql);
    }

    public Grupo findByNomeExato(String nome)throws Exception{
        String sql = "SELECT * FROM grupo WHERE UPPER(nome) = UPPER('" + nome + "') ;";
        ResultSet resultSet = this.st.executeQuery(sql);
        if (resultSet.next()) {
            Grupo marca = new Grupo();
            marca.setId(resultSet.getInt("id"));
            marca.setAtivo(resultSet.getBoolean("ativo"));
            marca.setNome(resultSet.getString("nome"));
            return marca;
        }
        return null;
    }

    public List getAll(String termoBusca)throws Exception{
        String sql = "";
        if (termoBusca.length() == 0)
           sql="SELECT * FROM grupo order by nome;";
        else if ((!termoBusca.matches("[0-9]")))
            sql = "Select * from grupo where id = "+ termoBusca +" order by nome;";
        else
            sql = "SELECT * FROM grupo WHERE nome ="+ termoBusca +" order by nome;";

        ResultSet rs = this.st.executeQuery(sql);
        List grupos = new ArrayList();
        while (rs.next()){
            Grupo grupo=new Grupo();
            grupo.setId(rs.getInt("id"));
            grupo.setAtivo(rs.getBoolean("ativo"));
            grupo.setNome(rs.getString("nome"));
            grupos.add(grupo);
        }
        return grupos;
    }

    public Optional<Grupo> findLast()throws Exception{
        String sql = "Select id from grupo order by ID desc limit 1;";
        ResultSet resultSet = this.st.executeQuery(sql);
        Integer id = null;
        if (resultSet.next()){
            id = resultSet.getInt("id");
        }
        return this.getByID(id);
    }



    public List getAllAtivos(String termoBusca)throws Exception{
        String sql="";
        if (termoBusca.length() == 0)
            sql="SELECT * FROM grupo where ativo = true ;";
        else if ((!termoBusca.matches("[0-9]")))
            sql = "Select * from grupo where id = "+ termoBusca +" and  ativo = true ;";
        else
            sql = "SELECT * FROM grupo WHERE nome = "+ termoBusca +" and  ativo = true ;";
        ResultSet rs = this.st.executeQuery(sql);
        List grupos = new ArrayList();
        while (rs.next()){
            Grupo grupo=new Grupo();
            grupo.setId(rs.getInt("id"));
            grupo.setAtivo(rs.getBoolean("ativo"));
            grupo.setNome(rs.getString("nome"));
            grupos.add(grupo);
        }
        return grupos;
    }

    public Optional<Grupo> getByID(Integer id)throws SQLException {
        String sql = "Select * from grupo where id ="+id+" ;";
        PreparedStatement preparedStatement=st.getConnection().prepareStatement(sql);
        ResultSet rs =  preparedStatement.executeQuery();
        Grupo grupo=null;
        while (rs.next()){
            grupo=new Grupo();
            grupo.setId(rs.getInt("id"));
            grupo.setAtivo(rs.getBoolean("ativo"));
            grupo.setNome(rs.getString("nome"));
        }
        return Optional.ofNullable(grupo);
    }

    public void deleteByID(Integer id) throws Exception{
        String sql = "DELETE  FROM grupo WHERE id = "+id+";";
        this.st.executeUpdate(sql);
    }


}

