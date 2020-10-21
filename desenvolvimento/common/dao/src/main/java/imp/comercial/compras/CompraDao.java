package imp.comercial.compras;

import imp.AbstractDao;
import imp.financeiro.condicaoPagamentoDAO.CondicaoPagamentoDAO;
import imp.financeiro.contas.contas_a_pagar.ContasPagarDao;
import imp.financeiro.formaPagamentoDAO.FormaPagamentoDAO;
import imp.pessoa.FornecedorDao;
import imp.produto.ProdutoDao;
import imp.sistema.FuncionarioDao;
import lib.model.comercial.Compra;
import lib.model.financeiro.contas.ContaPagar;
import lib.model.comercial.ItemProduto;
import lib.model.comercial.frete.TipoFrete;
import lib.model.pessoa.fornecedor.Fornecedor;
import lib.model.produto.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompraDao extends AbstractDao {

    public void saveItens(List<ItemProduto> itensProduto) throws Exception {
        for (ItemProduto itemProduto : itensProduto) {
            String sql = "INSERT INTO item_produto (quantidade, valor_unitario, desconto_unitario, acrescimo_unitario, valor_rateio, valor_total, produto_id, " +
                    "serie_compra, " +
                    "numero_compra," +
                    "modelo_compra)" +
                    " VALUES ("
                    + itemProduto.getQuantidade() +
                    ", " + itemProduto.getValorUnitario() +
                    ", " + itemProduto.getDescontoUnitario() +
                    ", " + itemProduto.getAcrescimoUnitario() +
                    ", " + itemProduto.getValorRateio() +
                    ", " + itemProduto.getValorTotal() +
                    ", " + itemProduto.getId() +
                    ", " + itemProduto.getCompra().getNumSerieNota() +
                    ", " + itemProduto.getCompra().getNumeroNota() +
                    ", '" + itemProduto.getCompra().getModeloNota()
                    + "' );";
            this.st.execute(sql);
        }
    }

    public void save(Compra compra) throws Exception {
        String sql = "INSERT INTO compra (" +
                " numero," +
                " modelo," +
                " serie, " +
                " data_chegada," +
                " data_emissao," +
                " fornecedor_id," +
                //   " funcionario_id," +
                " valor_frete, " +
                " valor_seguro," +
                " outras_despesas, " +
                " tipo_frete, " +
                " ativo, " +
                " condicao_pagamento_id, " +
                " funcionario_cadastro, " +
                " funcionario_ultima_alteracao, " +
                " data_cadastro, " +
                " data_ultima_alteracao " +
                ") values ("
                + compra.getNumeroNota() + ", '"
                + compra.getModeloNota() + "', "
                + compra.getNumSerieNota() + ", '"
                + compra.getDtChegada() + "', '"
                + compra.getDtEmisssao() + "', "
                + compra.getFornecedor().getId() + ", "
                //  + compra.getFuncionario().getId() + ", "
                + compra.getValorFrete() + ", "
                + compra.getValorSeguro() + ", "
                + compra.getOutrasDespesas() + ", "
                + compra.getTipoFrete().ordinal() + ", "
                + compra.isAtivo() + ", " +
                +compra.getCondicaoPagamento().getId() + ", "
                + compra.getFuncionarioCadastro().getId() + ", "
                + compra.getFuncionarioUltimaAtualizacao().getId() + ", "
                + " now(), now()"
                + " ) ; ";


        this.st.executeUpdate(sql);
        this.saveContas(compra.getContas());
        this.saveItens(compra.getItensProdutos());

    }

    public void cancelar(Compra compra) throws Exception {
        String sql = "update compra set ativo = false, motivo_cancelamento = '" + compra.getMotivoCancelamento() + "'," +
                " data_ultima_alteracao = now(), " +
                " funcionario_ultima_alteracao = " + compra.getFuncionarioUltimaAtualizacao().getId() +
                " where modelo = '" + compra.getModeloNota() + "' and numero = " + compra.getNumeroNota() + " and serie =" + compra.getNumSerieNota() + " ;";
        this.st.execute(sql);
    }

    public void saveContas(List<ContaPagar> contas) throws Exception {
        for (ContaPagar conta : contas) {
            new ContasPagarDao().save(conta);
//            String sql = "INSERT INTO conta_pagar (modelo, serie, num, valor, data_Lancamento, data_Vencimento, forma_pagamento_id) "
//                    + "values ('" +
//                    conta.getModelo() + "', " +
//                    conta.getSerie() + ", " +
//                    conta.getNumNota() + ", " +
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

    public List<ContaPagar> getAllContasByCompra(Compra compra) throws Exception {
        String sql = "SELECT num_parcela FROM conta_pagar WHERE modelo = '" + compra.getModeloNota() + "' and num = " + compra.getNumeroNota() + " and serie =" + compra.getNumSerieNota() + ";";
        PreparedStatement preparedStatement = st.getConnection().prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        List<ContaPagar> contas = new ArrayList<ContaPagar>();
        while (rs.next()) {
            ContaPagar contaPagar = new ContasPagarDao().getById(compra.getNumeroNota(), rs.getInt("num_parcela"), compra.getNumSerieNota(), compra.getFornecedor().getId(), compra.getModeloNota());
            contas.add(contaPagar);
        }
        return contas;
    }

    public List<ItemProduto> getAllItensByCompra(Compra compra) throws Exception {
        String sql = "SELECT * FROM item_produto WHERE modelo_compra = '" + compra.getModeloNota() + "' and numero_compra = " + compra.getNumeroNota() + " and serie_compra =" + compra.getNumSerieNota() + ";";
        PreparedStatement preparedStatement = st.getConnection().prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        List<ItemProduto> itens = new ArrayList<ItemProduto>();
        while (rs.next()) {
            ItemProduto itemProduto = new ItemProduto();
            itemProduto.setId(rs.getInt("id"));
            itemProduto.setValorRateio(rs.getDouble("valor_rateio"));
            itemProduto.setValorTotal(rs.getDouble("valor_total"));
            itemProduto.setValorUnitario(rs.getDouble("valor_unitario"));
            itemProduto.setAcrescimoUnitario(rs.getDouble("acrescimo_unitario"));
            itemProduto.setDescontoUnitario(rs.getDouble("desconto_unitario"));
            itemProduto.setQuantidade(rs.getDouble("quantidade"));
            itemProduto.setCompra(compra);
            itemProduto.buildItem((Produto) new ProdutoDao<Produto>().getByID(rs.getInt("produto_id")));
            itens.add(itemProduto);
        }
        return itens;
    }

    public List getAll(String termoBusca) throws Exception {
        String sql = "";
        if (termoBusca.length() == 0)
            sql = "SELECT * FROM compra ;";
        else if ((!termoBusca.matches("[0-9]")))
            sql = "Select * from compra where id = " + termoBusca + ";";
        else
            sql = "SELECT * FROM compra WHERE nome = " + termoBusca + ";";

        ResultSet rs = this.st.executeQuery(sql);
        List compras = new ArrayList();
        while (rs.next()) {
            Compra compra = new Compra();
            compra.setNumeroNota(rs.getInt("numero"));
            compra.setModeloNota(rs.getString("modelo"));
            compra.setNumSerieNota(rs.getInt("serie"));
            compra.setAtivo(rs.getBoolean("ativo"));
            compra.setOutrasDespesas(rs.getDouble("outras_despesas"));
            compra.setValorSeguro(rs.getDouble("valor_seguro"));
            compra.setValorFrete(rs.getDouble("valor_frete"));
            compra.setCondicaoPagamento(new CondicaoPagamentoDAO().getByID(rs.getInt("condicao_pagamento_id")));
            compra.setTipoFrete(TipoFrete.getByOrdinal(rs.getInt("tipo_frete")));
            compra.setDtChegada(rs.getDate("data_chegada"));
            compra.setDtEmisssao(rs.getDate("data_emissao"));
            compra.setFornecedor(new FornecedorDao().getByID(rs.getInt("fornecedor_id")));
            compra.setDataCadastro(rs.getDate("data_cadastro"));
            compra.setDataUltimaAlteracao(rs.getDate("data_ultima_alteracao"));
            compra.setFuncionarioCadastro(new FuncionarioDao().getByID(rs.getInt("funcionario_cadastro")));
            compra.setFuncionarioUltimaAtualizacao(new FuncionarioDao().getByID(rs.getInt("funcionario_ultima_alteracao")));
            compra.setContas(this.getAllContasByCompra(compra));
            compra.setItensProdutos(this.getAllItensByCompra(compra));
            compras.add(compra);
        }
        return compras;
    }

    public List findByFilters(String fornecedor, Integer num, Integer serie, String modelo, Boolean ativo) throws Exception {
        String sql =
                "SELECT * FROM compra c ";
        Boolean addAnd = false;
        if ((fornecedor != null && !fornecedor.isEmpty()) || num != null || serie != null || modelo != null || ativo != null) {
            sql += " WHERE ";
        }
        if (fornecedor != null && !fornecedor.isEmpty()) {
            sql += " c.fornecedor_id in (select id from fornecedor f where upper (f.nome) like upper('%" + fornecedor + "%')) ";
            addAnd = true;
        }
        if (num != null) {
            if (addAnd) {
                sql += " and ";
            }
            sql += " c.numero = " + num + " ";
            addAnd = true;
        }
        if (serie != null) {
            if (addAnd) {
                sql += " and ";
            }
            sql += " c.serie = " + serie + " ";
            addAnd = true;
        }
        if (modelo != null) {
            if (addAnd) {
                sql += " and ";
            }
            sql += " c.modelo = '" + modelo + "'  ";
            addAnd = true;
        }
        if (ativo != null) {
            if (addAnd) {
                sql += " and ";
            }
            sql += " c.ativo = " + ativo;
            addAnd = true;
        }
        sql += " ;";

        ResultSet rs = this.st.executeQuery(sql);
        List compras = new ArrayList();
        while (rs.next()) {
            Compra compra = new Compra();
            compra.setNumeroNota(rs.getInt("numero"));
            compra.setModeloNota(rs.getString("modelo"));
            compra.setNumSerieNota(rs.getInt("serie"));
            compra.setAtivo(rs.getBoolean("ativo"));
            compra.setOutrasDespesas(rs.getDouble("outras_despesas"));
            compra.setValorSeguro(rs.getDouble("valor_seguro"));
            compra.setValorFrete(rs.getDouble("valor_frete"));
            compra.setCondicaoPagamento(new CondicaoPagamentoDAO().getByID(rs.getInt("condicao_pagamento_id")));
            compra.setTipoFrete(TipoFrete.getByOrdinal(rs.getInt("tipo_frete")));
            compra.setDtChegada(rs.getDate("data_chegada"));
            compra.setDtEmisssao(rs.getDate("data_emissao"));
            compra.setFornecedor(new FornecedorDao().getByID(rs.getInt("fornecedor_id")));
            compra.setDataCadastro(rs.getDate("data_cadastro"));
            compra.setDataUltimaAlteracao(rs.getDate("data_ultima_alteracao"));
            compra.setFuncionarioCadastro(new FuncionarioDao().getByID(rs.getInt("funcionario_cadastro")));
            compra.setFuncionarioUltimaAtualizacao(new FuncionarioDao().getByID(rs.getInt("funcionario_ultima_alteracao")));
            compra.setContas(this.getAllContasByCompra(compra));
            compra.setItensProdutos(this.getAllItensByCompra(compra));
            compras.add(compra);
        }
        return compras;
    }

//    public List getAllAtivos() throws Exception {
//        String sql = "Select * from compras where ativo = " + 1 + " ;";
//        ResultSet rs = this.st.executeQuery(sql);
//        List compras = new ArrayList();
//        while (rs.next()) {
//            Compra compra = new Compra();
//            compra.setNumeroNota(rs.getInt("numeroa"));
//            compra.setAtivo(rs.getBoolean("ativo"));
//            compra.setModeloNota(rs.getString("modelo"));
//            compra.setNumSerieNota(rs.getInt("serie"));
//            compra.setDtEmisssao(rs.getDate("data_chegada"));
//            compra.setDtEmisssao(rs.getDate("data_emissao"));
//            compra.setFornecedor(new FornecedorDao().getByID(rs.getInt("fornecedor_id")));
//
//            //  compra.setFuncionario(new );
//            compras.add(compra);
//        }
//        return compras;
//    }

    public Object getByID(String modelo, Integer numero, Integer serie, Integer fornecedorId) throws Exception {
        String sql = "Select * from compra where numero = " + numero + " and serie = " + serie + " and modelo = '" + modelo + "' and" +
                " fornecedor_id = " + fornecedorId + " ;";
        PreparedStatement preparedStatement = st.getConnection().prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        Compra compra = null;
        while (rs.next()) {
            compra = new Compra();
            compra.setNumeroNota(rs.getInt("numero"));
            compra.setModeloNota(rs.getString("modelo"));
            compra.setNumSerieNota(rs.getInt("serie"));
            compra.setAtivo(rs.getBoolean("ativo"));
            compra.setOutrasDespesas(rs.getDouble("outras_despesas"));
            compra.setValorSeguro(rs.getDouble("valor_seguro"));
            compra.setValorFrete(rs.getDouble("valor_frete"));
            compra.setCondicaoPagamento(new CondicaoPagamentoDAO().getByID(rs.getInt("condicao_pagamento_id")));
            compra.setTipoFrete(TipoFrete.getByOrdinal(rs.getInt("tipo_frete")));
            compra.setDtChegada(rs.getDate("data_chegada"));
            compra.setDtEmisssao(rs.getDate("data_emissao"));
            compra.setFornecedor(new FornecedorDao().getByID(rs.getInt("fornecedor_id")));
            compra.setDataCadastro(rs.getDate("data_cadastro"));
            compra.setDataUltimaAlteracao(rs.getDate("data_ultima_alteracao"));
            compra.setFuncionarioCadastro(new FuncionarioDao().getByID(rs.getInt("funcionario_cadastro")));
            compra.setFuncionarioUltimaAtualizacao(new FuncionarioDao().getByID(rs.getInt("funcionario_ultima_alteracao")));
            compra.setContas(this.getAllContasByCompra(compra));
            compra.setItensProdutos(this.getAllItensByCompra(compra));
        }
        return compra;
    }

    public Object getByIDMinimize(String modelo, Integer numero, Integer serie, Integer fornecedorId) throws Exception {
        String sql = "Select * from compra where numero = " + numero + " and serie = " + serie + " and modelo = '" + modelo + "' and" +
                " fornecedor_id = " + fornecedorId + " ;";
        PreparedStatement preparedStatement = st.getConnection().prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        Compra compra = null;
        while (rs.next()) {
            compra = new Compra();
            compra.setNumeroNota(rs.getInt("numero"));
            compra.setModeloNota(rs.getString("modelo"));
            compra.setNumSerieNota(rs.getInt("serie"));
        }
        return compra;
    }

}
