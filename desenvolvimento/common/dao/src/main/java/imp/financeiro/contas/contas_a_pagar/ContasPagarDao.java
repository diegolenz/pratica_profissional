package imp.financeiro.contas.contas_a_pagar;

import imp.AbstractDao;
import imp.financeiro.formaPagamentoDAO.FormaPagamentoDAO;
import imp.pessoa.FornecedorDao;
import lib.model.financeiro.contas.ContaPagar;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ContasPagarDao extends AbstractDao {

    public List<ContaPagar> getAll(HashMap<String, Object> termos) throws SQLException {

        String sql = "select * from conta_pagar ";
        Date dataLancamento = (Date) termos.get("data_lancamento");
        StringBuilder termosString = new StringBuilder();
        if (dataLancamento != null) {
            termosString.append(" data_lancamento >= '" + dataLancamento + "' ");
        }
        Date dataLancamentoFinal = (Date) termos.get("data_lancamento_final");
        if (dataLancamentoFinal != null) {
            if (termosString.length() > 0)
                termosString.append(" and");
            termosString.append(" data_lancamento <= " + dataLancamentoFinal + " ");
        }

        Date dataPagamento = (Date) termos.get("data_pagamento");
        if (dataPagamento != null) {
            if (termosString.length() > 0)
                termosString.append(" and");
            termosString.append(" data_pagamento >= " + dataPagamento + " ");
        }
        Date dataPagamentoFinal = (Date) termos.get("data_pagamento");
        if (dataPagamentoFinal != null) {
            if (termosString.length() > 0)
                termosString.append(" and");
            termosString.append(" data_pagamento <= " + dataPagamentoFinal + " ");
        }

        Date dataVencimento = (Date) termos.get("data_vencimento");
        if (dataLancamento != null) {
            if (termosString.length() > 0)
                termosString.append(" and");
            termosString.append(" and data_vencimento " + dataVencimento + "");
        }
        Date dataVencimentoFinal = (Date) termos.get("data_vencimento_final");
        if (dataVencimentoFinal != null) {
            if (termosString.length() > 0)
                termosString.append(" and");
            termosString.append(" and data_vencimento " + dataVencimentoFinal + "");
        }


        if (termosString.length() > 0) {
            sql += " where " + termosString;
        }
        sql += " ;";

        List<ContaPagar> contas = new ArrayList<>();
        ResultSet resultSet = this.st.executeQuery(sql);
        while (resultSet.next()) {
            ContaPagar contaPagar = new ContaPagar();
            contaPagar.setDescricao(resultSet.getString("descricao"));
            contaPagar.setDesconto(resultSet.getDouble("desconto"));
            contaPagar.setMulta(resultSet.getDouble("multa"));
            contaPagar.setJuros(resultSet.getDouble("juros"));
            contaPagar.setValor(resultSet.getDouble("valor"));
            contaPagar.setFormaPagamento(new FormaPagamentoDAO().getByID(resultSet.getInt("forma_pagamento_id")));
            contaPagar.setRecebedor(new FornecedorDao().getByID(resultSet.getInt("fornecedor_id")));
            contaPagar.setPaga(resultSet.getBoolean("paga"));
            contaPagar.setValorPago(resultSet.getDouble("valor_pago"));
            contaPagar.setDataLancamento(resultSet.getDate("data_lancamento"));
            contaPagar.setDataVencimento(resultSet.getDate("data_vencimento"));
            contaPagar.setDataPagamento(resultSet.getDate("data_pagamento"));
            contaPagar.setId(resultSet.getInt("id"));
            contas.add(contaPagar);
        }
        return contas;

    }
//
//    public ContaPagar update(ContaPagar contaPagar) throws SQLException{
//        String sql = "update conta_pagar set descricao = "+contaPagar.getDescricao()+", valor_pago = "+contaPagar.getValorPago()+", paga ="+
//                contaPagar.getValorPago().equals(contaPagar.getValor()) + ", data_vencimento = '" +contaPagar.getDataVencimento() +"', data_pagamento ='" +
//                contaPagar.getDataPagamento() +"', valor_pago = " + contaPagar.getValorPago() +", juros = " +contaPagar.getJuros()+", desconto = " +contaPagar.getDesconto() +
//                ", multa = " + contaPagar.getMulta() + " where id = " + contaPagar.getId() +" ;";
//        this.st.execute(sql);
//        ResultSet rs = this.st.executeQuery("Select max(id) from conta_pagar");
//        if (rs.next()){
//            return getById(rs.getInt("id"));
//        }
//        return null;
//    }

    public ContaPagar getById(Integer id) throws SQLException {
        String sql = "select * from conta_pagar where id = " + id + " ;";
        ResultSet rs = this.st.executeQuery(sql);
        ContaPagar contaPagar = null;
        if (rs.next()) {
            contaPagar = new ContaPagar();
            contaPagar.setDescricao(rs.getString("descricao"));
            contaPagar.setDesconto(rs.getDouble("desconto"));
            contaPagar.setMulta(rs.getDouble("multa"));
            contaPagar.setJuros(rs.getDouble("juros"));
            contaPagar.setValor(rs.getDouble("valor"));
            contaPagar.setFormaPagamento(new FormaPagamentoDAO().getByID(rs.getInt("forma_pagamento_id")));
            contaPagar.setRecebedor(new FornecedorDao().getByID(rs.getInt("fornecedor_id")));
            contaPagar.setPaga(rs.getBoolean("paga"));
            contaPagar.setValorPago(rs.getDouble("valor_pago"));
            contaPagar.setDataLancamento(rs.getDate("data_lancamento"));
            contaPagar.setDataVencimento(rs.getDate("data_vencimento"));
            contaPagar.setDataPagamento(rs.getDate("data_pagamento"));
            contaPagar.setId(rs.getInt("id"));
        }
        return contaPagar;
    }


    public void save(ContaPagar conta) throws SQLException {

        String sql = "INSERT INTO conta_pagar (descricao,";
        if (conta.getCompra() != null) {
            sql += "modelo_compra, serie_compra, numero_compra,";
        }
        sql += "valor, data_Lancamento, data_Vencimento,  forma_pagamento_id, fornecedor_id, multa, desconto, juros, num_parcela) "
                + "values ('" +
                conta.getDescricao() + "', ";

        if (conta.getCompra() != null)
            sql += conta.getCompra().getModeloNota() + "', " +
                    conta.getCompra().getNumSerieNota() + ", " +
                    conta.getCompra().getNumeroNota() + ", ";
        sql += conta.getValor() + ", " +
                "'" + conta.getDataLancamento() + "', " +
                "' " + conta.getDataVencimento() + "', " +
                " " + conta.getFormaPagamento().getId() + ", " +
                " " + conta.getRecebedor().getId() + ", " +
                " " + conta.getMulta() + ", " +
                " " + conta.getDesconto() + ", " +
                " " + conta.getJuros() + ", " +
                " " + conta.getNumParcela() + " " +
                ");";
        this.st.execute(sql);

        //return "Salvo com sucesso";
    }

    public ContaPagar update(ContaPagar conta) throws SQLException {
        String sql = "UPDATE conta_pagar set descricao = '" + conta.getDescricao() + "', ";
        if (conta.getCompra() != null) {
            sql += "  modelo_compra = '" + conta.getCompra().getModeloNota() + "', " +
                    " serie_compra = " + conta.getCompra().getNumSerieNota() + ", numero_compra = " + conta.getCompra().getNumSerieNota() + ", ";
        }
        if (conta.isPaga()) {
            sql += "data_pagamento = '" + conta.getDataPagamento() + "',";
        }

        sql += " valor = " + conta.getValor() + ", data_Lancamento = '" + conta.getDataLancamento() + "', data_Vencimento = '" + conta.getDataVencimento() + "'," +
                "  forma_pagamento_id= " + conta.getFormaPagamento().getId() + ",  fornecedor_id = " + conta.getRecebedor().getId() +
                ", multa =" + conta.getMulta() +
                ", desconto = " + conta.getDesconto() + ", juros = " + conta.getJuros() + ", paga = " + conta.isPaga() + " where id = " + conta.getId() + " ;";

        this.st.execute(sql);

        ResultSet rs = this.st.executeQuery("Select max(id) from conta_pagar");
        if (rs.next()) {
            return getById(rs.getInt("max"));
        }
        return null;
    }


}
