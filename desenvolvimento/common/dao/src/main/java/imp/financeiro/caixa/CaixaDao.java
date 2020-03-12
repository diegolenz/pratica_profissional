package imp.financeiro.caixa;

import imp.AbstractDao;
import lib.model.financeiro.caixa.Caixa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CaixaDao extends AbstractDao {

    public Caixa save(Caixa caixa)throws SQLException{
        String sql = "INSERT INTO caixa (nome, ativo, data_cadastro, data_ultima_alteracao, funcionario_atualizacao_id, funcionario_cadastro_id) values ("+
               "'"+ caixa.getNome() + "', " + caixa.isAtivo() + ", '" + caixa.getDataCadastro() +"', '" + caixa.getDataUltimaAlteraocao() +"', " +
                caixa.getFuncionarioAtualizacao().getId() + ", " + caixa.getFuncionarioCadastro().getId() + " ;";

        this.st.execute(sql);

        ResultSet rs = this.st.executeQuery("Select max(id) from caixa;");
        if (rs.next()){
            return getById(rs.getInt("id"));
        }
        return caixa;
    }

    public Caixa update(Caixa caixa)throws SQLException{
        String sql = "UPDATE caixa SET nome = '" + caixa.getNome() + "', data_ultima_alteracao = '" + caixa.getDataUltimaAlteraocao() +
                "', ativo = " + caixa.isAtivo() + ", funcionario_atualizacao_id =" + caixa.getFuncionarioAtualizacao().getId() + " where id = " +
                caixa.getId() + " ;";
        this.st.execute(sql);
        return getById(caixa.getId());

    }

    public List<Caixa> getAll(String termoBusca)throws SQLException{
        String sql = "";
        if (termoBusca.length() ==0)
            sql = "select * from caixa ;";
        else if ((termoBusca.matches("[0-9]")))
            sql = "select * from caixa  where  id = "+termoBusca+" " ;
        else
            sql = "select * from caixa where upper(nome) like upper('%"+termoBusca+"%') ;";

        ResultSet rs = this.st.executeQuery(sql);
        List<Caixa> caixas = new ArrayList<>();
        while (rs.next()) {
            caixas.add(getById(rs.getInt("id")));
        }
        return caixas;
    }

    public Caixa getById(Integer id)throws SQLException {
        String sql = "SELECT * FROM caixa WHERE id = " + id + " ;";
        ResultSet rs = this.st.executeQuery(sql);
        if (rs.next()){
            Caixa caixa = new Caixa();
            caixa.setId(rs.getInt("id"));
            caixa.setNome(rs.getString("nome"));
            caixa.setDataCadastro(rs.getDate("data_cadastro"));
            caixa.setDataUltimaAlteraocao(rs.getDate("data_ultima_alteracao"));
            caixa.setAtivo(rs.getBoolean("ativo"));
            return caixa;
        }
        return null;
    }





}
