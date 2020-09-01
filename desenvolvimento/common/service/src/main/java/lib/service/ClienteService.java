package lib.service;


import imp.pessoa.ClienteDAO;
import lib.model.financeiro.CondicaoPagamento.CondicaoPagamento;
import lib.model.pessoa.Pessoa;
import lib.model.pessoa.TipoPessoa;
import lib.model.pessoa.cliente.Cliente;
import org.springframework.util.Assert;
import util.CpfCnpjValidator;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ClienteService extends Service{
    /*-------------------------------------------------------------------
     *				 		     ATTRIBUTES
     *-------------------------------------------------------------------*/
    /**
     * Password encoder
     */

    private ClienteDAO clienteDAO;

    /*-------------------------------------------------------------------
     *				 		     CONSTRUCTOR
     *-------------------------------------------------------------------*/

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }

    /*-------------------------------------------------------------------
     *				 		     SERVICES
     *-------------------------------------------------------------------*/

    public void save(Object object) throws SQLException {
        Pessoa pessoa = (Pessoa) object;
        Assert.notNull(pessoa, "Pessoa não pode estar nula");
        Assert.notNull(pessoa.getNome(), "Campo nome é obrigatório");
        Assert.isTrue(!pessoa.getNome().isEmpty(), "Campo nome é obrigatório");
        if (pessoa.getDataNascimento() != null)
            Assert.isTrue(pessoa.getDataNascimento().compareTo(new Date()) < 0,
                    pessoa.getTipo().equals(TipoPessoa.JURIDICA) ? "Data de fundação deve ser menor que a data de hoje" : "Data de nascimento deve ser menor que a data de hoje");


        Assert.notNull(pessoa.getCidade(), "Cidade é obrigatória");
        //Assert.notNull(pessoa.getBairro(), "Bairro é obrigatório");
        Assert.notNull(pessoa.getNumeroResidencial(), "Campo numero é obrigatório");
        Assert.notNull(pessoa.getLogradouro(), "Campo logradouro é obrigatório");
        Assert.isTrue(pessoa.getLogradouro().length() > 3, "Campo logradouro é obrigatório e deve conter mais que 3 caracteres");
        //Assert.isTrue(pessoa.getBairro().length() > 4, "Campo bairro é obrigatório e deve conter mais que 3 caracteres");

        if (!pessoa.getTipo().equals(TipoPessoa.ESTRANGEIRO)) {
            Assert.notNull(pessoa.getCep(), "CEP é obrigatório");
            Assert.notNull(pessoa.getCpfCnpj(), "CPF/CNPJ é obrigatório");
            if (pessoa.getTipo().isPessoaFisica())
                Assert.isTrue(CpfCnpjValidator.isCpfValido(pessoa.getCpfCnpj()), "CPF não é valido");
            else
                Assert.isTrue(CpfCnpjValidator.isCnpjValido(pessoa.getCpfCnpj()), "Cnpj não é valido");
            //Assert.notNull(pessoa.getDataNascimento(),"Data de nascimento/criação é obrigatório");

        }

        Assert.isTrue(pessoa.getTelefone() != null && pessoa.getEmail() != null && pessoa.getTelefoneAlternativo() != null, "Ao menos um contato deve ser preenchido");

        Assert.notNull(pessoa.getNumeroResidencial(), "Numero é obrigatório");
        Pessoa pessoaRetorno = this.getByCpfCnpjExato(pessoa.getCpfCnpj());
        if (pessoa.getId() != null) {
            if (pessoaRetorno != null)
                if (pessoa.getId() != pessoaRetorno.getId())
                    throw new SQLException("CPF ja cadastrado");
        } else if (pessoaRetorno != null) {
            throw new SQLException("CPF ja cadastrado");
        }


        clienteDAO.save(pessoa);
    }

    public List<Cliente> getFornecedores(String termo) throws SQLException {
        clienteDAO = new ClienteDAO();
        return clienteDAO.getAllClientes(termo);
    }

    public void salvarCondicoes(List<CondicaoPagamento> condicoes, Integer id) throws SQLException{
        clienteDAO.saveCondicoesPagamento(condicoes, id);
    }

    public void deleteCondicoes(List<CondicaoPagamento> condicoes, Integer id) throws SQLException{
        clienteDAO.deleteCondicoes(condicoes, id);
    }

    public void update(Pessoa pessoa) throws SQLException {
        Assert.notNull(pessoa, "Pessoa não pode estar nula");
        Assert.notNull(pessoa.getNome(), "Nome é obrigatório");
       // Assert.isTrue(pessoa.getTelefone().length() > 7, "Telefone de contato deve ter ao minimo 8 numeros");
        if (pessoa.getDataNascimento() != null)
            Assert.isTrue(pessoa.getDataNascimento().compareTo(new Date()) < 0, "Data de nascimento/fundação deve ser menor que a data de hoje");

//        if (!pessoa.getTipo().equals(TipoPessoa.JURIDICA) && (pessoa.getRgIe() != null || !pessoa.getRgIe().isEmpty()))
//            Assert.isTrue(pessoa.getRgIe().length() > 6, "Rg é um campo obrigatório e deve conter ao minimo 7 caracteres");


        Assert.notNull(pessoa.getCidade(), "Cidade é obrigatória");
        Assert.notNull(pessoa.getNumeroResidencial(), "Numero é obrigatório");
        if (!pessoa.getTipo().equals(TipoPessoa.ESTRANGEIRO)) {
            Assert.notNull(pessoa.getCep(), "CEP é obrigatório");
            Assert.notNull(pessoa.getCpfCnpj(), "CPF/CNPJ é obrigatório");
            Assert.isTrue(CpfCnpjValidator.isCpfValido(pessoa.getCpfCnpj()), "CPF não é valido");
        }
        Cliente pessoaRetorno = this.getByCpfCnpjExato(pessoa.getCpfCnpj());
        if (!pessoa.getTipo().equals(TipoPessoa.ESTRANGEIRO)) {
            Assert.notNull(pessoa.getCep(), "CEP é obrigatório");
            Assert.notNull(pessoa.getCpfCnpj(), "CPF/CNPJ é obrigatório");
            if (pessoa.getTipo().isPessoaFisica())
                Assert.isTrue(CpfCnpjValidator.isCpfValido(pessoa.getCpfCnpj()), "CPF não é valido");
            else
                Assert.isTrue(CpfCnpjValidator.isCnpjValido(pessoa.getCpfCnpj()), "Cnpj não é valido");
            //Assert.notNull(pessoa.getDataNascimento(),"Data de nascimento/criação é obrigatório");

        }

        clienteDAO.update(pessoa);
    }

    public List getAll(String termo) throws SQLException {
        return clienteDAO.getAllClientes(termo);
    }

    public List getAllClientesAtivos(String termo) throws SQLException {
        return clienteDAO.getAllClientes(termo);
    }

    public List getAllClientes(String termo) throws SQLException {
        return clienteDAO.getAllClientes(termo);
    }


    public Cliente getByID(Integer id) throws SQLException {
        Assert.notNull(id, "ID passado como parametro não pode estar nulo");
        Cliente pessoa = clienteDAO.getByID(id);
        return pessoa;
    }

    public void deleteByID(Pessoa pessoa) throws SQLException {
        clienteDAO.deleteByID(pessoa.getId());
    }


    public Cliente getByCpfCnpjExato(String cpf) throws SQLException {
        return clienteDAO.getByCpfCnpjExato(cpf);
    }


}