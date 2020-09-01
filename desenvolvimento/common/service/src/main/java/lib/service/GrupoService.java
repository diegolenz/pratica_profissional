package lib.service;

import imp.grupo.GrupoDao;
import lib.model.grupo.Grupo;
import org.springframework.util.Assert;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class GrupoService {

    private GrupoDao grupoDao;

    public  GrupoService(){
        grupoDao = new GrupoDao();
    }

    public void save(Grupo grupo) throws Exception{
        Assert.notNull(grupo, "Grupo não pode ser nula");
        Assert.notNull(grupo.getNome(), "Campo Nome é obrigatório");
        Optional<Grupo> marca1 = Optional.ofNullable(grupoDao.findByNomeExato(grupo.getNome()));
        Assert.isTrue(!marca1.isPresent(), "Nome duplicado. \n Ja existe uma grupo com o mesmo nome cadastrada no sistema");
        grupoDao.save(grupo);
    }

    public void update(Grupo grupo) throws Exception{
        Assert.notNull(grupo, "Grupo não pode ser nula");
        Assert.notNull(grupo.getId(), "Falha Técnica: ID nulo, para um registro ser alterado o objeto deve conter um ID");
        Assert.notNull(grupo.getNome(), "Campo nome é obrigatório");
        Optional<Grupo> marca1 = Optional.ofNullable(grupoDao.findByNomeExato(grupo.getNome()));
        if (marca1.isPresent())
            Assert.isTrue( marca1.get().getId().equals(grupo.getId()), "Nome duplicado. \n Ja existe um grupo com o mesmo nome cadastrada no sistema");
        grupoDao.update(grupo);
    }

    public List getAll(String termoBusca) throws Exception{
        List list = grupoDao.getAll(termoBusca);
        //Assert.isTrue(list.size() > 0, "Nenhum resultado encontrado");
        return list;
    }

    public List getAllAtivos(String termoBusca) throws Exception{
        List list = grupoDao.getAllAtivos(termoBusca);
       // Assert.isTrue(list.size() > 0, "Nenhum resultado encontrado");
        return list;
    }

    public Grupo getLast()throws Exception{
        return (Grupo) grupoDao.findLast().get();
    }

    public Object getByID(Integer id) throws SQLException {

        Grupo grupo = null;
        Optional<Grupo> grupoOptional = grupoDao.getByID(id);
        if (grupoOptional.isPresent() ){
            grupo = grupoOptional.get();
        }
        //Assert.notNull(grupo, "Nenhuma grupo com esse código foi encontrada");
        return grupo;
    }

    public void deleteByID(Integer id)throws Exception{
        grupoDao.deleteByID(id);
    }

}
