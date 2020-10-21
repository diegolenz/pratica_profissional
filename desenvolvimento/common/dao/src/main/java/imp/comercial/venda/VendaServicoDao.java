package imp.comercial.venda;

import imp.AbstractDao;
import imp.financeiro.condicaoPagamentoDAO.CondicaoPagamentoDAO;
import imp.financeiro.contas_a_receber.ContaReceberDao;
import imp.financeiro.formaPagamentoDAO.FormaPagamentoDAO;
import imp.pessoa.ClienteDAO;
import imp.servico.ServicoDao;
import lib.model.comercial.VendaServico;
import lib.model.financeiro.contas.ContaReceber;
import lib.model.servico.ItemServico;
import lib.model.servico.Servico;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VendaServicoDao extends AbstractDao {


    public void saveItens(List<ItemServico> itens) throws Exception {
        for (ItemServico itemServico : itens) {
            String sql = "INSERT INTO item_servico (cliente_id, quantidade, valor_unitario, desconto_unitario, acrescimo_unitario, valor_rateio, valor_total, servico_id, " +
                    "serie_venda, " +
                    "numero_venda," +
                    "modelo_venda)" +
                    " VALUES (" +
                    itemServico.getVenda().getCliente().getId() +
                    ", " + itemServico.getQuantidade() +
                    ", " + itemServico.getValorUnitario() +
                    ", " + itemServico.getDescontoUnitario() +
                    ", " + itemServico.getAcrescimoUnitario() +
                    ", " + itemServico.getValorRateio() +
                    ", " + itemServico.getValorTotal() +
                    ", " + itemServico.getId() +
                    ", " + itemServico.getVenda().getNumSerieNota() +
                    ", " + itemServico.getVenda().getNumeroNota() +
                    ", '" + itemServico.getVenda().getModeloNota()
                    + "' );";
            this.st.execute(sql);
        }
    }

    public void save(VendaServico venda) throws Exception {
        String sql = "INSERT INTO venda_servico (" +
                " numero," +
                " modelo," +
                " serie, " +
                " data_chegada," +
                " data_emissao," +
                " cliente_id," +
                //" funcionario_id," +
                " valor_frete, " +
                " valor_seguro," +
                " outras_despesas, " +
                " tipo_frete, " +
                " ativo, " +
                " condicao_pagamento_id" +
                ") values ("
                + venda.getNumeroNota() + ", '"
                + venda.getModeloNota() + "', "
                + venda.getNumSerieNota() + ", '"
                + venda.getDtChegada() + "', '"
                + venda.getDtEmisssao() + "', "
                + venda.getCliente().getId() + ", "
                //  + venda.getFuncionario().getId() + ", "
                + venda.getValorFrete() + ", "
                + venda.getValorSeguro() + ", "
                + venda.getOutrasDespesas() + ", "
                + venda.getTipoFrete().ordinal() + ", "
                + venda.isAtivo() + ", " +
                +venda.getCondicaoPagamento().getId()
                + " ) ; ";

        this.st.executeUpdate(sql);
        this.saveContas(venda.getContas());
        this.saveItens(venda.getItensServicos());

    }

    public void cancelar(VendaServico venda) throws Exception {
        String sql = "update venda_servico set ativo = false, motivo_cancelamento = '" + venda.getMotivoCancelamento() + "' where modelo = '" + venda.getModeloNota() + "' and numero = " + venda.getNumeroNota() + " and serie =" + venda.getNumSerieNota() + " ;";
        this.st.execute(sql);
    }

    public void saveContas(List<ContaReceber> contas) throws Exception {
        for (ContaReceber conta : contas) {
            new ContaReceberDao().save(conta);
//            String sql = "INSERT INTO conta_receber ( modelo , serie, num, valor, data_Lancamento, data_Vencimento, forma_pagamento_id) "
//                    + "values (" +
//                    conta.getVendaServico().getModeloNota() + "', " +
//                    conta.getVendaServico().getNumSerieNota() + ", " +
//                    conta.getVendaServico().getNumeroNota() + ", " +
//                    conta.getValor() + ", " +
//                    "'" + conta.getDataLancamento() + "', " +
//                    "' " + conta.getDataVencimento() + "', " +
//                    " " + conta.getFormaPagamento().getId() + " " +
//                    //  conta.getStatusConta().ordinal() + ", " +
//                    ");";
//            this.st.execute(sql);
        }
        //return "Salvo com sucesso";
    }

    public List<ContaReceber> getAllContasByVenda(VendaServico venda) throws Exception {
        String sql = "SELECT * FROM conta_receber WHERE modelo = '" + venda.getModeloNota() + "' and num = " + venda.getNumeroNota() + " and serie =" + venda.getNumSerieNota() + ";";
        PreparedStatement preparedStatement = st.getConnection().prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        List<ContaReceber> contas = new ArrayList<ContaReceber>();
        while (rs.next()) {
            ContaReceber contaPagar = new ContaReceberDao().getById(
                    venda.getNumeroNota(),
                    rs.getInt("num_parcela"),
                    venda.getNumSerieNota(),
                    venda.getCliente().getId(),
                    venda.getModeloNota()
            );
            if (contaPagar != null) {
                contas.add(contaPagar);
            }
            // contaPagar.setParcela(new ParcelaDAO().getByID(rs.getInt("parcela_id")));
//            contaPagar.setDataVencimento(rs.getDate("data_vencimento"));
//            contaPagar.setDataLancamento(rs.getDate("data_lancamento"));
//            contaPagar.setDataPagamento(rs.getDate("data_pagamento"));
//            contaPagar.setDesconto(rs.getDouble("desconto"));
//            contaPagar.setJuros(rs.getDouble("juros"));
//            contaPagar.setMulta(rs.getDouble("multa"));
//            contaPagar.setValorPago(rs.getDouble("valor_pago"));
//            contaPagar.setVendaServico(venda);
//            // contaPagar.setFormaPagamento((FormaPagamento) new FormaPagamentoDAO().getByID(rs.getInt("forma_pagamento_id")));
//            contaPagar.setValor(rs.getDouble("valor"));
//            //contaPagar.setValorRecebido(rs.getDouble("valor_recebido"));
//            contaPagar.setPaga(rs.getBoolean("paga"));
//            contaPagar.setFormaPagamento(new FormaPagamentoDAO().getByID(rs.getInt("forma_pagamento_id")));
          //  contas.add(contaPagar);
        }
        return contas;
    }

    public List<ItemServico> getAllItensByVenda(VendaServico venda) throws Exception {
        String sql = "SELECT * FROM item_servico WHERE modelo_venda = '" + venda.getModeloNota() + "' and numero_venda = " + venda.getNumeroNota() + " and serie_venda =" + venda.getNumSerieNota() + ";";
        PreparedStatement preparedStatement = st.getConnection().prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        List<ItemServico> itens = new ArrayList();
        while (rs.next()) {
            ItemServico itemProduto = new ItemServico();
            itemProduto.setId(rs.getInt("id"));
            itemProduto.setValorRateio(rs.getDouble("valor_rateio"));
            itemProduto.setValorTotal(rs.getDouble("valor_total"));
            itemProduto.setValorUnitario(rs.getDouble("valor_unitario"));
            itemProduto.setAcrescimoUnitario(rs.getDouble("acrescimo_unitario"));
            itemProduto.setDescontoUnitario(rs.getDouble("desconto_unitario"));
            itemProduto.setQuantidade(rs.getDouble("quantidade"));
            itemProduto.setVenda(venda);
            itemProduto.buildItem((Servico) new ServicoDao().getByID(rs.getInt("servico_id")));
            itens.add(itemProduto);
        }
        return itens;
    }

    public List getAll(String termo, Boolean ativo) throws Exception {
        String sql = "SELECT * FROM venda_servico";
        if ((termo != null && !termo.isEmpty()) || ativo != null) {
            sql += " WHERE ";
        }
        Boolean addAnd = false;
        if ((termo != null && !termo.isEmpty())) {
            sql += " cliente_id in (select id from cliente where upper(nome) like upper('%" + termo + "%')) ";
            addAnd = true;
        }
        if (ativo != null) {
            if (addAnd)
                sql += " and ";
            sql += " ativo = " + ativo +" ";
        }

        ResultSet rs = this.st.executeQuery(sql);
        List vendas = new ArrayList();
        while (rs.next()) {
            vendas.add(getByID(rs.getString("modelo"), rs.getInt("numero"), rs.getInt("serie")));
        }
        return vendas;
    }

    public List getAllAtivos() throws Exception {
        String sql = "Select * from venda_produto where ativo = " + 1 + " ;";
        ResultSet rs = this.st.executeQuery(sql);
        List vendas = new ArrayList();
        while (rs.next()) {
            vendas.add(getByID(rs.getString("modelo"), rs.getInt("numero"), rs.getInt("serie")));
        }
        return vendas;
    }

    public Object getByID(String modelo, Integer numero, Integer serie) throws Exception {
        String sql = "Select * from venda_servico where numero = " + numero + " and serie = " + serie + " and modelo = '" + modelo + "' ;";
        PreparedStatement preparedStatement = st.getConnection().prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        VendaServico  venda = null;
        while (rs.next()) {
            venda = new VendaServico();
            venda.setNumeroNota(rs.getInt("numero"));
            venda.setModeloNota(rs.getString("modelo"));
            venda.setNumSerieNota(rs.getInt("serie"));
            venda.setDtChegada(rs.getDate("data_chegada"));
            venda.setDtEmisssao(rs.getDate("data_emissao"));
            venda.setOutrasDespesas(rs.getDouble("outras_despesas"));
            venda.setValorSeguro(rs.getDouble("valor_seguro"));
            venda.setValorFrete(rs.getDouble("valor_frete"));
            venda.setAtivo(rs.getBoolean("ativo"));
            //venda.setCliente(new ClienteService().getByID(rs.getInt("funcionario_id")));
            venda.setCliente(new ClienteDAO().getByID(rs.getInt("cliente_id")));
            venda.setContas(getAllContasByVenda(venda));
            venda.setCondicaoPagamento(new CondicaoPagamentoDAO().getByID(rs.getInt("condicao_pagamento_id")));

            venda.setItensServicos(getAllItensByVenda(venda));
        }
        return venda;
    }

}
