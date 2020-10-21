package lib.service;

import imp.comercial.venda.VendaServicoDao;
import lib.model.comercial.VendaProduto;
import lib.model.comercial.VendaServico;
import lib.model.financeiro.contas.ContaReceber;

import java.sql.SQLException;
import java.util.List;

public class VendaServicoService extends Service {
    /*-------------------------------------------------------------------
     *				 		     ATTRIBUTES
     *-------------------------------------------------------------------*/
    private VendaServicoDao vendaDAO;

    /*-------------------------------------------------------------------
     *				 		     CONSTRUCTOR
     *-------------------------------------------------------------------*/

    public VendaServicoService() {
            this.vendaDAO = new VendaServicoDao();
    }

    /*-------------------------------------------------------------------
     *				 		     SERVICES
     *-------------------------------------------------------------------*/

    public void save(Object venda) throws Exception {
        vendaDAO.save((VendaServico) venda);
    }

    public void cancelamento(VendaServico venda) throws Exception {
        ContaReceberService contaReceberService = new ContaReceberService();
        for (ContaReceber contaReceber : venda.getContas()){
            contaReceber.setAtivo(false);
            contaReceberService.update(contaReceber);
        }
        vendaDAO.cancelar(venda);

    }

    public void update(VendaProduto venda) throws Exception {
        // vendaDAO.update(venda);
    }

    public List getAll(String termo,  Boolean ativo) throws Exception {
        return vendaDAO.getAll(termo,  ativo);
    }

    public VendaServico getByNumSerieModelo(Integer n, String m, Integer s) throws Exception {
        return (VendaServico) vendaDAO.getByID(m, n, s);
    }

    public List getAllVendasAtivos(String termo) throws Exception {
        return null;// vendaDAO.getAllVendas(termo);
    }

    public List getAllVendas(String termo) throws Exception {
        return null;// vendaDAO.getAllVendas(termo);
    }


}
