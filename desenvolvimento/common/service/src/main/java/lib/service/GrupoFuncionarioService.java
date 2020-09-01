package lib.service;

import imp.sistema.GrupoOperadorDao;
import lib.model.interno.GrupoFuncionario;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class GrupoFuncionarioService  extends Service{
    private GrupoOperadorDao grupoOperadorDao;

    public GrupoFuncionarioService(){
        grupoOperadorDao = new GrupoOperadorDao();
    }

    public GrupoFuncionario save(GrupoFuncionario grupo)throws SQLException {
        grupo.setDataCadastro(new Date());
        grupo.setDataUltimaAlteracao(new Date());
        return grupoOperadorDao.save(grupo);
    }

    public GrupoFuncionario update(GrupoFuncionario grupoFuncionario) throws SQLException {
        grupoFuncionario.setDataUltimaAlteracao(new Date());
        return grupoOperadorDao.update(grupoFuncionario);
    }

    public List<GrupoFuncionario> getAll(String termos)throws SQLException {
        return grupoOperadorDao.getAll(termos);
    }

    public void rollback()throws SQLException{
        grupoOperadorDao.rollback();
    }

    public void delete(GrupoFuncionario grupoFuncionario)throws SQLException{
        grupoOperadorDao.delete(grupoFuncionario);
    }

    public GrupoFuncionario getById(Integer id)throws SQLException{
        return grupoOperadorDao.getByID(id);
    }
}
