package imp.os.veiculo;

import imp.AbstractDao;
import imp.os.modelo.ModeloDao;
import lib.model.os.veiculo.Veiculo;
import lib.model.os.veiculo.modelo.Modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VeiculoDao extends AbstractDao {

    public void save(Veiculo veiculo)throws SQLException {
        String sql = "insert into veiculo (ativo, nome, data_cadastro, data_ultima_alteracao," +
                "funcionario_cadastro_id, funcionario_ultima_alteracao_id, modelo_id, chassis, placa, cor, ano, km) values ("+veiculo.getAtivo()+"," +
                "'"+ veiculo.getNome() + "', '"+ veiculo.getDataCadastro()+"', '"+veiculo.getDataUltimaAalteracao()+"', " +
                ""+ veiculo.getFuncionarioCadastro().getId() + ", " +veiculo.getFuncionarioUltimaAlteracao().getId() + "," +
                ""+veiculo.getModelo().getId() + ", '" + veiculo.getChassis() + "', '" + veiculo.getPlaca() + "', " +
                "' "+veiculo.getCor()+"', " + veiculo.getAno() + ", "+veiculo.getKm()+" );";
        this.st.getConnection().prepareStatement(sql).executeUpdate();
    }

    public Veiculo findLast()throws Exception{
        String sql = "Select id from veiculo order by ID desc limit 1;";
        ResultSet resultSet = this.st.executeQuery(sql);
        Integer id = null;
        if (resultSet.next()){
            id = resultSet.getInt("id");
        }
        return this.getByID(id);
    }


    public List<Veiculo> getAll()throws SQLException{
        ResultSet rs =  this.st.getConnection().prepareStatement("select * from veiculo ;").executeQuery();
        List<Veiculo> veiculos = new ArrayList<>();
        while (rs.next()){
            veiculos.add(getByID(rs.getInt("id")));
        }
        return veiculos;
    }

    public Veiculo getByID(Integer id)throws SQLException{
        ResultSet rs = this.st.getConnection().prepareStatement("select * from veiculo where id = " + id +" ;").executeQuery();
        if (rs.next()){
            Veiculo veiculo = new Veiculo();
            veiculo.setId(rs.getInt("id"));
            veiculo.setNome(rs.getString("nome"));
            veiculo.setDataCadastro(rs.getDate("data_cadastro"));
            veiculo.setDataUltimaAalteracao(rs.getDate("data_ultima_alteracao"));
            veiculo.setModelo((Modelo) new ModeloDao().getByID(rs.getInt("modelo_id")));
            veiculo.setChassis(rs.getString("chassis"));
            veiculo.setAtivo(rs.getBoolean("ativo"));
            veiculo.setAno(rs.getInt("ano"));
            veiculo.setCor(rs.getString("cor"));
            veiculo.setKm(rs.getDouble("km"));
            veiculo.setPlaca(rs.getString("placa"));
            return veiculo;
        }
        return null;
    }

    public void delete(Veiculo veiculo)throws SQLException{
        this.st.getConnection().prepareStatement("delete from veiculo where id = "+ veiculo.getId() + " ;" ).execute();
    }

    public void update(Veiculo veiculo) throws SQLException{
        this.st.getConnection().prepareStatement("update veiculo set nome = '" + veiculo.getNome() + "'" +
                ", ativo = "+veiculo.getAtivo()+", data_ultima_alteracao '" +
                veiculo.getDataUltimaAalteracao() + "', funcionario_ultima_alteracao_id = " + veiculo.getFuncionarioUltimaAlteracao().getId() + "," +
                " chassis = '"+veiculo.getChassis() + "', km = "+veiculo.getKm() + " placa = '"+ veiculo.getPlaca() + "', " +
                "cor = '"+veiculo.getCor()+", ano = "+veiculo.getAno()+";")
                .executeUpdate();

    }

}

