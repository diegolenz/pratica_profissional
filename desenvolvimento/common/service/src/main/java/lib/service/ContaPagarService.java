package lib.service;

import imp.financeiro.contas.contas_a_pagar.ContasPagarDao;
import lib.model.financeiro.contas.ContaPagar;
import org.springframework.util.Assert;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class ContaPagarService {
    private ContasPagarDao contasPagarDao;

    public List<ContaPagar> getAll(HashMap<String, Object> termos) throws Exception {
        return contasPagarDao.getAll(termos);
    }

    public ContaPagarService(){
        contasPagarDao = new ContasPagarDao();
    }

//    public ContaPagar getById(Integer id)throws Exception{
//        return contasPagarDao.getById(id);
//    }

    public void save(ContaPagar contaPagar)throws Exception{
        contasPagarDao.save(contaPagar);
    }

    public ContaPagar update(ContaPagar contaPagar)throws Exception{
        if (contaPagar.getDataVencimento().before(contaPagar.getDataLancamento()))
            throw new Exception("A data de vencimento não pode ser menor que a data de lançamento ");
        return contasPagarDao.update(contaPagar);
    }
}