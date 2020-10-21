package imp.financeiro.formaPagamentoDAO;

import imp.AbstractDao;
import imp.sistema.FuncionarioDao;
import lib.model.financeiro.formaPagamento.FormaPagamento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FormaPagamentoDAO extends AbstractDao {

    public void save(Object obj) throws Exception {
        FormaPagamento formaPagamento = (FormaPagamento) obj;
        String sql = "";
        sql = ("INSERT INTO forma_Pagamento (nome, ativo, data_cadastro, data_ultima_alteracao, funcionario_cadastro_id, funcionario_ultima_alteracao_id) values (" +
                "'" + formaPagamento.getNome() + "', " + formaPagamento.getAtivo() + ", now(), now(), " + ((FormaPagamento) obj).getFuncionarioCadastro().getId() +
                ", " + formaPagamento.getFuncionarioUltimaAlteracao().getId() + " );");

        this.st.executeUpdate(sql);
    }


    public void deleteByID(Object id) throws Exception {
        String sql = "DELETE FROM forma_Pagamento WHERE id = " + id + " ;";
        this.st.executeUpdate(sql);
    }


    public List getAll(String termo) throws SQLException {
        ResultSet rs = this.st.executeQuery("SELECT id FROM forma_Pagamento;");
        List<FormaPagamento> formas = new ArrayList<>();
        while (rs.next()) {
            FormaPagamento formaPagamento = this.getByID(rs.getInt("id"));
            if (formaPagamento != null)
                formas.add(formaPagamento);
        }
        return formas;
    }

    public List getAllAtivos(String termo) throws SQLException {
        String sql = "";
        if (termo.length() == 0)
            sql = "SELECT id FROM forma_Pagamento where ativo = true ;";
        else if ((termo.matches("[0-9]")))
            sql = "Select id from forma_Pagamento where id = " + termo + " and ativo = true ;";
        else
            sql = "SELECT id FROM forma_Pagamento WHERE nome like '%" + termo + "%' and ativo = true";
        ResultSet rs = this.st.executeQuery(sql);
        List<FormaPagamento> formas = new ArrayList<>();
        while (rs.next()) {
            FormaPagamento formaPagamento = this.getByID(rs.getInt("id"));
            if (formaPagamento != null)
                formas.add(formaPagamento);
        }
        return formas;
    }

    public FormaPagamento getByID(Integer id) throws SQLException {
        PreparedStatement preparedStatement = st.getConnection().prepareStatement("SELECT * FROM forma_Pagamento WHERE id = " + id + ";");
        //    ResultSet rs = this.st.executeQuery("SELECT * FROM pais WHERE ID = "+id+";");
        ResultSet rs = preparedStatement.executeQuery();
        FormaPagamento formaPagamento = null;

        while (rs.next()) {
            formaPagamento = new FormaPagamento();
            formaPagamento.setId(rs.getInt("id"));
            formaPagamento.setNome(rs.getString("nome"));
            formaPagamento.setAtivo(rs.getBoolean("ativo"));
            formaPagamento.setDataCadastro(rs.getTimestamp("data_cadastro"));
            formaPagamento.setDataUltAlteracao(rs.getTimestamp("data_ultima_alteracao"));
            formaPagamento.setFuncionarioCadastro(new FuncionarioDao().getNomeAndId(rs.getInt("funcionario_cadastro_id")));
            formaPagamento.setFuncionarioUltimaAlteracao(new FuncionarioDao().getNomeAndId(rs.getInt("funcionario_ultima_alteracao_id")));
        }
        return formaPagamento;
    }


    public void update(Object obj) throws SQLException {
        FormaPagamento formaPagamento = (FormaPagamento) obj;
        String sql = "UPDATE forma_Pagamento SET nome = '" + formaPagamento.getNome() + "', ativo=" + formaPagamento.getAtivo() + "," +
                " funcionario_ultima_alteracao_id = " + formaPagamento.getFuncionarioUltimaAlteracao().getId() + ", data_ultima_alteracao = now() WHERE id = " + formaPagamento.getId() + " ;";
        this.st.executeUpdate(sql);
    }


}
