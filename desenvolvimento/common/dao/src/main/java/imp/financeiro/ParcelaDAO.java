package imp.financeiro;

import imp.AbstractDao;
import imp.financeiro.condicaoPagamentoDAO.CondicaoPagamentoDAO;
import imp.financeiro.formaPagamentoDAO.FormaPagamentoDAO;
import lib.model.financeiro.CondicaoPagamento.CondicaoPagamento;
import lib.model.financeiro.parcela.Parcela;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParcelaDAO extends AbstractDao {
    public void save(Object obj) throws Exception {
        Parcela parcela = (Parcela) obj;
        String sql = "";
        sql = "INSERT INTO parcela (forma_Pagamento_id, ativo, dias, numero, porcentagem, condicao_pagamento_id ) values (" +
                "" + parcela.getFormaPagamento().getId() + ", " + parcela.getAtivo() + ", " + parcela.getDias() + ", " + parcela.getNumero() + ", "
                + parcela.getPorcentagem() + ", " + parcela.getCondicaoPagamento().getId() +
                " );";
        this.st.executeUpdate(sql);
        parcela.setId(getUltimoId());
    }

    public Integer getUltimoId() throws Exception {

        ResultSet rs = this.st.executeQuery("SELECT * FROM parcela ;");
        Integer id = 0;

        while (rs.next()) {
            id = rs.getInt("id");
        }
        return id;
    }


    public void deleteByID(Object id) throws Exception {
        String sql = "DELETE FROM parcela WHERE id = " + id + " ;";
        this.st.executeUpdate(sql);
    }


    public List getAll() throws Exception {
        ResultSet rs = this.st.executeQuery("SELECT * FROM parcela;");
        List<Parcela> formas = new ArrayList<>();
        while (rs.next()) {
            Parcela parcela = new Parcela();
            parcela.setId(rs.getInt("id"));
            parcela.setFormaPagamento(new FormaPagamentoDAO().getByID(rs.getInt("forma_pagamento_id")));
            parcela.setAtivo(rs.getBoolean("ativo"));
            formas.add(parcela);
        }
        return formas;
    }

    public List getAllAtivos(String termo) throws Exception {
        String sql = "";
        if (termo.length() == 0)
            sql = "SELECT * FROM parcela where ativo = true ;";
        else if ((termo.matches("[0-9]")))
            sql = "Select * from parcela where id = " + termo + " and ativo = true ;";
        else
            sql = "SELECT * FROM parcela WHERE nome like '%" + termo + "%' and ativo = true";
        ResultSet rs = this.st.executeQuery(sql);
        List<Parcela> formas = new ArrayList<>();
        while (rs.next()) {
            Parcela parcela = new Parcela();
            parcela.setId(rs.getInt("id"));
            parcela.setFormaPagamento(new FormaPagamentoDAO().getByID(rs.getInt("forma_pagamento_id")));
            parcela.setAtivo(rs.getBoolean("ativo"));
            formas.add(parcela);
        }
        return formas;
    }

    public Parcela getByID(Integer id) throws Exception {
        PreparedStatement preparedStatement = st.getConnection().prepareStatement("SELECT * FROM parcela WHERE id = " + id + ";");
        //    ResultSet rs = this.st.executeQuery("SELECT * FROM pais WHERE ID = "+id+";");
        ResultSet rs = preparedStatement.executeQuery();
        Parcela parcela = new Parcela();

        while (rs.next()) {
            parcela.setId(rs.getInt("id"));
            parcela.setFormaPagamento(new FormaPagamentoDAO().getByID(rs.getInt("forma_pagamento_id")));
            parcela.setId(rs.getInt("id"));
            parcela.setAtivo(rs.getBoolean("ativo"));
            parcela.setDias(rs.getInt("dias"));
            parcela.setNumero(rs.getInt("numero"));
            parcela.setPorcentagem(rs.getDouble("porcentagem"));
            parcela.setCondicaoPagamento(new CondicaoPagamentoDAO().getByIDSemParcelas(rs.getInt("condicao_pagamento_id")));
        }
        return parcela;
    }


    public void update(Object obj) throws SQLException {
        Parcela parcela = (Parcela) obj;
        String sql = "UPDATE parcela SET forma_pagamento_id = " + parcela.getFormaPagamento().getId() + ", ativo=" + parcela.getAtivo() + " WHERE id = " + parcela.getId() + " ;";
        this.st.executeUpdate(sql);
    }

}
