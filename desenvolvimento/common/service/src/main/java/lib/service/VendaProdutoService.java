package lib.service;

import imp.comercial.venda.VendaProdutoDao;
import imp.produto.ProdutoDao;
import lib.model.comercial.ItemProduto;
import lib.model.comercial.VendaProduto;
import lib.model.financeiro.contas.ContaReceber;
import lib.model.produto.Produto;

import java.sql.SQLException;
import java.util.List;

public class VendaProdutoService extends Service {
    /*-------------------------------------------------------------------
     *				 		     ATTRIBUTES
     *-------------------------------------------------------------------*/
    private VendaProdutoDao vendaDAO;

    /*-------------------------------------------------------------------
     *				 		     CONSTRUCTOR
     *-------------------------------------------------------------------*/

    public VendaProdutoService() {
            this.vendaDAO = new VendaProdutoDao();
    }

    /*-------------------------------------------------------------------
     *				 		     SERVICES
     *-------------------------------------------------------------------*/

    public void save(Object venda) throws SQLException {
        vendaDAO.save((VendaProduto) venda);
    }

    public void cancelamento(VendaProduto venda) throws SQLException {
        vendaDAO.st.getConnection().setAutoCommit(false);
        ProdutoDao produtoDao = new ProdutoDao();
        for (ItemProduto item : venda.getItensProdutos()){
            Produto produto = item.buildProduto(item);
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + item.getQuantidade());
            produtoDao.update(produto);
        }
        ContaReceberService contaReceberService = new ContaReceberService();
        for (ContaReceber contaReceber : venda.getContas()){
            contaReceber.setAtivo(false);
            contaReceberService.update(contaReceber);
        }
    vendaDAO.cancelar(venda);

    }

    public void update(VendaProduto venda) throws SQLException {
        // vendaDAO.update(venda);
    }

    public List getAll(String termo) throws SQLException {
        return vendaDAO.getAll(termo);
    }

    public VendaProduto getByNumSerieModelo(Integer n, String m, Integer s) throws SQLException {
        return (VendaProduto) vendaDAO.getByID(m, n, s);
    }

    public List getAllVendasAtivos(String termo) throws SQLException {
        return null;// vendaDAO.getAllVendas(termo);
    }

    public List getAllVendas(String termo) throws SQLException {
        return null;// vendaDAO.getAllVendas(termo);
    }


}
