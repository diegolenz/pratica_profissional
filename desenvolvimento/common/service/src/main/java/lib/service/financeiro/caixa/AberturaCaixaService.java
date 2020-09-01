package lib.service.financeiro.caixa;

import imp.AbstractDao;
import imp.financeiro.caixa.AberturaCaixaDao;
import lib.model.financeiro.caixa.AberturaCaixa;

import java.sql.SQLException;
import java.util.List;

public class AberturaCaixaService extends AbstractDao {

    private AberturaCaixaDao aberturaCaixaDao;

    public AberturaCaixaService(){
        aberturaCaixaDao = new AberturaCaixaDao();
    }

    public AberturaCaixa save(AberturaCaixa aberturaCaixa)throws SQLException{
        return aberturaCaixaDao.save(aberturaCaixa);
    }

    public AberturaCaixa update(AberturaCaixa aberturaCaixa)throws SQLException{
        return aberturaCaixaDao.update(aberturaCaixa);
    }

    public List<AberturaCaixa> getAll()throws SQLException{
        return aberturaCaixaDao.getAll();
    }

    public AberturaCaixa getById(Integer id)throws SQLException{
        return aberturaCaixaDao.getById(id);
    }

}
