package lib.service;

import imp.sistema.FuncionarioDao;
import lib.model.interno.Funcionario;
import lib.model.interno.GrupoFuncionario;
import org.springframework.util.Assert;

import java.sql.SQLException;
import java.util.List;

public class FuncionarioService extends Service {

    /*-------------------------------------------------------------------
     *				 		     ATTRIBUTES
     *-------------------------------------------------------------------*/
    /**
     * Password encoder
     */

    private FuncionarioDao operadorDao;


    /*-------------------------------------------------------------------
     *				 		     CONSTRUCTOR
     *-------------------------------------------------------------------*/

    public FuncionarioService() {
        this.operadorDao = new FuncionarioDao();
    }

    /*-------------------------------------------------------------------
     *				 		     SERVICES
     *-------------------------------------------------------------------*/

    public void save(Object object) throws SQLException {
        Funcionario operador = (Funcionario) object;
        Assert.notNull(operador, "Operador não pode estar nula");
        Assert.notNull(operador.getNome(), "Nome é obrigatório");

        operadorDao.save(operador);
    }

    public void clear() {
        try {
            operadorDao.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void update(Funcionario operador) throws SQLException {
        Assert.notNull(operador, "Operador não pode estar nula");
        Assert.notNull(operador.getNome(), "Nome é obrigatório");

        operadorDao.update(operador);
    }

    public List getAll(String termos) throws SQLException {
        return operadorDao.getAll(termos);
    }

    public List getAllOperadoresAtivos(String termo) throws SQLException {
        return operadorDao.getAllAtivos(termo);
    }


    public Funcionario getByID(Integer id) throws SQLException {
        Assert.notNull(id, "ID passado como parametro não pode estar nulo");
        Funcionario operador = operadorDao.getByID(id);
        return operador;
    }

    public void deleteByID(Funcionario operador) throws SQLException {
        operadorDao.deleteByID(operador.getId());
        // enderecoService.deleteByID(operador.getEndereco().getId());
    }


    public Funcionario getByCpfCnpjExato(String cpf) throws SQLException {
        return operadorDao.getByCpfCnpjExato(cpf);
    }

    public void deleteGrupos(List<GrupoFuncionario> condicoes, Integer id) throws SQLException {
        operadorDao.deleteGrupos(condicoes, id);
    }

    public void salvarGrupos(List<GrupoFuncionario> condicoes, Integer id) throws SQLException {
        operadorDao.saveGrupo(condicoes, id);
    }

    public void delete(Integer id) throws SQLException {
        operadorDao.deleteByID(id);
    }

    public Funcionario getByLogin(String user, String password) throws SQLException {
        return operadorDao.getByLogin(user, password);
    }
}



