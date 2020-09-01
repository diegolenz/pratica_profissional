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

    public void save(Object ordemServico) throws SQLException {
        ordemServicoDAO.save((OrdemServico) ordemServico);
    }

//    public void cancelamento(OrdemServico ordemServico) throws SQLException {
//        ContaReceberService contaReceberService = new ContaReceberService();
//        for (ContaReceber contaReceber : ordemServico.getContas()){
//            contaReceber.setAtivo(false);
//            conta
//        ordemServicoDAO.cancelar(ordemServico);
//
//    }

    public void update(OrdemServico ordemServico) throws SQLException {
         ordemServicoDAO.update(ordemServico);
    }

    public List getAll() throws SQLException {
        return ordemServicoDAO.getAll();
    }

    public List findByFilters(String descricao, Date dtInicialPeriodo, Date dtFinalPeriodo, String status) throws SQLException {
        if (descricao.isEmpty()) {
            descricao = null;
        }
        return ordemServicoDAO.findByFilters(descricao, dtInicialPeriodo, dtFinalPeriodo, status);
    }

    public void deleteProdutos(List list)throws SQLException{
        ordemServicoDAO.deleteItensProdutos(list);
    }

    public void deleteServicos(List list)throws SQLException{
        ordemServicoDAO.deleteItensServicos(list);
    }

    public void saveProdutos(List list, Integer idos)throws SQLException{
        ordemServicoDAO.saveItensProdutos(list, idos);
    }

    public void saveServicos(List list, Integer idos)throws SQLException{
        ordemServicoDAO.saveItensServicos(list, idos);
    }

    public void deleteItensProdutos(List<ItemProduto> itens)throws SQLException{
        ordemServicoDAO.deleteItensProdutos(itens);
    }

    public void deleteItensServicos(List<ItemServico> itens)throws SQLException{
        ordemServicoDAO.deleteItensServicos(itens);
    }


    public OrdemServico getByNumSerieModelo(Integer id) throws SQLException {
        return (OrdemServico) ordemServicoDAO.getById(id);
    }

    public List getAllOrdemServicosAtivos(String termo) throws SQLException {
        return null;// ordemServicoDAO.getAllOrdemServicos(termo);
    }

    public List getAllOrdemServicos(String termo) throws SQLException {
        return null;// ordemServicoDAO.getAllOrdemServicos(termo);
    }

}
