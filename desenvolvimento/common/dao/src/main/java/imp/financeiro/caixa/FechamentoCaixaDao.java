package imp.financeiro.caixa;


import imp.AbstractDao;
import imp.sistema.FuncionarioDao;
import lib.model.financeiro.caixa.FechamentoCaixa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FechamentoCaixaDao extends AbstractDao {

    public FechamentoCaixa save(FechamentoCaixa caixa)throws SQLException {
        String sql = "INSERT INTO caixa (caixa_id, data_fechamento, valor, funcionario_id) values ("+
                "'"+ caixa.getCaixa().getId() + "', " + caixa.getDataFechamento()+ ",  " + caixa.getValorFechamento() +", " +
                caixa.getFuncionario().getId() + " ;";
        this.st.execute(sql);

        ResultSet rs = this.st.executeQuery("Select max(id) from caixa;");
        if (rs.next()){
            return getById(rs.getInt("id"));
        }
        return caixa;
    }

    public FechamentoCaixa update(FechamentoCaixa caixa)throws SQLException{
        String sql = "UPDATE caixa SET caixa_id = " + caixa.getCaixa().getId() + ", data_fechamento = '" + caixa.getDataFechamento() +
                "', valor = " + caixa.getValorFechamento() + ", funcionario_id = "+caixa.getFuncionario().getId()+" where id = " +
                caixa.getId() + " ;";
        this.st.execute(sql);
        return getById(caixa.getId());

    }

    public List<FechamentoCaixa> getAll()throws SQLException{
        String sql = "select * from abertura_caixa ;";
        ResultSet rs = this.st.executeQuery(sql);
        List<FechamentoCaixa> caixas = new ArrayList<>();
        while (rs.next()) {
            caixas.add(getById(rs.getInt("id")));
        }
        return caixas;
    }

    public FechamentoCaixa getById(Integer id)throws SQLException {
        String sql = "SELECT * FROM abertura_caixa WHERE id = " + id + " ;";
        ResultSet rs = this.st.executeQuery(sql);
        if (rs.next()){
            FechamentoCaixa caixa = new FechamentoCaixa();
            caixa.setId(rs.getInt("id"));
            caixa.setCaixa(new CaixaDao().getById(rs.getInt("caixa_id")));
            caixa.setDataFechamento(rs.getDate("data_Abertura"));
            caixa.setFuncionario(new FuncionarioDao().getByID(rs.getInt("funcionario_id")));
            caixa.setValorFechamento(rs.getDouble("valor"));
            return caixa;
        }
        return null;
    }
}
