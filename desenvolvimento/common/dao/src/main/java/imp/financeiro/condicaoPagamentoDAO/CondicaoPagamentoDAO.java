package imp.financeiro.condicaoPagamentoDAO;

import imp.AbstractDao;
import imp.financeiro.ParcelaDAO;
import imp.financeiro.formaPagamentoDAO.FormaPagamentoDAO;
import lib.model.financeiro.CondicaoPagamento.CondicaoPagamento;
import lib.model.financeiro.parcela.Parcela;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CondicaoPagamentoDAO extends AbstractDao {

    private ParcelaDAO parcelaService;

    public void save(Object obj) throws Exception {
        CondicaoPagamento condicaoPagamento = (CondicaoPagamento) obj;
        String sql = "";
        this.st.getConnection().setAutoCommit(false);

        sql = ("INSERT INTO condicao_Pagamento (nome, ativo, juros, multa, desconto ) values (" +
                "'" + condicaoPagamento.getNome() + "', " + condicaoPagamento.getAtivo() +
                ", " + condicaoPagamento.getJuros() + ", " + condicaoPagamento.getMulta() + "" +
                ", " + condicaoPagamento.getDesconto() + " );");

        this.st.executeUpdate(sql);
        condicaoPagamento.setId(getUltimoId());

        for (Parcela parcela : condicaoPagamento.getParcelas()) {
            String sqlParcelas = "INSERT INTO condicao_pagamento_parcela (condicao_pagamento_id, parcelas_id) values (" + condicaoPagamento.getId() + ", " + parcela.getId() + " );";
            this.st.executeUpdate(sqlParcelas);
        }
        this.st.getConnection().commit();
        this.st.getConnection().setAutoCommit(true);
    }

    public void removeParcelas(List<Parcela> parcelas)  throws Exception {
        for (Parcela parcela : parcelas) {
            this.st.executeQuery("DELETE FROM parcela WHERE id = " + parcela.getId() + " ;");
        }
    }


    public Integer getUltimoId() throws Exception {

        ResultSet rs = this.st.executeQuery("SELECT * FROM condicao_Pagamento;");
        Integer id = 0;

        while (rs.next()) {
            id = rs.getInt("id");
        }
        return id;
    }


    public void deleteByID(Object id) throws Exception {
        String sql = "DELETE FROM condicao_Pagamento WHERE id = " + id + " ;";
        this.st.executeUpdate(sql);
    }


    public List getAll(String termo) throws SQLException {
        String sql = "";
        if (termo.length() == 0)
            sql = "SELECT * FROM condicao_Pagamento where ativo = true ;";
        else if ((termo.matches("[0-9]")))
            sql = "Select * from condicao_Pagamento where id = " + termo + " and ativo = true ;";
        else
            sql = "SELECT * FROM condicao_Pagamento WHERE nome like '%" + termo + "%' and ativo = true";

        ResultSet rs = this.st.executeQuery(sql);

        List<CondicaoPagamento> formas = new ArrayList<>();
        while (rs.next()) {
            Integer id = (rs.getInt("id"));
            formas.add(getByID(id));
        }
        return formas;
    }

    public List getAllAtivos(String termo) throws Exception {
        String sql = "";
        if (termo.length() == 0)
            sql = "SELECT * FROM condicao_Pagamento where ativo = true ;";
        else if ((termo.matches("[0-9]")))
            sql = "Select * from condicao_Pagamento where id = " + termo + " and ativo = true ;";
        else
            sql = "SELECT * FROM condicao_Pagamento WHERE nome like '%" + termo + "%' and ativo = true";
        ResultSet rs = this.st.executeQuery(sql);
        List<CondicaoPagamento> formas = new ArrayList<>();
        while (rs.next()) {
            CondicaoPagamento condicaoPagamento = new CondicaoPagamento();
            condicaoPagamento.setId(rs.getInt("id"));
            condicaoPagamento.setNome(rs.getString("nome"));
            condicaoPagamento.setAtivo(rs.getBoolean("ativo"));
            condicaoPagamento.setMulta(rs.getDouble("multa"));
            condicaoPagamento.setDesconto(rs.getDouble("desconto"));
            condicaoPagamento.setJuros(rs.getDouble("juros"));
            condicaoPagamento.setParcelas(new ArrayList<>());
            PreparedStatement preparedStatement = st.getConnection().prepareStatement("SELECT * FROM condicao_Pagamento_parcela WHERE condicao_pagamento_id = " + condicaoPagamento.getId() + " ;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer is = resultSet.getInt("parcelas_id");
                PreparedStatement prepared = st.getConnection().prepareStatement(
                        "SELECT * FROM parcela WHERE id = " + condicaoPagamento.getId() + " ;");
                ResultSet result = prepared.executeQuery();
                while (result.next()) {
                    Parcela parcela = new Parcela();
                    parcela.setId(result.getInt("id"));
                    parcela.setAtivo(result.getBoolean("ativo"));
                    parcela.setFormaPagamento(new FormaPagamentoDAO().getByID(result.getInt("forma_pagamento_id")));
                    parcela.setDias(result.getInt("dias"));
                    parcela.setNumero(result.getInt("numero"));
                    parcela.setPorcentagem(result.getDouble("porcentagem"));
                    condicaoPagamento.getParcelas().add(parcela);
                }
            }

            formas.add(condicaoPagamento);
        }


        rs = this.st.executeQuery(sql);
        return formas;
    }

    public Parcela getParcelaById(Integer id) throws SQLException {
        PreparedStatement pt = st.getConnection().prepareStatement("SELECT * FROM parcela WHERE id = " + id + ";");
        ResultSet resultSet = pt.executeQuery();
        Parcela parcela = null;
        while (resultSet.next()) {
            parcela = new Parcela();
            parcela.setId(resultSet.getInt("id"));
            parcela.setFormaPagamento(new FormaPagamentoDAO().getByID(resultSet.getInt("forma_pagamento_id")));
            parcela.setPorcentagem(resultSet.getDouble("porcentagem"));
            parcela.setNumero(resultSet.getInt("numero"));
            parcela.setDias(resultSet.getInt("dias"));
            parcela.setAtivo(resultSet.getBoolean("ativo"));
            // parcela.setValor(resultSet.getDouble("valor"));

        }
        return parcela;
    }

    public List<Parcela> getParcelasByIdCondicao(Integer id) throws SQLException {
        PreparedStatement pt = st.getConnection().prepareStatement("SELECT * FROM condicao_Pagamento_parcelas WHERE condicao_Pagamento_id = " + id + ";");
        ResultSet resultSet = pt.executeQuery();
        List<Parcela> parcelas = new ArrayList<>();
        while (resultSet.next()) {
            Integer idParcela = resultSet.getInt("parcela_id");
            parcelas.add(this.getParcelaById(idParcela));
        }
        return parcelas;
    }

    public CondicaoPagamento getByID(Integer id) throws SQLException {
        PreparedStatement preparedStatement = st.getConnection().prepareStatement("SELECT * FROM condicao_Pagamento WHERE id = " + id + ";");
        //    ResultSet rs = this.st.executeQuery("SELECT * FROM pais WHERE ID = "+id+";");
        ResultSet rs = preparedStatement.executeQuery();
        CondicaoPagamento condicaoPagamento = null;

        while (rs.next()) {
            condicaoPagamento = new CondicaoPagamento();
            condicaoPagamento.setId(rs.getInt("id"));
            condicaoPagamento.setNome(rs.getString("nome"));
            condicaoPagamento.setAtivo(rs.getBoolean("ativo"));
            condicaoPagamento.setJuros(rs.getDouble("juros"));
            condicaoPagamento.setMulta(rs.getDouble("multa"));
            condicaoPagamento.setDesconto(rs.getDouble("desconto"));
            condicaoPagamento.setParcelas(new ArrayList<>());
            PreparedStatement ps = st.getConnection().prepareStatement("SELECT * FROM condicao_Pagamento_parcela WHERE condicao_pagamento_id = " + condicaoPagamento.getId() + " ;");
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                Integer idParcela = result.getInt("parcelas_id");
                condicaoPagamento.getParcelas().add(this.getParcelaById(idParcela));
            }
        }
        return condicaoPagamento;
    }

    public CondicaoPagamento getByNome(String nome) throws SQLException {
        String sql = "SELECT * FROM condicao_pagamento WHERE UPPER(nome) LIKE UPPER('" + nome + "')";
        PreparedStatement preparedStatement = st.getConnection().prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        Integer id = null;
        if (rs.next()) {
            id = rs.getInt("id");
        }
        if (id != null)
            return getByID(id);

        return null;
    }

    public CondicaoPagamento getLast() throws Exception {
        String sql = "Select id from condicao_pagamento order by ID desc limit 1;";
        ResultSet resultSet = this.st.executeQuery(sql);
        Integer id = null;
        if (resultSet.next()) {
            id = resultSet.getInt("id");
        }
        Optional<Object> cidade = Optional.ofNullable(this.getByID(id));
        return (CondicaoPagamento) cidade.get();
    }

    public void update(Object obj) throws Exception {
        CondicaoPagamento condicaoPagamento = (CondicaoPagamento) obj;
        String sql = "";

        sql = ("Update condicao_Pagamento set nome ='" + condicaoPagamento.getNome() + "', ativo = " + condicaoPagamento.getAtivo() +
                ", juros = " + condicaoPagamento.getJuros() +
                ", multa = " + condicaoPagamento.getMulta() +
                " desconto = " + condicaoPagamento.getDesconto() + " ;");

        this.st.executeUpdate(sql);
        condicaoPagamento.setId(getUltimoId());

        for (Parcela parcela : condicaoPagamento.getParcelas()) {
            if (parcela.getId() == null) {
                String sqlParcelas = "INSERT INTO condicao_pagamento_parcela (condicao_pagamento_id, parcelas_id) values (" + condicaoPagamento.getId() + ", " + parcela.getId() + " );";
                this.st.executeUpdate(sqlParcelas);
            }
        }
    }

}
