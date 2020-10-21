package imp.financeiro.contas.contas_a_pagar;

import imp.AbstractDao;
import imp.comercial.compras.CompraDao;
import imp.financeiro.formaPagamentoDAO.FormaPagamentoDAO;
import imp.pessoa.FornecedorDao;
import imp.sistema.FuncionarioDao;
import lib.model.comercial.Compra;
import lib.model.financeiro.StatusConta;
import lib.model.financeiro.contas.ContaPagar;
import lib.model.interno.Funcionario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ContasPagarDao extends AbstractDao {

    public List<ContaPagar> getAll(HashMap<String, Object> termos) throws Exception {

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
            termosString.append(" data_lancamento <= '" + dataLancamentoFinal + "' ");
        }

        Date dataPagamento = (Date) termos.get("data_pagamento");
        if (dataPagamento != null) {
            if (termosString.length() > 0)
                termosString.append(" and");
            termosString.append(" data_pagamento >= '" + dataPagamento + "' ");
        }
        Date dataPagamentoFinal = (Date) termos.get("data_pagamento");
        if (dataPagamentoFinal != null) {
            if (termosString.length() > 0)
                termosString.append(" and");
            termosString.append(" data_pagamento <= '" + dataPagamentoFinal + "' ");
        }

        Date dataVencimento = (Date) termos.get("data_vencimento");
        if (dataVencimento != null) {
            if (termosString.length() > 0)
                termosString.append(" and");
            termosString.append(" data_vencimento >= '" + dataVencimento + "' ");
        }
        Date dataVencimentoFinal = (Date) termos.get("data_vencimento_final");
        if (dataVencimentoFinal != null) {
            if (termosString.length() > 0)
                termosString.append(" and");
            termosString.append(" data_vencimento <= '" + dataVencimentoFinal + "' ");
        }
        String recebedor = (String) termos.get("recebedor");
        if (recebedor != null && !recebedor.isEmpty()) {
            if (termosString.length() > 0)
                termosString.append(" and");
            termosString.append(" fornecedor_id in (select id from FORNECEDOR where upper(nome) like upper('%" + recebedor + "%')) ");
        }


        if (termosString.length() > 0) {
            sql += " where " + termosString;
        }
        sql += " ;";

        List<ContaPagar> contas = new ArrayList<>();
        ResultSet resultSet = this.st.executeQuery(sql);
        while (resultSet.next()) {
            ContaPagar contaPagar = this.getById(resultSet.getInt("num"), resultSet.getInt("num_parcela"),  resultSet.getInt("serie"),
                    resultSet.getInt("fornecedor_id"), resultSet.getString("modelo"));
            if (contaPagar != null)
                contas.add(contaPagar);
        }
        return contas;

    }
