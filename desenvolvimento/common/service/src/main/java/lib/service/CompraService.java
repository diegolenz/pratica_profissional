package lib.service;

import imp.comercial.compras.CompraDao;
import imp.produto.ProdutoDao;
import lib.model.comercial.Compra;
import lib.model.comercial.ItemProduto;
import lib.model.financeiro.contas.ContaPagar;
import lib.model.interno.Funcionario;
import lib.model.produto.Produto;

import java.sql.SQLException;
import java.util.List;

public class CompraService extends Service{
    /*-------------------------------------------------------------------
     *				 		     ATTRIBUTES
     *-------------------------------------------------------------------*/
    private CompraDao compraDAO;

    /*-------------------------------------------------------------------
     *				 		     CONSTRUCTOR
     *-------------------------------------------------------------------*/

    public CompraService() {
        this.compraDAO = new CompraDao();
    }

    /*-------------------------------------------------------------------
     *				 		     SERVICES
     *-------------------------------------------------------------------*/

    public void save(Object compra) throws Exception {
        compraDAO.save((Compra) compra);
    }

    public void cancelamento(Compra compra, Funcionario func) throws Exception {
        compraDAO.st.getConnection().setAutoCommit(false);

        ProdutoService produtoService = new ProdutoService();
        produtoService.setAutoCommit(false);
        for (ItemProduto item : compra.getItensProdutos()){
            Produto produto = item.buildProduto(item);
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - item.getQuantidade());
            produto.setFuncionarioUltimaAtualizacao(func);
            produtoService.update(produto);
        }
        ContaPagarService contaPagarService = new ContaPagarService();
        for (ContaPagar contaPagar : compra.getContas()){
            contaPagar.setAtivo(false);
            contaPagarService.update(contaPagar);
        }

        compraDAO.cancelar(compra);

        compraDAO.commit();
        compraDAO.setAutoCommit(true);
    }

    public void update(Compra compra) throws Exception {
        // compraDAO.update(compra);
    }

    public List getAll(String termo) throws Exception {
        return compraDAO.getAll(termo);
    }

    public List findAllByFilters(String fornecedor, Integer num, Integer serie, String modelo, Boolean ativo) throws Exception {
        return compraDAO.findByFilters( fornecedor,  num,  serie,  modelo, ativo);
    }

    public Compra getByNumSerieModelo(Integer n, String m, Integer s,Integer f) throws Exception {
        return (Compra) compraDAO.getByID(m, n, s, f);
    }

    public List getAllComprasAtivos(String termo) throws Exception {
        return null;// compraDAO.getAllCompras(termo);
    }

    public List getAllCompras(String termo) throws Exception {
        return null;// compraDAO.getAllCompras(termo);
    }


}
