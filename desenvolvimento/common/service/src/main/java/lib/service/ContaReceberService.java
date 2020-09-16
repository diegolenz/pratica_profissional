package lib.service;

import imp.financeiro.contas_a_receber.ContaReceberDao;
import lib.model.financeiro.contas.ContaReceber;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class ContaReceberService {
    private ContaReceberDao contasReceberDao;

    public List<ContaReceber> getAll(HashMap<String, Object> termos) throws SQLException {
        return contasReceberDao.getAll(termos);
    }

    public ContaReceberService(){
        contasReceberDao = new ContaReceberDao();
    }

//    public ContaReceber getById(Integer id)throws SQLException{
//        return contasReceberDao.getById(id);
//    }

    public void save(ContaReceber contaReceber)throws SQLException{
        contasReceberDao.save(contaReceber);
    }

    public ContaReceber update(ContaReceber contaReceber)throws SQLException{
        return contasReceberDao.update(contaReceber);
    }
}
