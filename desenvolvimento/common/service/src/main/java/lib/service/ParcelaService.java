package lib.service;

import imp.financeiro.ParcelaDAO;
import lib.model.financeiro.parcela.Parcela;
import org.springframework.util.Assert;

import java.util.List;

public class ParcelaService {

    private ParcelaDAO parcelaDao;

    public  ParcelaService(){
        parcelaDao = new ParcelaDAO();
    }

    public void save(Parcela parcela) throws Exception{
        Assert.notNull(parcela, "Parcela não pode ser nulo");
        Assert.notNull(parcela.getFormaPagamento(), "Campo Forma de pagamento da parcela é obrigatório");
        parcelaDao.save(parcela);
    }

    public void update(Parcela parcela)throws Exception{
        Assert.notNull(parcela, "Parcela não pode ser nulo");
        Assert.notNull(parcela, "Falha Técnica: ID nulo, para um registro ser alterado o objeto deve conter um ID");
        parcelaDao.update(parcela);
    }

    public List getAll() throws Exception{
        List list = parcelaDao.getAll();
        Assert.isTrue(list.size() > 0, "Nenhum parcela encontrado");
        return list;
    }

    public List getAllBy() throws Exception{
        List list = parcelaDao.getAll();
        Assert.isTrue(list.size() > 0, "Nenhum parcela encontrado");
        return list;
    }


    public List getAllAtivos(String busca)throws Exception{
        return parcelaDao.getAllAtivos(busca);
    }

    public Object getByID(Integer id)throws Exception{
        Parcela parcela = (Parcela) parcelaDao.getByID(id);
        Assert.notNull(parcela, "Nenhuma parcela com esse id foi encontrada");
        return parcela;
    }

    public void deleteByID(Integer id)throws Exception{
        parcelaDao.deleteByID(id);
    }
}
