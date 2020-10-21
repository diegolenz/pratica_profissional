package lib.service;

import imp.comercial.venda.VendaProdutoDao;
import imp.produto.ProdutoDao;
import lib.model.comercial.ItemProduto;
import lib.model.comercial.VendaProduto;
import lib.model.financeiro.contas.ContaReceber;
import lib.model.interno.Funcionario;
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

    public void save(Object venda) throws Exception {
        vendaDAO.save((VendaProduto) venda);
    }

    public void cancelamento(VendaProduto venda, Funcionario func) throws Exception {
        vendaDAO.st.getConnection().setAutoCommit(false);
        ProdutoDao produtoDao = new ProdutoDao();
        produtoDao.setAutoCommit(false);
        for (ItemProduto item : venda.getItensProdutos()) {
            Produto produto = item.buildProduto(item);
            produto.setFuncionarioUltimaAtualizacao(func);
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + item.getQuantidade());
            produtoDao.update(produto);
        }
        ContaReceberService contaReceberService = new ContaReceberService();
        contaReceberService.setAutoCommit(false);
        for (ContaReceber contaReceber : venda.getContas()) {
            contaReceber.setAtivo(false);
            contaReceber.setFuncionarioUltimaAlteracao(func);
            contaReceberService.update(contaReceber);
        }
        vendaDAO.cancelar(venda);
        vendaDAO.commit();
        vendaDAO.setAutoCommit(true);

    }

    public void update(VendaProduto venda) throws Exception {
        // vendaDAO.update(venda);
    }

    public List getAll(String termo, Boolean ativo) throws Exception {
        return vendaDAO.getAll(termo, ativo);
    }

    public VendaProduto getByNumSerieModelo(Integer n, String m, Integer s) throws Exception {
        return (VendaProduto) vendaDAO.getByID(m, n, s);
    }

    public List getAllVendasAtivos(String termo) throws Exception {
        return null;// vendaDAO.getAllVendas(termo);
    }

    public List getAllVendas(String termo) throws Exception {
        return null;// vendaDAO.getAllVendas(termo);
    }


}
