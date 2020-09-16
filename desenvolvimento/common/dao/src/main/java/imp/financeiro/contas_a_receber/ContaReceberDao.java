package imp.financeiro.contas_a_receber;

import imp.AbstractDao;
import imp.financeiro.formaPagamentoDAO.FormaPagamentoDAO;
import imp.pessoa.ClienteDAO;
import imp.sistema.FuncionarioDao;
import lib.model.financeiro.contas.ContaReceber;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ContaReceberDao extends AbstractDao {

    public List<ContaReceber> getAll(HashMap<String, Object> termos) throws SQLException {

        String sql = "select * from conta_receber ";
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

        List<ContaReceber> contas = new ArrayList<>();
        ResultSet resultSet = this.st.executeQuery(sql);
        while (resultSet.next()) {
            ContaReceber ContaReceber = this.getById(resultSet.getInt("num"), resultSet.getInt("num_parcela"), resultSet.getInt("serie"),
                    resultSet.getInt("cliente_id"), resultSet.getString("modelo"));
            if (ContaReceber != null)
                contas.add(ContaReceber);
        }
        return contas;

    }
//
//    public ContaReceber update(ContaReceber ContaReceber) throws SQLException{
//        String sql = "update conta_pagar set descricao = "+ContaReceber.getDescricao()+", valor_pago = "+ContaReceber.getValorPago()+", paga ="+
//                ContaReceber.getValorPago().equals(ContaReceber.getValor()) + ", data_vencimento = '" +ContaReceber.getDataVencimento() +"', data_pagamento ='" +
//                ContaReceber.getDataPagamento() +"', valor_pago = " + ContaReceber.getValorPago() +", juros = " +ContaReceber.getJuros()+", desconto = " +ContaReceber.getDesconto() +
//                ", multa = " + ContaReceber.getMulta() + " where id = " + ContaReceber.getId() +" ;";
//        this.st.execute(sql);
//        ResultSet rs = this.st.executeQuery("Select max(id) from conta_pagar");
//        if (rs.next()){
//            return getById(rs.getInt("id"));
//        }
//        return null;
//    }

    public ContaReceber getById(Integer num, Integer numParcela, Integer serie, Integer clienteId, String modelo) throws SQLException {
        String sql = "select * from conta_receber where num = " + num + " and num_parcela = " + numParcela + " and cliente_id =" + clienteId +
                " and modelo = '" + modelo + "' and serie = " + serie + ";";
        PreparedStatement preparedStatement = this.st.getConnection().prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        ContaReceber ContaReceber = null;
        if (rs.next()) {
            ContaReceber = new ContaReceber();
            ContaReceber.setDesconto(rs.getDouble("desconto"));
            ContaReceber.setMulta(rs.getDouble("multa"));
            ContaReceber.setJuros(rs.getDouble("juros"));
            ContaReceber.setValor(rs.getDouble("valor"));
            ContaReceber.setFormaPagamento(new FormaPagamentoDAO().getByID(rs.getInt("forma_pagamento_id")));
            ContaReceber.setRecebedor(new ClienteDAO().getByID(rs.getInt("cliente_id")));
            ContaReceber.setPaga(rs.getBoolean("paga"));
            ContaReceber.setValorPago(rs.getDouble("valor_pago"));
            ContaReceber.setDataCadastro(rs.getDate("data_cadastro"));
            ContaReceber.setDataUltAlteracao(rs.getDate("data_ultima_alteracao"));
            ContaReceber.setFuncionarioCadastro(new FuncionarioDao().getByID(rs.getInt("funcionario_cadastro")));
            ContaReceber.setFuncionarioUltimaAlteracao(new FuncionarioDao().getByID(rs.getInt("funcionario_ultima_alteracao")));
            ContaReceber.setDataLancamento(rs.getDate("data_lancamento"));
            ContaReceber.setDataVencimento(rs.getDate("data_vencimento"));
            ContaReceber.setDataPagamento(rs.getDate("data_pagamento"));
            ContaReceber.setNumNota(rs.getInt("num"));
            ContaReceber.setModelo(rs.getString("modelo"));
            ContaReceber.setSerie(rs.getInt("serie"));
            //     ContaReceber.setId(rs.getInt("id"));
            ContaReceber.setAtivo(rs.getBoolean("ativo"));
            ContaReceber.setNumParcela(rs.getInt("num_parcela"));
        }
        return ContaReceber;
    }


    public void save(ContaReceber conta) throws SQLException {

        String sql = "INSERT INTO conta_receber (";

        sql += "modelo, serie, num, valor, data_Lancamento, data_cadastro, data_ultima_alteracao, funcionario_cadastro," +
                " funcionario_ultima_alteracao, data_Vencimento,  forma_pagamento_id, cliente_id, multa, desconto, juros, num_parcela," +
                " ativo) "
                + "values ( ";

        sql += "'" + conta.getModelo() + "', " +
                conta.getSerie() + ", " +
                conta.getNumNota() + ", " +
                conta.getValor() + ", " +
                " now(), " +
                " now(), " +
                " now(), " +
                conta.getFuncionarioCadastro().getId() + ", " +
                conta.getFuncionarioUltimaAlteracao().getId() + ", " +
                "'" + conta.getDataVencimento() + "', " +
                " " + conta.getFormaPagamento().getId() + ", " +
                " " + conta.getRecebedor().getId() + ", " +
                " " + conta.getMulta() + ", " +
                " " + conta.getDesconto() + ", " +
                " " + conta.getJuros() + ", " +
                " " + conta.getNumParcela() + "," +
                " " + conta.isAtivo() +
                ");";
        this.st.execute(sql);

        //return "Salvo com sucesso";
    }

    public ContaReceber update(ContaReceber conta) throws SQLException {
        String sql = "UPDATE conta_receber set ";
//        sql += "  modelo = '" + conta.getModelo() + "', " +
//                " serie = " + conta.getSerie() + ", num = " + conta.getNumNota() + ", ";

        if (conta.isPaga()) {
            sql += "data_pagamento = '" + conta.getDataPagamento() + "', ";
        }
        sql += " data_Vencimento = '" + conta.getDataVencimento() + "',";

        //sql += " valor = " + conta.getValor() + ", data_Lancamento = '" + conta.getDataLancamento() + "', data_Vencimento = '" + conta.getDataVencimento() + "',";
        if (conta.getFormaPagamento() != null) {
            sql += " forma_pagamento_id = " + conta.getFormaPagamento().getId();
        }
//        if (conta.getRecebedor() != null) {
//            sql += ", cliente_id = " + conta.getRecebedor().getId();
//        }
        sql += ", multa =" + conta.getMulta() +
                ", desconto = " + conta.getDesconto() + ", juros = " + conta.getJuros() + ", paga = " + conta.isPaga() + " " +
                ", ativo = " + conta.isAtivo() +
                " where  num = " + conta.getNumNota() + " and num_parcela = " + conta.getNumParcela() + " and cliente_id=" + conta.getRecebedor().getId() + "" +
                " and modelo = '" + conta.getModelo() + "' and  serie = " + conta.getSerie() + ";";

        this.st.execute(sql);

        return conta;
    }


}
