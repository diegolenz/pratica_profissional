package imp.endereco.cidade;

import imp.AbstractDao;
import imp.endereco.estado.EstadoDao;
import imp.sistema.FuncionarioDao;
import lib.model.endereco.cidade.Cidade;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CidadeDao extends AbstractDao {


    private Cidade cidade;
    private EstadoDao estadoService;

    public CidadeDao() {
        this.cidade = new Cidade();
        this.estadoService = new EstadoDao();
        //  this.cidadeDao = new CidadeDao();

    }

    public Optional<Cidade> getLast()throws Exception{
        String sql = "Select id from cidade order by ID desc limit 1;";
        ResultSet resultSet = this.st.executeQuery(sql);
        Integer id = null;
        if (resultSet.next()){
            id = resultSet.getInt("id");
        }
        Optional<Cidade> cidade = Optional.ofNullable(this.getByID(id));
        return cidade;
    }

    public void save(Object obj) throws Exception {
        Cidade cidade = (Cidade) obj;
        String sql = "";
        sql = ("INSERT INTO cidade (nome, DDD, estado_id, ativo, data_cadastro, data_ultima_alteracao, funcionario_cadastro, funcionario_ultima_alteracao ) values (" +
                "'" + cidade.getNome() + "','" + cidade.getDDD() + "', " + cidade.getEstado().getId() + ", " + cidade.getAtivo() + ", now(), now(), + " +
                " "+ cidade.getFuncionarioCadastro().getId()+", " + cidade.getFuncionarioUltimaAtualizacao().getId() +" );");

        this.st.executeUpdate(sql);
    }

    public void deleteByID(Object id) throws Exception {
        String sql = "DELETE FROM cidade WHERE id = " + id + " ;";
        this.st.executeUpdate(sql);
    }

    public Cidade getByNomeAndEstadoExato(Cidade cidade)throws SQLException{
        String sql = "select * from cidade where upper(nome) = upper('"+ cidade.getNome() +"') and estado_id = " + cidade.getEstado().getId() +" ;";
        ResultSet resultSet = this.st.executeQuery(sql);
        Cidade cidade1 = null;
        if (resultSet.next()){
            cidade1 = getByID(resultSet.getInt("id"));
        }
        return cidade1;
    }


    public List getAll(String termo) throws Exception {
        String sql = "";
        if (termo.length() == 0)
            sql = "SELECT * FROM cidade ;";
        else if ((termo.matches("[0-9]")))
            sql = "Select * from cidade where id = "+ termo +" ;";
        else
            sql = "SELECT * FROM cidade WHERE upper(nome) like upper('%"+ termo +"%') and ativo = true";
        ResultSet rs = this.st.executeQuery(sql);
        List<Cidade> cidades = new ArrayList<>();

        while (rs.next()) {
            Cidade cidade =  this.getByID(rs.getInt("id"));
            cidades.add(cidade);
        }
        return cidades;
    }

    public List getAllAtivos(String termo) throws Exception {
        String sql = "";
        if (termo.length() == 0)
            sql = "SELECT * FROM cidade where ativo = true ;";
        else if ((termo.matches("[0-9]")))
            sql = "Select * from cidade where id = "+ termo +" and ativo = true ;";
        else
            sql = "SELECT * FROM cidade WHERE nome like '%"+ termo +"%' and ativo = true";
        ResultSet rs = this.st.executeQuery(sql);
        List<Cidade> cidades = new ArrayList<>();

        while (rs.next()) {
            cidades.add(getByID(rs.getInt("id")));
        }
        return cidades;
    }

    public void update(Object obj) throws SQLException {
        cidade = (Cidade) obj;
        String sql = "UPDATE cidade SET nome = '" + cidade.getNome() + "', DDD = '" + cidade.getDDD() + "', estado_id=" + cidade.getEstado().getId() + ", ativo=" + cidade.getAtivo() + "" +
                ", funcionario_ultima_alteracao = " + cidade.getFuncionarioUltimaAtualizacao().getId() +", data_ultima_alteracao = now() WHERE id = " + cidade.getId() + " ;";
        this.st.executeUpdate(sql);
    }

    public List<Cidade> getByEstado(Integer idEstado) throws Exception {
        return null;
    }

    public List<Cidade> getByNomeEstado(String idEstado) throws Exception {

       return this.getAllAtivos("");
    }

    public Cidade getByID(Integer id) throws SQLException {
        PreparedStatement preparedStatement=st.getConnection().
                prepareStatement("SELECT * FROM cidade WHERE id = "+id+";");
        ResultSet rs = preparedStatement.executeQuery();
        Cidade cidade=new Cidade();
        while (rs.next()) {
            cidade.setId(rs.getInt("id"));
            cidade.setNome(rs.getString("nome"));
            cidade.setDDD(rs.getString("DDD"));
            cidade.setAtivo(rs.getBoolean("ativo"));
            cidade.setFuncionarioCadastro(new FuncionarioDao().getByID(rs.getInt("funcionario_cadastro")));
            cidade.setFuncionarioUltimaAtualizacao(new FuncionarioDao().getByID(rs.getInt("funcionario_ultima_alteracao")));
            cidade.setDataCadastro(rs.getDate("data_cadastro"));
            cidade.setDataUltimaAlteracao(rs.getDate("data_ultima_alteracao"));
            cidade.setEstado(this.estadoService.getByID(rs.getInt("estado_id")));
        }
        return cidade;
    }
}