//
//    public ContaPagar update(ContaPagar contaPagar) throws Exception{
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

    public ContaPagar getById(Integer num, Integer numParcela, Integer serie, Integer fornecedorId, String modelo) throws Exception {
        String sql = "select * from conta_pagar where num = " + num + " and num_parcela = " + numParcela + " and fornecedor_id =" + fornecedorId +
                " and modelo = '" + modelo + "' and serie = " + serie + ";";
        PreparedStatement preparedStatement = this.st.getConnection().prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        ContaPagar contaPagar = null;
        if (rs.next()) {
            contaPagar = new ContaPagar();
            contaPagar.setDesconto(rs.getDouble("desconto"));
            contaPagar.setMulta(rs.getDouble("multa"));
            contaPagar.setJuros(rs.getDouble("juros"));
            contaPagar.setValor(rs.getDouble("valor"));
            contaPagar.setFormaPagamento(new FormaPagamentoDAO().getByID(rs.getInt("forma_pagamento_id")));
            contaPagar.setRecebedor(new FornecedorDao().getByID(rs.getInt("fornecedor_id")));
            contaPagar.setPaga(rs.getBoolean("paga"));
            contaPagar.setValorPago(rs.getDouble("valor_pago"));
            contaPagar.setDataCadastro(rs.getDate("data_cadastro"));
            contaPagar.setDataUltAlteracao(rs.getDate("data_ultima_alteracao"));
            contaPagar.setFuncionarioCadastro(new FuncionarioDao().getByID(rs.getInt("funcionario_cadastro")));
            contaPagar.setFuncionarioUltimaAlteracao(new FuncionarioDao().getByID(rs.getInt("funcionario_ultima_alteracao")));
            contaPagar.setDataLancamento(rs.getDate("data_lancamento"));
            contaPagar.setDataVencimento(rs.getDate("data_vencimento"));
            contaPagar.setDataPagamento(rs.getDate("data_pagamento"));
            contaPagar.setNumNota(rs.getInt("num"));
            contaPagar.setModelo(rs.getString("modelo"));
            contaPagar.setSerie(rs.getInt("serie"));
            //contaPagar.setId(rs.getInt("id"));
            contaPagar.setAtivo(rs.getBoolean("ativo"));
            contaPagar.setNumParcela(rs.getInt("num_parcela"));
            contaPagar.setCompra((Compra) new CompraDao().getByIDMinimize(contaPagar.getModelo(), contaPagar.getNumNota(), contaPagar.getSerie(),  contaPagar.getRecebedor().getId()));
        }
        return contaPagar;
    }

    public List<ContaPagar> getByCompra(Integer num, Integer serie, Integer fornecedorId, String modelo) throws Exception {
        String sql = "select * from conta_pagar where num = " + num + " and fornecedor_id =" + fornecedorId +
                " and modelo = '" + modelo + "' and serie = " + serie + ";";
        PreparedStatement preparedStatement = this.st.getConnection().prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        List<ContaPagar> contas = new ArrayList<>();
        while (rs.next()) {
            ContaPagar contaPagar = new ContaPagar();
            contaPagar.setDesconto(rs.getDouble("desconto"));
            contaPagar.setMulta(rs.getDouble("multa"));
            contaPagar.setJuros(rs.getDouble("juros"));
            contaPagar.setValor(rs.getDouble("valor"));
            contaPagar.setFormaPagamento(new FormaPagamentoDAO().getByID(rs.getInt("forma_pagamento_id")));
            contaPagar.setRecebedor(new FornecedorDao().getByID(rs.getInt("fornecedor_id")));
            contaPagar.setPaga(rs.getBoolean("paga"));
            contaPagar.setValorPago(rs.getDouble("valor_pago"));
            contaPagar.setDataCadastro(rs.getDate("data_cadastro"));
            contaPagar.setDataUltAlteracao(rs.getDate("data_ultima_alteracao"));
            contaPagar.setFuncionarioCadastro(new FuncionarioDao().getByID(rs.getInt("funcionario_cadastro")));
            contaPagar.setFuncionarioUltimaAlteracao(new FuncionarioDao().getByID(rs.getInt("funcionario_ultima_alteracao")));
            contaPagar.setDataLancamento(rs.getDate("data_lancamento"));
            contaPagar.setDataVencimento(rs.getDate("data_vencimento"));
            contaPagar.setDataPagamento(rs.getDate("data_pagamento"));
            contaPagar.setNumNota(rs.getInt("num"));
            contaPagar.setModelo(rs.getString("modelo"));
            contaPagar.setSerie(rs.getInt("serie"));
            //contaPagar.setId(rs.getInt("id"));
            contaPagar.setAtivo(rs.getBoolean("ativo"));
            contaPagar.setNumParcela(rs.getInt("num_parcela"));
            contas.add(contaPagar);
        }
        return contas;
    }

    public void save(ContaPagar conta) throws Exception {

        String sql = "INSERT INTO conta_pagar (";
        sql += "modelo, serie, num,";

        sql += "valor, data_Lancamento, data_cadastro, data_ultima_alteracao, funcionario_cadastro, funcionario_ultima_alteracao, data_Vencimento,  forma_pagamento_id, fornecedor_id, multa, desconto, juros, num_parcela, ativo) "
                + "values ( ";

        sql += "'" + conta.getModelo() + "', " +
                conta.getSerie() + ", " +
                conta.getNumNota() + ", ";
        sql += conta.getValor() + ", " +
                "'" + conta.getDataLancamento() + "', " +
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

    public ContaPagar update(ContaPagar conta) throws Exception {

//        if (conta.getCompra() != null) {
//            sql += "  modelo_compra = '" + conta.getCompra().getModeloNota() + "', " +
//                    " serie_compra = " + conta.getCompra().getNumSerieNota() + ", num_compra = " + conta.getCompra().getNumSerieNota() + ", ";
//        }
        String sql = "UPDATE conta_pagar set ";
        if (conta.isPaga()) {
            sql += "data_pagamento = '" + conta.getDataPagamento() + "',";
        }

        sql += " valor = " + conta.getValor() + ", data_Lancamento = '" + conta.getDataLancamento() + "', data_Vencimento = '" + conta.getDataVencimento() + "',";
        if (conta.getFormaPagamento() != null) {
            sql += " forma_pagamento_id = " + conta.getFormaPagamento().getId();
        }
        if (conta.getRecebedor() != null) {
            sql += ", fornecedor_id = " + conta.getRecebedor().getId();
        }
        sql += ", multa =" + conta.getMulta() +
                ", desconto = " + conta.getDesconto() + ", juros = " + conta.getJuros() + ", paga = " + conta.isPaga() + " " +
                ", ativo = " + conta.isAtivo() +
                " where  num = " + conta.getNumNota() + " and num_parcela = " + conta.getNumParcela() + " and fornecedor_id = " + conta.getRecebedor().getId() + "" +
                " and modelo = '" + conta.getModelo() + "' and serie = " + conta.getSerie() + ";";

        this.st.execute(sql);

        return conta;
    }


}
