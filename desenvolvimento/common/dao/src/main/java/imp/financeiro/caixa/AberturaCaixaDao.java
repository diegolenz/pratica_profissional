package imp.financeiro.caixa;

import imp.AbstractDao;
import imp.sistema.FuncionarioDao;
import lib.model.financeiro.caixa.AberturaCaixa;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AberturaCaixaDao extends AbstractDao {


    public AberturaCaixa save(AberturaCaixa caixa)throws SQLException {
        String sql = "INSERT INTO caixa (caixa_id, data_fechamento, valor, funcionario_id) values ("+
                "'"+ caixa.getCaixa().getId() + "', " + caixa.getDataAbertura()+ ",  " + caixa.getValor() +", " +
                caixa.getFuncionario().getId() + " ;";
        this.st.execute(sql);

        ResultSet rs = this.st.executeQuery("Select max(id) from caixa;");
        if (rs.next()){
            return getById(rs.getInt("id"));
        }
        return caixa;
    }

    public AberturaCaixa update(AberturaCaixa caixa)throws SQLException{
        String sql = "UPDATE caixa SET caixa_id = " + caixa.getCaixa().getId() + ", data_sbertura = '" + caixa.getDataAbertura() +
                "', valoe = " + caixa.getValor() + ", funcionario_id = "+caixa.getFuncionario().getId()+" where id = " +
                caixa.getId() + " ;";
        this.st.execute(sql);
        return getById(caixa.getId());

    }

    public List<AberturaCaixa> getAll()throws SQLException{
        String sql = "select * from abertura_caixa ;";
        ResultSet rs = this.st.executeQuery(sql);
        List<AberturaCaixa> caixas = new ArrayList<>();
        while (rs.next()) {
            caixas.add(getById(rs.getInt("id")));
        }
        return caixas;
    }

    public AberturaCaixa getById(Integer id)throws SQLException {
        String sql = "SELECT * FROM abertura_caixa WHERE id = " + id + " ;";
        ResultSet rs = this.st.executeQuery(sql);
        if (rs.next()){
            AberturaCaixa caixa = new AberturaCaixa();
            caixa.setId(rs.getInt("id"));
            caixa.setCaixa(new CaixaDao().getById(rs.getInt("caixa_id")));
            caixa.setDataAbertura(rs.getDate("data_Abertura"));
            caixa.setFuncionario(new FuncionarioDao().getByID(rs.getInt("funcionario_id")));
            caixa.setValor(rs.getDouble("valor"));
            return caixa;
        }
        return null;
    }
}
