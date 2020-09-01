package lib.service.financeiro.caixa;

import imp.financeiro.caixa.CaixaDao;
import lib.model.financeiro.caixa.Caixa;

import java.sql.SQLException;
import java.util.List;

public class CaixaService {

    private CaixaDao caixaDao;

    public CaixaService(){
        caixaDao = new CaixaDao();
    }

    public Caixa save(Caixa caixa) throws SQLException{
        return caixaDao.save(caixa);
    }

    public List<Caixa> getAll(String termos)throws SQLException{
        return caixaDao.getAll(termos);
    }

    public Caixa getByID(Integer id)throws SQLException{
        return caixaDao.getById(id);
    }

    public Caixa update(Caixa caixa)throws SQLException{
        return caixaDao.update(caixa);
    }

}
