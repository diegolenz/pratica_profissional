package lib.service;

import imp.endereco.cidade.CidadeDao;
import lib.model.endereco.cidade.Cidade;
import org.springframework.util.Assert;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CidadeService {

    CidadeDao cidadeDao;

    public void save(Cidade cidade) throws Exception{
        Assert.notNull(cidade, "cidade não pode estar nulo");
        Assert.notNull(cidade.getNome(), "Campo Nome precisa ser preenchido");
        Assert.notNull(cidade.getEstado(), "Estado não pode estar nulo");
        Optional<Cidade> paisOptional = Optional.ofNullable(cidadeDao.getByNomeAndEstadoExato(cidade));
        Assert.isTrue(!paisOptional.isPresent(), "Ja existe um a cidade com esse mesmo nome no mesmo estado\n, o cadastro de estado não não deve conter nomes duplicados em um mesmo país ");
        cidadeDao.save(cidade);
    }

    public void update(Cidade cidade) throws Exception{
        Assert.notNull(cidade, "cidade não pode estar nulo");
        Assert.notNull(cidade.getId(), "Código do cidade não pode estar nulo");
        Assert.notNull(cidade.getNome(), "Campo Nome precisa ser preenchido");
        Assert.notNull(cidade.getEstado(), "Estado não pode estar nulo");
        Optional<Cidade> paisOptional = Optional.ofNullable(cidadeDao.getByNomeAndEstadoExato(cidade));

        if (paisOptional.isPresent() && !paisOptional.get().getId().equals(cidade.getId())) {
            throw new Exception("Ja existe uma cidade com esse mesmo nome no mesmo país \n, o cadastro de cidade não deve conter nomes duplicados em um mesmo estado ");
        }
        cidadeDao.update(cidade);
    }

    public List<Cidade> getAll(String termo) throws Exception {
        return cidadeDao.getAll( termo);
    }

    public List getAllAtivos(String termo) throws Exception {
        return cidadeDao.getAllAtivos(termo);
    }


    public Cidade getCidadeByID(Integer id) throws SQLException {
        Assert.notNull(id, "ID passado como parametro não pode estar nulo");
        Cidade cidade = cidadeDao.getByID(id);
        Assert.notNull(cidade.getId(), "Nenhuma cidade foi encontrada com esse código");
        return cidade;
    }

    public void deleteByID(Integer id) throws Exception {
        cidadeDao.deleteByID(id);
    }

    public List getByEstado(Integer idEstado) throws Exception {
        Assert.notNull(idEstado, "ID passado como parametro não pode estar nulo");
        return cidadeDao.getByEstado(idEstado);
    }

    public List getByNomeEstado(String nome) throws Exception {
        return cidadeDao.getByNomeEstado(nome);
    }

  public Cidade getLast()throws Exception {
      Optional<Cidade> cidade = cidadeDao.getLast();
      if (cidade.isPresent())
          return cidade.get();
      else return null;
  }

    public CidadeService() {
        this.cidadeDao = new CidadeDao();
    }
}
