package imp.os.modelo;

import imp.AbstractDao;
import imp.marca.MarcaDao;
import imp.sistema.FuncionarioDao;
import lib.model.marca.Marca;
import lib.model.os.veiculo.modelo.Modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModeloDao extends AbstractDao {
    public void save(Modelo modelo)throws SQLException {
        this.st.getConnection().prepareStatement("insert into modelo (ativo, nome, data_cadastro, data_ultima_alteracao," +
                "funcionario_cadastro_id, funcionario_ultima_alteracao_id, marca_id) values ("+modelo.getAtivo()+"," +
                "'"+ modelo.getNome() + "', '"+ modelo.getDataCadastro()+"', '"+modelo.getDataUltimaAalteracao()+"', " +
                ""+ modelo.getFuncionarioCadastro().getId() + ", " +modelo.getFuncionarioUltimaAlteracao().getId() + "," +
                ""+modelo.getMarca().getId() + " );").executeUpdate();
    }


    public List<Modelo> getAll()throws SQLException{
        ResultSet rs =  this.st.getConnection().prepareStatement("select * from modelo ;").executeQuery();
        List<Modelo> modelos = new ArrayList<Modelo>();
        while (rs.next()){
            modelos.add(getByID(rs.getInt("id")));
        }
        return modelos;
    }

    public Modelo getByID(Integer id) throws SQLException{
        ResultSet rs = this.st.getConnection().prepareStatement("select * from modelo where id = " + id +" ;").executeQuery();
        if (rs.next()){
            Modelo modelo = new Modelo();
            modelo.setId(rs.getInt("id"));
            modelo.setNome(rs.getString("nome"));
            modelo.setDataCadastro(rs.getDate("data_cadastro"));
            modelo.setDataUltimaAalteracao(rs.getDate("data_ultima_alteracao"));
            modelo.setFuncionarioCadastro(new FuncionarioDao().getNomeAndId(rs.getInt("funcionario_cadastro_id")));
            modelo.setFuncionarioUltimaAlteracao(new FuncionarioDao().getNomeAndId(rs.getInt("funcionario_ultima_alteracao_id")));
            modelo.setAtivo(rs.getBoolean("ativo"));
            modelo.setMarca((Marca) new MarcaDao<Marca>().getByID(rs.getInt("marca_id")));
            return modelo;
        }
        return null;
    }

    public void delete(Modelo modelo)throws SQLException{
        this.st.getConnection().prepareStatement("delete from modelo where id = "+ modelo.getId() + " ;" ).execute();
    }

    public void update(Modelo modelo) throws SQLException{
        this.st.getConnection().prepareStatement("update modelo set nome = '" + modelo.getNome() + "'" +
                ", ativo = "+modelo.getAtivo()+", data_ultima_alteracao '" +
                modelo.getDataUltimaAalteracao() + "', funcionario_ultima_alteracao_id = " + modelo.getFuncionarioUltimaAlteracao().getId() + " ;")
                .executeUpdate();

    }

}
