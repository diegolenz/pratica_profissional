package lib.service;

import imp.os.OrdemServicoDao;
import lib.model.comercial.ItemProduto;
import lib.model.os.OrdemServico;
import lib.model.servico.ItemServico;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class OrdemServicoService extends Service{

    /*-------------------------------------------------------------------
     *				 		     ATTRIBUTES
     *-------------------------------------------------------------------*/
    private OrdemServicoDao ordemServicoDAO;

    /*-------------------------------------------------------------------
     *				 		     CONSTRUCTOR
     *-------------------------------------------------------------------*/

    public OrdemServicoService() {
        this.ordemServicoDAO = new OrdemServicoDao();
    }

    /*-------------------------------------------------------------------
     *				 		     SERVICES
     *-------------------------------------------------------------------*/

    public void save(Object ordemServico) throws Exception {
        ordemServicoDAO.save((OrdemServico) ordemServico);
    }

//    public void cancelamento(OrdemServico ordemServico) throws Exception {
//        ContaReceberService contaReceberService = new ContaReceberService();
//        for (ContaReceber contaReceber : ordemServico.getContas()){
//            contaReceber.setAtivo(false);
//            conta
//        ordemServicoDAO.cancelar(ordemServico);
//
//    }

    public void update(OrdemServico ordemServico) throws Exception {
         ordemServicoDAO.update(ordemServico);
    }

    public List getAll(String t) throws Exception {
        return ordemServicoDAO.getAll(t);
    }

    public List findByFilters(String descricao, Date dtInicialPeriodo, Date dtFinalPeriodo, String status) throws Exception {
        if (descricao.isEmpty()) {
            descricao = null;
        }
        return ordemServicoDAO.findByFilters(descricao, dtInicialPeriodo, dtFinalPeriodo, status);
    }

    public void deleteProdutos(List list)throws Exception{
        ordemServicoDAO.deleteItensProdutos(list);
    }

    public void deleteServicos(List list)throws Exception{
        ordemServicoDAO.deleteItensServicos(list);
    }

    public void saveProdutos(List list, Integer idos)throws Exception{
        ordemServicoDAO.saveItensProdutos(list, idos);
    }

    public void saveServicos(List list, Integer idos)throws Exception{
        ordemServicoDAO.saveItensServicos(list, idos);
    }

    public void deleteItensProdutos(List<ItemProduto> itens)throws Exception{
        ordemServicoDAO.deleteItensProdutos(itens);
    }

    public void deleteItensServicos(List<ItemServico> itens)throws Exception{
        ordemServicoDAO.deleteItensServicos(itens);
    }


    public OrdemServico getByNumSerieModelo(Integer id) throws Exception {
        return (OrdemServico) ordemServicoDAO.getById(id);
    }

    public List getAllOrdemServicosAtivos(String termo) throws Exception {
        return null;// ordemServicoDAO.getAllOrdemServicos(termo);
    }

    public List getAllOrdemServicos(String termo) throws Exception {
        return null;// ordemServicoDAO.getAllOrdemServicos(termo);
    }

}
