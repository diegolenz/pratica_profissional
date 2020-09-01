package lib.service;

import imp.endereco.pais.PaisDao;
import lib.model.endereco.pais.Pais;
import org.springframework.util.Assert;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PaisService {

    private PaisDao paisDao;

    public void save(Pais pais) throws Exception{
        Assert.notNull(pais, "pais não pode estar nulo");
        Assert.notNull(pais.getNome(), "Campo Nome precisa ser preenchido");
        Assert.notNull(pais.getDdi(), "Campo DDI é obrigatório");
        Optional<Pais> paisOptional = Optional.ofNullable(paisDao.getPaisByNomeExato(pais.getNome()));
        Assert.isTrue(!paisOptional.isPresent(), "Ja existe um país com esse mesmo nome, o cadastro de país não não deve conter nomes duplicados ");
        paisDao.save(pais);
    }

    public void update(Pais pais) throws Exception{
        Assert.notNull(pais, "pais não pode estar nulo");
        Assert.notNull(pais.getId(), "Código do pais não pode estar nulo");
        Assert.notNull(pais.getNome(), "Campo Nome precisa ser preenchido");
        Assert.notNull(pais.getDdi(), "Campo DDI é obrigatório");
        Optional<Pais> paisOptional = Optional.ofNullable(paisDao.getPaisByNomeExato(pais.getNome()));
        if (paisOptional.isPresent())
        Assert.isTrue( paisOptional.get().getId() == pais.getId(), "Ja existe um país com esse mesmo nome, o cadastro de país não não deve conter nomes duplicados ") ;
        paisDao.update(pais);
    }

    public Pais getLast(Pais estado)throws Exception{
        return paisDao.getLast().get();

    }

    public List<Pais> getAll(String termos) throws Exception {
        return paisDao.getAll(termos);
    }

    public List getAllAtivos(String termo) throws Exception {
        List list = paisDao.getAllAtivos(termo);
      //  Assert.isTrue(list.size() > 0, "Nenhum resultado encontrado");
        return list;
    }

    public void deleteByID(Integer id) throws Exception {
        paisDao.deleteByID(id);
    }

    public void mudarStatus(Pais pais){ }

    public Pais getPaisByID(Integer id) throws SQLException {
        Assert.notNull(id, "ID passado como parametro não pode estar nulo");
        return paisDao.getByID(id);
    }

    public PaisService() {
        this.paisDao = new PaisDao();
    }

}


