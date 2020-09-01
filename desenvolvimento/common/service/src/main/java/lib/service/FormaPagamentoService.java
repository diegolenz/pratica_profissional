package lib.service;


import imp.financeiro.formaPagamentoDAO.FormaPagamentoDAO;
import lib.model.endereco.estado.Estado;
import lib.model.financeiro.formaPagamento.FormaPagamento;
import org.springframework.util.Assert;

import java.sql.SQLException;
import java.util.List;

public class FormaPagamentoService {

    FormaPagamentoDAO formaPagamentoDAO;

    public void save(FormaPagamento formaPagamento) throws Exception{
        Assert.notNull(formaPagamento, "estado não pode estar nulo");
        Assert.notNull(formaPagamento.getNome(), "Campo Nome precisa ser preenchido");
        Assert.isTrue(formaPagamento.getNome().length() > 1, "Campo nome precisa ao menos 2 caracteres sem contabilizar espaços");
        Assert.notNull(formaPagamento.getNome(), "Selecione um país");
        formaPagamentoDAO.save(formaPagamento);
    }

    public void update(FormaPagamento formaPagamento) throws Exception{
        Assert.notNull(formaPagamento, "estado não pode estar nulo");
        Assert.notNull(formaPagamento.getId(), "Código do estado não pode estar nulo");
        Assert.notNull(formaPagamento.getNome(), "Campo Nome precisa ser preenchido");
        formaPagamentoDAO.update(formaPagamento);
    }

    public List getAll(String termo) throws Exception {
        return formaPagamentoDAO.getAll( termo);
    }

    public List getAllAtivos(String termo) throws Exception {
        List list = formaPagamentoDAO.getAllAtivos(termo);
     //   Assert.isTrue(list.size() > 0, "Nenhum resultado encontrado");
        return list;
    }


    public FormaPagamento getByID(Integer id) throws SQLException {
        Assert.notNull(id, "ID passado como parametro não pode estar nulo");
        FormaPagamento formaPagamento = formaPagamentoDAO.getByID(id);
       // Assert.notNull(formaPagamento, "Não foi encontrado nenhum estado com esse código");
        return  formaPagamento;
    }

    public void deleteByID(Integer id) throws Exception {
        formaPagamentoDAO.deleteByID(id);
    }



    public void mudarStatus(Estado estado){


    }

    public FormaPagamentoService() {
        this.formaPagamentoDAO = new FormaPagamentoDAO();
    }
}
