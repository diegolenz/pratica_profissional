package imp.comercial.venda;

import com.sun.org.apache.xpath.internal.operations.Bool;
import imp.AbstractDao;
import imp.financeiro.condicaoPagamentoDAO.CondicaoPagamentoDAO;
import imp.financeiro.contas_a_receber.ContaReceberDao;
import imp.financeiro.formaPagamentoDAO.FormaPagamentoDAO;
import imp.pessoa.ClienteDAO;
import imp.produto.ProdutoDao;
import imp.sistema.FuncionarioDao;
import lib.model.comercial.ItemProduto;
import lib.model.comercial.VendaProduto;
import lib.model.comercial.frete.TipoFrete;
import lib.model.financeiro.contas.ContaPagar;
import lib.model.financeiro.contas.ContaReceber;
import lib.model.produto.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VendaProdutoDao extends AbstractDao {


    public void saveItens(List<ItemProduto> itensProduto) throws Exception {
        for (ItemProduto itemProduto : itensProduto) {
            String sql = "INSERT INTO item_produto_venda (quantidade, valor_unitario, desconto_unitario, acrescimo_unitario, valor_rateio, valor_total, produto_id, " +
                    "serie_venda, " +
                    "numero_venda," +
                    "modelo_venda)" +
                    " VALUES ("
                    + itemProduto.getQuantidade() +
                    ", " + itemProduto.getValorUnitario() +
                    ", " + itemProduto.getDescontoUnitario() +
                    ", " + itemProduto.getAcrescimoUnitario() +
                    ", " + itemProduto.getValorRateio() +
                    ", " + itemProduto.getValorTotal() +
                    ", " + itemProduto.getId() +
                    ", " + itemProduto.getVenda().getNumSerieNota() +
                    ", " + itemProduto.getVenda().getNumeroNota() +
                    ", '" + itemProduto.getVenda().getModeloNota()
                    + "' );";
            this.st.execute(sql);
        }
    }

    public void save(VendaProduto venda) throws Exception {
        String sql = "INSERT INTO venda_produto (" +
                " numero," +
                " modelo," +
                " serie, " +
                " data_chegada," +
                " data_emissao," +
                " cliente_id," +
                " funcionario_id," +
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
                + venda.getFuncionario().getId() + ", "
                + venda.getValorFrete() + ", "
                + venda.getValorSeguro() + ", "
                + venda.getOutrasDespesas() + ", "
                + venda.getTipoFrete().ordinal() + ", "
                + venda.isAtivo() + ", " +
                +venda.getCondicaoPagamento().getId()
                + " ) ; ";

        this.st.getConnection().setAutoCommit(false);
        this.st.executeUpdate(sql);
        this.saveContas(venda.getContas());
        this.saveItens(venda.getItensProdutos());

    }

    public void cancelar(VendaProduto venda) throws Exception {
        String sql = "update venda_produto set ativo = false, motivo_cancelamento = '" + venda.getMotivoCancelamento() + "' where modelo = '" + venda.getModeloNota() + "' and numero = " + venda.getNumeroNota() + " and serie =" + venda.getNumSerieNota() + " ;";
        this.st.execute(sql);
    }

    public void saveContas(List<ContaReceber> contas) throws Exception {
        for (ContaReceber conta : contas) {
            new ContaReceberDao().save(conta);
        }
    }

    public List<ContaReceber> getAllContasByVenda(VendaProduto venda) throws Exception {
        String sql = "SELECT num, num_parcela, cliente_id, modelo, serie FROM conta_receber WHERE modelo = '" + venda.getModeloNota() + "' and num = " + venda.getNumeroNota() + " and serie =" + venda.getNumSerieNota() + ";";
        PreparedStatement preparedStatement = st.getConnection().prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        List<ContaReceber> contas = new ArrayList<ContaReceber>();
        while (rs.next()) {
            ContaReceber contaPagar = new ContaReceberDao().getById(rs.getInt("num"), rs.getInt("num_parcela"), rs.getInt("serie"),
                    rs.getInt("cliente_id"), rs.getString("modelo"));
            if (contaPagar != null)
                contas.add(contaPagar);
            // contaPagar.setParcela(new ParcelaDAO().getByID(rs.getInt("parcela_id")));
//            contaPagar.setDataVencimento(rs.getDate("data_vencimento"));
//            contaPagar.setDataLancamento(rs.getDate("data_lancamento"));
//            contaPagar.setVenda(venda);
//            // contaPagar.setFormaPagamento((FormaPagamento) new FormaPagamentoDAO().getByID(rs.getInt("forma_pagamento_id")));
//            contaPagar.setValor(rs.getDouble("valor"));
//            //contaPagar.setValorRecebido(rs.getDouble("valor_recebido"));
//            contaPagar.setPaga(rs.getBoolean("paga"));
//            contaPagar.setFormaPagamento(new FormaPagamentoDAO().getByID(rs.getInt("forma_pagamento_id")));
            //  contas.add(contaPagar);
        }
        return contas;
    }

    public List<ItemProduto> getAllItensByVenda(VendaProduto venda) throws Exception {
        String sql = "SELECT * FROM item_produto_venda WHERE modelo_venda = '" + venda.getModeloNota() + "' and numero_venda = " + venda.getNumeroNota() + " and serie_venda =" + venda.getNumSerieNota() + ";";
        PreparedStatement preparedStatement = st.getConnection().prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        List<ItemProduto> itens = new ArrayList<ItemProduto>();
        while (rs.next()) {
            ItemProduto itemProduto = new ItemProduto();
            itemProduto.setValorRateio(rs.getDouble("valor_rateio"));
            itemProduto.setValorTotal(rs.getDouble("valor_total"));
            itemProduto.setValorUnitario(rs.getDouble("valor_unitario"));
            itemProduto.setAcrescimoUnitario(rs.getDouble("acrescimo_unitario"));
            itemProduto.setDescontoUnitario(rs.getDouble("desconto_unitario"));
            itemProduto.setQuantidade(rs.getDouble("quantidade"));
            itemProduto.setVenda(venda);
            itemProduto.buildItem((Produto) new ProdutoDao<Produto>().getByID(rs.getInt("produto_id")));
            itens.add(itemProduto);
        }
        return itens;
    }

    public List getAll(String termo, Boolean ativo) throws Exception {
        String sql = "SELECT * FROM venda_produto";
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
            VendaProduto venda = new VendaProduto();
            venda.setNumeroNota(rs.getInt("numero"));
            venda.setModeloNota(rs.getString("modelo"));
            venda.setNumSerieNota(rs.getInt("serie"));
            venda.setAtivo(rs.getBoolean("ativo"));
            venda.setOutrasDespesas(rs.getDouble("outras_despesas"));
            venda.setValorSeguro(rs.getDouble("valor_seguro"));
            venda.setValorFrete(rs.getDouble("valor_frete"));
            venda.setCondicaoPagamento(new CondicaoPagamentoDAO().getByID(rs.getInt("condicao_pagamento_id")));
            venda.setTipoFrete(TipoFrete.getByOrdinal(rs.getInt("tipo_frete")));
            venda.setDtChegada(rs.getDate("data_chegada"));
            venda.setDtEmisssao(rs.getDate("data_emissao"));
            venda.setCliente(new ClienteDAO().getByID(rs.getInt("cliente_id")));
            venda.setContas(this.getAllContasByVenda(venda));
            venda.setItensProdutos(this.getAllItensByVenda(venda));
            venda.setDataCadastro(rs.getDate("data_cadastro"));
            venda.setDataUltimaAlteracao(rs.getDate("data_ultima_alteracao"));
            venda.setFuncionarioCadastro(new FuncionarioDao().getByID(rs.getInt("funcionario_cadastro")));
            venda.setFuncionarioUltimaAtualizacao(new FuncionarioDao().getByID(rs.getInt("funcionario_ultima_alteracao")));
            vendas.add(venda);
        }
        return vendas;
    }

    public List getAllAtivos() throws Exception {
        String sql = "Select * from venda_produto where ativo = " + 1 + " ;";
        ResultSet rs = this.st.executeQuery(sql);
        List vendas = new ArrayList();
        while (rs.next()) {
            VendaProduto venda = new VendaProduto();
            venda.setNumeroNota(rs.getInt("numeroa"));
            venda.setAtivo(rs.getBoolean("ativo"));
            venda.setModeloNota(rs.getString("modelo"));
            venda.setNumSerieNota(rs.getInt("serie"));
            venda.setDtEmisssao(rs.getDate("data_chegada"));
            venda.setDtEmisssao(rs.getDate("data_emissao"));
            venda.setCliente(new ClienteDAO().getByID(rs.getInt("cliente_id")));

            //  venda.setFuncionario(new );
            vendas.add(venda);
        }
        return vendas;
    }

    public Object getByID(String modelo, Integer numero, Integer serie) throws Exception {
        String sql = "Select * from venda_produto where numero = " + numero + " and serie = " + serie + " and modelo = '" + modelo + "' ;";
        PreparedStatement preparedStatement = st.getConnection().prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        VendaProduto venda = null;
        while (rs.next()) {
            venda = new VendaProduto();
            venda.setNumeroNota(rs.getInt("numero"));
            venda.setModeloNota(rs.getString("modelo"));
            venda.setNumSerieNota(rs.getInt("serie"));
            venda.setAtivo(rs.getBoolean("ativo"));
            venda.setOutrasDespesas(rs.getDouble("outras_despesas"));
            venda.setValorSeguro(rs.getDouble("valor_seguro"));
            venda.setValorFrete(rs.getDouble("valor_frete"));
            venda.setCondicaoPagamento(new CondicaoPagamentoDAO().getByID(rs.getInt("condicao_pagamento_id")));
            venda.setTipoFrete(TipoFrete.getByOrdinal(rs.getInt("tipo_frete")));
            venda.setDtChegada(rs.getDate("data_chegada"));
            venda.setDtEmisssao(rs.getDate("data_emissao"));
            venda.setCliente(new ClienteDAO().getByID(rs.getInt("cliente_id")));
            venda.setContas(this.getAllContasByVenda(venda));
            venda.setItensProdutos(this.getAllItensByVenda(venda));
            venda.setDataCadastro(rs.getDate("data_cadastro"));
            venda.setDataUltimaAlteracao(rs.getDate("data_ultima_alteracao"));
            venda.setFuncionarioCadastro(new FuncionarioDao().getByID(rs.getInt("funcionario_cadastro")));
            venda.setFuncionarioUltimaAtualizacao(new FuncionarioDao().getByID(rs.getInt("funcionario_ultima_alteracao")));
        }
        return venda;
    }

}
