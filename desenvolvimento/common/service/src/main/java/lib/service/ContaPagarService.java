package lib.service;

import imp.financeiro.contas.contas_a_pagar.ContasPagarDao;
import lib.model.financeiro.contas.ContaPagar;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class ContaPagarService {
    private ContasPagarDao contasPagarDao;

    public List<ContaPagar> getAll(HashMap<String, Object> termos) throws SQLException {
        return contasPagarDao.getAll(termos);
    }

    public ContaPagarService(){
        contasPagarDao = new ContasPagarDao();
    }

    public ContaPagar getById(Integer id)throws SQLException{
        return contasPagarDao.getById(id);
    }

    public void save(ContaPagar contaPagar)throws SQLException{
        contasPagarDao.save(contaPagar);
    }

    public ContaPagar update(ContaPagar contaPagar)throws SQLException{
        return contasPagarDao.update(contaPagar);
    }
}