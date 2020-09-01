package lib.service;

import imp.marca.MarcaDao;
import lib.model.marca.Marca;
import org.springframework.util.Assert;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MarcaService {

    private MarcaDao marcaDao;

    public MarcaService() {
        this.marcaDao = new MarcaDao();
    }

    public void save(Marca marca) throws Exception{
        Assert.notNull(marca, "Marca não pode ser nula");
        Assert.notNull(marca.getNome(), "Campo Nome é obrigatório");
        Optional<Marca> marca1 = Optional.ofNullable(marcaDao.findByNomeExato(marca.getNome()));
        Assert.isTrue(!marca1.isPresent(), "Nome duplicado. \n Ja existe uma marca com o mesmo nome cadastrada no sistema");
        marcaDao.save(marca);
    }

    public Marca findLast()throws Exception{
        return (Marca) marcaDao.findLast().get();

    }

    public void update(Marca marca) throws Exception{
        Assert.notNull(marca, "Marca não pode ser nula");
        Assert.notNull(marca, "Falha Técnica: ID nulo, para um registro ser alterado o objeto deve conter um ID");
        Assert.notNull(marca.getNome(), "Campo Nome é obrigatório");
        Optional<Marca> marca1 = Optional.ofNullable(marcaDao.findByNomeExato(marca.getNome()));
        if (marca1.isPresent())
            Assert.isTrue( marca1.get().getId().equals(marca.getId()), "Nome duplicado. \n Ja existe uma marca com o mesmo nome cadastrada no sistema");
        marcaDao.update(marca);
    }

    public List getAll(String termoBusca)throws Exception{
        List list =  marcaDao.getAll(termoBusca);
      //  Assert.isTrue(list.size() > 0, "Nenhum resultado encontrado.");
        return list;
    }

    public List getAllAtivos()throws  Exception{
        return marcaDao.getAllAtivos();
    }

    public Object getByID(Integer id) throws SQLException {
        Marca marca = (Marca) marcaDao.getByID(id);
      //  Assert.notNull(marca, "Nenhuma marca com esse id foi encontrada");
        return marca;
    }

    public void deleteByID(Integer id) throws Exception {
        marcaDao.deleteByID(id);
    }

}
