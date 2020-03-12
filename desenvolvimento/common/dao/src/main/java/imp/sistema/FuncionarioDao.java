package imp.sistema;

import imp.AbstractDao;
import imp.endereco.cidade.CidadeDao;
import lib.model.interno.Funcionario;
import lib.model.interno.GrupoFuncionario;
import lib.model.pessoa.Sexo;
import lib.model.pessoa.TipoPessoa;;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDao extends AbstractDao {

    private Funcionario operador;

    public FuncionarioDao() {
        this.operador = new Funcionario();
    }

    public void save(Object obj) throws SQLException {
        Funcionario funcionario = (Funcionario) obj;

        String dtNascimento = "";
        if (funcionario.getDataNascimento() != null)
            dtNascimento = funcionario.getDataNascimento().toString();

        String dtdemissão = "";
        if (funcionario.getDataNascimento() != null)
            dtdemissão = funcionario.getDataDemissao().toString();


        String sql = "INSERT INTO funcionario (" +
                " nome," +
                " cpf_cnpj," +
                " data_nascimento," +
                " email," +
                " sexo," +
                " nome_fantasia_apelido," +
                " rg_ie," +
                " telefone," +
                " telefone_alternativo," +
                " tipo," +
                " cidade_id," +
                " bairro," +
                " logradouro," +
                " complemento," +
                " numero_residencial, " +
                " cep ," +
                " ativo," +
                " data_cadastro," +
                " data_ultima_alteracao," +
                " data_admissao, " +
                " data_demissao, " +
                " primeiro_login, " +
                " usuario, " +
                " senha " +
                ") values (" +
                "'" + funcionario.getNome() +
                "','" + funcionario.getCpfCnpj() + "'," ;
        sql += funcionario.getDataNascimento() != null ? "'" + funcionario.getDataNascimento() + "'," :  "null,";
        sql +=  "'" + funcionario.getEmail() +
                "', " + funcionario.getSexo().ordinal() +
                ", '" + funcionario.getNomeFantasia_Apelido() +
                "', '" + funcionario.getRgIe() +
                "', '" + funcionario.getTelefone() +
                "', '" + funcionario.getTelefoneAlternativo() +
                "', " + funcionario.getTipo().ordinal() +
                ", " + funcionario.getCidade().getId() +
                ", '" + funcionario.getBairro() +
                "', '" + funcionario.getLogradouro() +
                "', '" + funcionario.getComplemento() +
                "', '" + funcionario.getNumeroResidencial() +
                "', '" + funcionario.getCep() +
                "', " + funcionario.isAtivo() +
                ", '" + funcionario.getDataCadastro() +
                "','" + funcionario.getDataUltAlteracao() +
                "', '" + funcionario.getDataAdmissao() ;
        sql += funcionario.getDataDemissao() != null ? "', '" + funcionario.getDataDemissao() + "'," : "', null,";
        sql +=  funcionario.getPrimeiroLogin() +
                ", '" + funcionario.getUsuario() +
                "', '" + funcionario.getSenha() +
                "' );";

        this.st.executeUpdate(sql);
        this.saveGrupo(funcionario.getGrupos(), getUltimoIDFuncionario());
    }

    public Integer getUltimoIDFuncionario() throws SQLException {
        ResultSet resultSet = this.st.executeQuery("Select id from funcionario order by ID desc limit 1");
        Integer id = null;
        while (resultSet.next())
            id = resultSet.getInt("id");
        return id;
    }

    public void deleteByID(Object id) throws SQLException {
        String sql = "DELETE FROM funcionario WHERE id = " + id + " ;";
        this.st.executeUpdate(sql);
    }

    public void deleteGrupos(List<GrupoFuncionario> condicoes, Integer fornecedorId) throws SQLException {
        String sql = "";
        for (GrupoFuncionario condicaoPagamento : condicoes) {
            sql = "DELETE FROM grupo_funcionario WHERE grupo_id = " + condicaoPagamento.getId() + " and funcionario_id = " + fornecedorId + " ;";
            this.st.execute(sql);
        }
    }

    public void saveGrupo(List<GrupoFuncionario> condicoes, Integer id) throws SQLException {
        for (GrupoFuncionario condicaoPagamento : condicoes) {
            String sql = "INSERT INTO relacao_grupo_funcionario ( funcionario_id, grupo_id) values (" +
                    "" + id + ", " + condicaoPagamento.getId() + " );";
            st.execute(sql);
        }
    }


    public List getAll(String termos) throws SQLException {

        ResultSet rs = this.st.executeQuery("SELECT * FROM funcionario;");
        List<Funcionario> pessoas = new ArrayList<>();
        while (rs.next()) {
            pessoas.add(getByID(rs.getInt("id")));
        }
        return pessoas;
    }

    public void update(Object obj) throws SQLException {
        Funcionario funcionario = (Funcionario) obj;
        String sql = "UPDATE funcionario SET nome = '" + funcionario.getNome() +
                "', sexo = " + funcionario.getSexo().ordinal() +
                ",  nome_fantasia_apelido = '" + funcionario.getNomeFantasia_Apelido() +
                "', cpf_cnpj = '" + funcionario.getCpfCnpj() +
                "', rg_ie = '" + funcionario.getRgIe() +
                "', telefone = '" + funcionario.getTelefone() +
                "', telefone_alternativo = '" + funcionario.getTelefoneAlternativo() +
                "', tipo = " + funcionario.getTipo().ordinal() +
                ",";
        if (funcionario.getDataNascimento() != null) {
            sql += "  data_nascimento = '" + funcionario.getDataNascimento() + "', ";
        }
        sql +=
                " email = '" + funcionario.getEmail() +
                        "', logradouro = '" + funcionario.getLogradouro() +
                        "', complemento = '" + funcionario.getComplemento() +
                        "', cep ='" + funcionario.getCep() +
                        "', cidade_ID = " + funcionario.getCidade().getId() +
                        ", numero_residencial = '" + funcionario.getNumeroResidencial() +
                        "'    WHERE id = " + funcionario.getId() + " ;";
        sql = " ativo = " + funcionario.isAtivo() +
                ", usuario='" + funcionario.getUsuario() +
                "', senha='" + funcionario.getSenha() +
                "', data_admissao ='" + funcionario.getDataAdmissao() +
                "',  data_cadastro = '" + funcionario.getDataCadastro();
        if (funcionario.getDataDemissao() != null) {
            sql += "', data_demissao = ' " + funcionario.getDataDemissao();
        }
        sql += "', data_ultima_alteracao = '" + funcionario.getDataUltAlteracao() + "' where id = " + funcionario.getId() + " ;";
        this.st.executeUpdate(sql);
    }

    public List<Funcionario> getAllAtivos(String termo) throws SQLException {

        String sql = "";
        if (termo.length() == 0)
            sql = "SELECT * FROM funcionario where ativo = true ;";
        else if ((termo.matches("[0-9]")))
            sql = "SELECT * FROM funcionario where  id = " + termo + " and ativo = true ;";
        else
            sql = "SELECT * FROM funcionario where  nome LIKE '%" + termo + "%' and ativo = true ;";

        ResultSet rs = this.st.executeQuery(sql);
        List<Funcionario> pessoas = new ArrayList<>();
        while (rs.next()) {

        }
        return pessoas;
    }

    public Funcionario getByLogin(String user, String passwors)throws SQLException{
        ResultSet rs = st.getConnection().prepareStatement("select * from funcionario where usuario = '"+user +"' and senha ='" + passwors + "' ;").executeQuery();
        if (rs.next()){
            Funcionario funcionario = new Funcionario();
            funcionario = getByID(rs.getInt("id"));
            return funcionario;
        }
        return null;
    }

    public Funcionario getByID(Integer id) throws SQLException {
        PreparedStatement preparedStatement = st.getConnection().prepareStatement("SELECT * FROM funcionario WHERE ID = " + id + ";");
        ResultSet rs = preparedStatement.executeQuery();
        Funcionario funcionario = null;
        while (rs.next()) {
            funcionario = new Funcionario();

            funcionario.setAtivo(rs.getBoolean("ativo"));
            funcionario.setDataUltAlteracao(rs.getDate("data_ultima_alteracao"));
            funcionario.setDataCadastro(rs.getDate("data_cadastro"));
            funcionario.setUsuario(rs.getString("usuario"));
            funcionario.setSenha(rs.getString("senha"));
            funcionario.setDataDemissao(rs.getDate("data_demissao"));
            funcionario.setDataAdmissao(rs.getDate("data_admissao"));
            funcionario.setNome(rs.getString("nome"));
            funcionario.setNomeFantasia_Apelido(rs.getString("nome_fantasia_apelido"));
            funcionario.setBairro(rs.getString("bairro"));
            funcionario.setLogradouro(rs.getString("logradouro"));
            funcionario.setComplemento(rs.getString("complemento"));
            funcionario.setCidade(new CidadeDao().getByID(rs.getInt("cidade_ID")));
            funcionario.setNumeroResidencial(rs.getString("numero_residencial"));
            funcionario.setCep(rs.getString("cep"));
            funcionario.setTelefone(rs.getString("telefone"));
            funcionario.setTelefoneAlternativo(rs.getString("telefone_alternativo"));
            funcionario.setDataNascimento(rs.getDate("data_nascimento"));
            funcionario.setRgIe(rs.getString("rg_ie"));
            funcionario.setCpfCnpj(rs.getString("cpf_cnpj"));
            funcionario.setEmail(rs.getString("email"));

            Integer sexo = rs.getInt("sexo");
            switch (sexo) {
                case 0:
                    funcionario.setSexo(Sexo.MASCULINO);
                    break;
                case 1:
                    funcionario.setSexo(Sexo.FEMININO);
                    break;
                case 2:
                    funcionario.setSexo(Sexo.OUTROS);
            }

            Integer tipo = rs.getInt("tipo");
            switch (tipo) {
                case 0:
                    funcionario.setTipo(TipoPessoa.FISICA);
                    break;
                case 1:
                    funcionario.setTipo(TipoPessoa.JURIDICA);
                    break;
                case 2:
                    funcionario.setTipo(TipoPessoa.ESTRANGEIRO);
            }

            funcionario.setId(rs.getInt("id"));
            funcionario.setGrupos(getGrupos(operador.getId()));
        }
        return funcionario;
    }

    private List<GrupoFuncionario> getGrupos(Integer id) throws SQLException {
        PreparedStatement preparedStatement = st.getConnection().prepareStatement("SELECT * FROM relacao_grupo_funcionario WHERE funcionario_id = " + id + ";");
        ResultSet rs = preparedStatement.executeQuery();
        List<GrupoFuncionario> condicaoPagamentos = new ArrayList<>();
        while (rs.next()) {
            condicaoPagamentos.add(new GrupoOperadorDao().getByID(rs.getInt("grupo_id")));
        }
        return condicaoPagamentos;
    }

    public Funcionario getByCpfCnpjExato(String cpf) throws SQLException {
        PreparedStatement preparedStatement = st.getConnection().prepareStatement("SELECT * FROM funcionario WHERE cpfcnpj = '" + cpf + "' ;");
        ResultSet rs = preparedStatement.executeQuery();
        Funcionario operador = null;
        if (rs.next()) {

        }
        return operador;

    }


}

