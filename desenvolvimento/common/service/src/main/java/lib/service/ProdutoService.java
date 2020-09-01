package lib.service;


import imp.produto.ProdutoDao;
import lib.model.produto.Produto;
import org.springframework.util.Assert;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ProdutoService {

    private ProdutoDao produtoDao;

    public  ProdutoService(){
        produtoDao = new ProdutoDao();
    }

    public Produto getLast()throws Exception{
        return produtoDao.getLast();
    }


    public void save(Produto produto) throws Exception{
        Assert.notNull(produto, "Produto não pode ser nulo");
        Assert.notNull(produto.getNome(), "Campo Nome é obrigatório");
        Assert.isTrue(produto.getNome().length() > 1, "Campo nome é obrigatório e deve ser preenchido com ao menos 2 caracteres");
        Assert.notNull(produto.getMarca(), "Selecione uma marca");
        Assert.notNull(produto.getGrupo(), "Selecione um grupo de produtos");
        Assert.notNull(produto.getUnidadeMedida(), "Unidade de medida é um campo obrigatório");
        Assert.isTrue(produto.getUnidadeMedida().length() > 1, "Campo Unidade de medida é um campo obrigatório e deve ser preenchido " +
                "com mais de 1 caracter");
        Assert.isTrue(!produto.getUnidadeMedida().trim().isEmpty(), "Campo Unidade de medida é um campo obrigatório");
        produto.setDataUltimaAlteracao(new Date());
        produto.setDataCadastro(new Date());
        produtoDao.save(produto);
    }

    public void update(Produto produto)throws SQLException {
        Assert.notNull(produto, "Produto não pode ser nulo");
        Assert.notNull(produto, "Falha Técnica: ID nulo, para um registro ser alterado o objeto deve conter um ID");
        Assert.notNull(produto.getNome(), "Campo Nome é obrigatório");
        Assert.notNull(produto.getValor(),"Campo valor é obrigatório");
        Assert.notNull(produto.getUnidadeMedida(), "Campo Unidade de medida é obrigatório");
        Assert.isTrue(!produto.getUnidadeMedida().trim().isEmpty(), "Campo Unidade de Medida invalido");
        Assert.isTrue(produto.getUnidadeMedida().length() > 1, "Campo Unidade de medida invalido, minimo dois caracteres");
        produto.setDataUltimaAlteracao(new Date());
        produtoDao.update(produto);
    }

    public List getAll(String termo) throws SQLException{
        List list = produtoDao.getAll(termo);
        return list;
    }

    public List getAllAtivos(String busca)throws SQLException{
        return produtoDao.getAllAtivos(busca);
    }

    public Object getByID(Integer id)throws SQLException{
        Produto produto = (Produto) produtoDao.getByID(id);
        Assert.notNull(produto, "Nenhum produto foi encontrada");
        return produto;
    }

    public void deleteByID(Integer id)throws Exception{
        produtoDao.deleteByID(id);
    }


}
