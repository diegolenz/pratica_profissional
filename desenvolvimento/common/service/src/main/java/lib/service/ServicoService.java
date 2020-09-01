package lib.service;

import imp.servico.ServicoDao;
import lib.model.servico.Servico;
import org.springframework.util.Assert;

import java.sql.SQLException;
import java.util.List;

public class ServicoService {
    private ServicoDao servicoDao;

    public  ServicoService(){
        servicoDao = new ServicoDao();
    }

    public void save(Servico servico) throws SQLException{
        Assert.notNull(servico, "Servico não pode ser nulo");
        Assert.notNull(servico.getNome(), "Campo Nome é obrigatório");
        Assert.isTrue(servico.getNome().length() > 1, "Campo nome é obrigatório e deve ser preenchido com ao menos 2 caracteres");
        Assert.notNull(servico.getGrupo(), "Selecione um grupo de servicos");
        servicoDao.save(servico);
    }

    public void update(Servico servico)throws SQLException{
        Assert.notNull(servico, "Servico não pode ser nulo");
        Assert.notNull(servico, "Falha Técnica: ID nulo, para um registro ser alterado o objeto deve conter um ID");
        Assert.notNull(servico.getNome(), "Campo Nome é obrigatório");
        Assert.notNull(servico.getValor(),"Campo valor é obrigatório");
        servicoDao.update(servico);
    }

    public List getAll(String termo) throws SQLException{
        List list = servicoDao.getAll(termo);
        Assert.isTrue(list.size() > 0, "Nenhum servico encontrado");
        return list;
    }

    public List getAllAtivos(String busca)throws SQLException{
        return servicoDao.getAllAtivos(busca);
    }

    public Servico getByID(Integer id)throws SQLException {
        Servico servico = (Servico) servicoDao.getByID(id);
      //  Assert.notNull(servico, "Nenhuma servico com esse id foi encontrada");
        return servico;
    }

    public void deleteByID(Integer id)throws SQLException{
        servicoDao.deleteByID(id);
    }


}
