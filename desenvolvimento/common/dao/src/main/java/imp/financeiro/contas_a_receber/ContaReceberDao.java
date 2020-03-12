package imp.financeiro.contas_a_receber;

import imp.AbstractDao;
import imp.financeiro.formaPagamentoDAO.FormaPagamentoDAO;
import imp.pessoa.ClienteDAO;
import lib.model.financeiro.contas.ContaReceber;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ContaReceberDao extends AbstractDao {

    public List<ContaReceber> getByCnpj(String cnpj) throws SQLException {
        String sql = "select * from conta_receber where cliente_id in (select id from cliente where cpf_cnpj = '" + cnpj +"');";
        ResultSet resultSet = this.st.getConnection().prepareStatement(sql).executeQuery();
        List<ContaReceber> contas = new ArrayList<>();
        while (resultSet.next()){
            contas.add(getById(resultSet.getInt("id")));
        }
        return contas;
    }

    public List<ContaReceber> getAll(HashMap<String, Object> termos)throws SQLException {

        String sql = "select * from conta_receber ";
        Date dataLancamento = (Date) termos.get("data_lancamento");
        StringBuilder termosString = new StringBuilder();
        if (dataLancamento != null){
            termosString.append(" data_lancamento >= '" + dataLancamento + "' ");
        }
        Date dataLancamentoFinal = (Date) termos.get("data_lancamento_final");
        if (dataLancamentoFinal != null){
            if (termosString.length()>0)
                termosString.append(" and");
            termosString.append(" data_lancamento <= '" + dataLancamentoFinal + "' ");
        }

        Date dataPagamento = (Date) termos.get("data_pagamento");
        if (dataPagamento != null){
            if (termosString.length()>0)
                termosString.append(" and");
            termosString.append(" data_pagamento >= '" + dataPagamento + "' ");
        }
        Date dataPagamentoFinal = (Date) termos.get("data_pagamento");
        if (dataPagamentoFinal != null){
            if (termosString.length()>0)
                termosString.append(" and");
            termosString.append(" data_pagamento <= '" + dataPagamentoFinal + "' ");
        }

        Date dataVencimento = (Date) termos.get("data_vencimento");
        if (dataLancamento != null ) {
            if (termosString.length()>0)
                termosString.append(" and");
            termosString.append(" and data_vencimento '" + dataVencimento + "'");
        }
        Date dataVencimentoFinal = (Date) termos.get("data_vencimento_final");
        if (dataVencimentoFinal != null ) {
            if (termosString.length()>0)
                termosString.append(" and");
            termosString.append(" and data_vencimento '" + dataVencimentoFinal + "'");
        }


        if (termosString.length()>0){
            sql += " where " + termosString;
        }
        sql += " ;";

        List<ContaReceber> contas = new ArrayList<ContaReceber>();
        ResultSet resultSet = this.st.getConnection().prepareStatement(sql).executeQuery();
        while (resultSet.next()){
            contas.add(getById(resultSet.getInt("id")));
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

    public ContaReceber getById(Integer id)throws SQLException{
        String sql = "select * from conta_RECEBER where id = " + id + " ;";
        ResultSet rs = this.st.executeQuery(sql);
        ContaReceber contaPagar = null;
        if (rs.next()){
            contaPagar = new ContaReceber();
            contaPagar.setDescricao(rs.getString("descricao"));
            contaPagar.setDesconto(rs.getDouble("desconto"));
            contaPagar.setMulta(rs.getDouble("multa"));
            contaPagar.setJuros(rs.getDouble("juros"));
            contaPagar.setValor(rs.getDouble("valor"));
            contaPagar.setFormaPagamento(new FormaPagamentoDAO().getByID(rs.getInt("forma_pagamento_id")));
            contaPagar.setRecebedor(new ClienteDAO().getByID(rs.getInt("cliente_id")));
            contaPagar.setPaga(rs.getBoolean("paga"));
            contaPagar.setValorPago(rs.getDouble("valor_pago"));
            contaPagar.setDataLancamento(rs.getDate("data_lancamento"));
            contaPagar.setDataVencimento(rs.getDate("data_vencimento"));
            contaPagar.setDataPagamento(rs.getDate("data_pagamento"));
            contaPagar.setId(rs.getInt("id"));
          //  contaPagar.setFormaPagamento(new FormaPagamentoService().getByID(rs.getInt("forma_pagamento_id")));
        }
        return contaPagar;
    }


    public void save(ContaReceber conta) throws SQLException {

        String sql = "INSERT INTO conta_receber (descricao,";
        if (conta.getVenda() != null) {
            sql += "modelo_compra, serie_compra, numero_compra,";
        }
        sql +="valor, data_Lancamento, data_Vencimento,  forma_pagamento_id, fornecedor_id, multa, desconto, juros) "
                + "values ('" +
                conta.getDescricao() + "', " ;

        if (conta.getVenda() != null)
            sql += conta.getVenda().getModeloNota() + "', " +
                    conta.getVenda().getNumSerieNota() + ", " +
                    conta.getVenda().getNumeroNota() + ", " ;
        sql += conta.getValor() + ", " +
                "'" + conta.getDataLancamento() + "', " +
                "' " + conta.getDataVencimento() + "', " +
                " " + conta.getFormaPagamento().getId() + ", " +
                " " + conta.getRecebedor().getId() + ", " +
                " " + conta.getMulta() + ", " +
                " " + conta.getDesconto()+ ", " +
                " " + conta.getJuros()  + " " +
                ");";
        this.st.execute(sql);

        //return "Salvo com sucesso";
    }

    public ContaReceber update(ContaReceber conta) throws SQLException {
        String sql = "UPDATE conta_receber set descricao = '" + conta.getDescricao() + "', ";
        if (conta.getVenda() != null) {
            sql += "  modelo_compra = '" + conta.getVenda().getModeloNota() + "', " +
                    " serie_compra = " + conta.getVenda().getNumSerieNota() + ", numero_compra = " + conta.getVenda().getNumSerieNota() + ", ";
        }

        sql += " valor = "+ conta.getValor() + ", data_Lancamento = '" + conta.getDataLancamento() + "', data_Vencimento = '" + conta.getDataVencimento() + "'," +
                "  forma_pagamento_id= " + conta.getFormaPagamento().getId() + ",  fornecedor_id = " + conta.getRecebedor().getId() +
                ", multa " +  conta.getMulta() +
                ", desconto = " + conta.getDesconto()+ ", juros = "  + conta.getJuros()  + ";";

        this.st.execute(sql);

        ResultSet rs = this.st.executeQuery("Select max(id) from conta_pagar");
        if (rs.next()){
            return getById(rs.getInt("id"));
        }
        return null;
    }




}
