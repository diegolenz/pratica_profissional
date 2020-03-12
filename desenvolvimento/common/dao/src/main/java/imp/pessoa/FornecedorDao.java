package imp.pessoa;

import imp.AbstractDao;
import imp.endereco.cidade.CidadeDao;
import imp.financeiro.condicaoPagamentoDAO.CondicaoPagamentoDAO;
import lib.model.financeiro.CondicaoPagamento.CondicaoPagamento;
import lib.model.pessoa.Sexo;
import lib.model.pessoa.TipoPessoa;
import lib.model.pessoa.fornecedor.Fornecedor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FornecedorDao extends AbstractDao {

    private Fornecedor fornecedor;

    public FornecedorDao() {
        this.fornecedor = new Fornecedor();
    }


    public Integer getUltimoIDFornecedor() throws Exception {
        ResultSet resultSet = this.st.executeQuery("Select id from fornecedor order by ID desc limit 1");
        Integer id = null;
        while (resultSet.next())
            id = resultSet.getInt("id");
        return id;
    }

    public void saveCondicoesPagamento(List<CondicaoPagamento> condicoes, Integer id) throws SQLException {
        for (CondicaoPagamento condicaoPagamento : condicoes) {
            String sql = "INSERT INTO condicao_pagamento_fornecedor ( fornecedor_id, condicao_id) values (" +
                    "" + id + ", " + condicaoPagamento.getId() + " );";
            st.execute(sql);
        }
    }

    private List<CondicaoPagamento> getCondicaoByFornecedor(Integer id) throws SQLException {
        PreparedStatement preparedStatement = st.getConnection().prepareStatement("SELECT * FROM condicao_pagamento_fornecedor WHERE fornecedor_id = " + id + ";");
        ResultSet rs = preparedStatement.executeQuery();
        List<CondicaoPagamento> condicaoPagamentos = new ArrayList<>();
        while (rs.next()) {
            condicaoPagamentos.add(new CondicaoPagamentoDAO().getByID(rs.getInt("condicao_id")));
        }
        return condicaoPagamentos;
    }

    public void deleteCondicoes(List<CondicaoPagamento> condicoes, Integer fornecedorId) throws Exception {
        String sql = "";
        for (CondicaoPagamento condicaoPagamento : condicoes) {
            sql = "DELETE FROM condicao_pagamento_fornecedor WHERE condicao_id = " + condicaoPagamento.getId() + " and fornecedor_id = " + fornecedorId + " ;";
            this.st.execute(sql);
        }
    }

    public void save(Object obj) throws Exception {
        Fornecedor fornecedor = (Fornecedor) obj;
        String date = "";
        if (fornecedor.getDataNascimento() != null)
            date = fornecedor.getDataNascimento().toString();

        // Insere informações da pessoa
        String sql = "INSERT INTO fornecedor (" +
                " nome," +
                " cpf_cnpj,";
        if (fornecedor.getDataNascimento() != null)
            sql += " data_nascimento,";
        sql += " email," +
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
                " cep, " +
                " ativo," +
                " data_cadastro," +
                " data_ultima_alteracao" +
                ") values (" +
                "'" + fornecedor.getNome() +
                "','" + fornecedor.getCpfCnpj() + "',";
        if (fornecedor.getDataNascimento() != null)
            sql += " '" + date + "',";

        sql += " '" + fornecedor.getEmail() +
                "', " + fornecedor.getSexo().ordinal() +
                ", '" + fornecedor.getNomeFantasia_Apelido() +
                "', '" + fornecedor.getRgIe() +
                "', '" + fornecedor.getTelefone() +
                "', '" + fornecedor.getTelefoneAlternativo() +
                "', " + fornecedor.getTipo().ordinal() +
                ", " + fornecedor.getCidade().getId() +
                ", '" + fornecedor.getBairro() +
                "', '" + fornecedor.getLogradouro() +
                "', '" + fornecedor.getComplemento() +
                "', '" + fornecedor.getNumeroResidencial() +
                "', '" + fornecedor.getCep() +
                "', " +
                fornecedor.getAtivo() + ", '" +
                fornecedor.getDataCadastro() + "','" +
                fornecedor.getDataUltAlteracao() + "'" +
                " );";

        this.st.getConnection().prepareStatement(sql).executeUpdate();
        this.saveCondicoesPagamento(fornecedor.getCondicoesPagamentos(), getUltimoIDFornecedor());
    }

    public void deleteByID(Object id) throws SQLException {
        this.st.getConnection().setAutoCommit(false);
        String deleteCondiscoes = "DELETE FROM condicao_pagamento_fornecedor where fornecedor_id =" + fornecedor.getId();
        String sql = "DELETE FROM fornecedor WHERE id = " + id + " ;";

        this.st.execute(deleteCondiscoes);
        this.st.execute(sql);

        this.st.getConnection().commit();
        this.st.getConnection().setAutoCommit(true);
    }

    public List getAllFornecedores(String termoBusca) throws SQLException {
        String sql = "";
        if (termoBusca.length() == 0)
            sql = "SELECT * FROM fornecedor;";
        else if ((termoBusca.matches("[0-9]")))
            sql = "SELECT * FROM fornecedor where  id = " + termoBusca + " ";
        else
            sql = "select * from fornecedor where nome like '%" + termoBusca + "%' );";

        ResultSet rs = this.st.executeQuery(sql);
        List<Fornecedor> fornecedors = new ArrayList<>();

        while (rs.next()) {
            fornecedors.add(getByID(rs.getInt("id")));
        }
        fornecedors.sort(Comparator.comparing(Fornecedor::getNome));
        return fornecedors;
    }

    public void update(Object obj) throws SQLException {
        fornecedor = (Fornecedor) obj;

        String sql = "UPDATE fornecedor SET nome = '" + fornecedor.getNome() +
                "', sexo = " + fornecedor.getSexo().ordinal() +
                ",  nome_fantasia_apelido = '" + fornecedor.getNomeFantasia_Apelido() +
                "', cpf_cnpj = '" + fornecedor.getCpfCnpj() +
                "', rg_ie = '" + fornecedor.getRgIe() +
                "', telefone = '" + fornecedor.getTelefone() +
                "', telefone_alternativo = '" + fornecedor.getTelefoneAlternativo() +
                "', tipo = " + fornecedor.getTipo().ordinal() +
                ",";
        if (fornecedor.getDataNascimento() != null) {
            sql += "  data_nascimento = '" + fornecedor.getDataNascimento() + "', ";
        }
        sql +=
                " email = '" + fornecedor.getEmail() +
                        "', logradouro = '" + fornecedor.getLogradouro() +
                        "', complemento = '" + fornecedor.getComplemento() +
                        "', cep ='" + fornecedor.getCep() +
                        "', cidade_ID = " + fornecedor.getCidade().getId() +
                        ", numero_residencial = '" + fornecedor.getNumeroResidencial() +
                        "', ativo = " + fornecedor.getAtivo() +
                        ", data_ultima_alteracao = '" + fornecedor.getDataUltAlteracao() + "'" +
                        " where id = " + fornecedor.getId() + " ;";

        this.st.executeUpdate(sql);
    }

    public Fornecedor getByID(Integer id) throws SQLException {
        PreparedStatement preparedStatement = st.getConnection().prepareStatement("SELECT * FROM fornecedor WHERE ID = " + id + ";");
        ResultSet rs = preparedStatement.executeQuery();
        Fornecedor fornecedor = null;
        while (rs.next()) {
            fornecedor = new Fornecedor();

            fornecedor.setAtivo(rs.getBoolean("ativo"));
            fornecedor.setDataUltAlteracao(rs.getDate("data_ultima_alteracao"));
            fornecedor.setDataCadastro(rs.getDate("data_cadastro"));
            fornecedor.setCondicoesPagamentos(getCondicaoByFornecedor(fornecedor.getId()));
            fornecedor.setId(rs.getInt("id"));
            fornecedor.setNome(rs.getString("nome"));
            fornecedor.setNomeFantasia_Apelido(rs.getString("nome_fantasia_apelido"));
            fornecedor.setBairro(rs.getString("bairro"));
            fornecedor.setLogradouro(rs.getString("logradouro"));
            fornecedor.setComplemento(rs.getString("complemento"));
            fornecedor.setCidade(new CidadeDao().getByID(rs.getInt("cidade_ID")));
            fornecedor.setNumeroResidencial(rs.getString("numero_residencial"));
            fornecedor.setCep(rs.getString("cep"));
            fornecedor.setTelefone(rs.getString("telefone"));
            fornecedor.setTelefoneAlternativo(rs.getString("telefone_alternativo"));
            fornecedor.setDataNascimento(rs.getDate("data_nascimento"));
            fornecedor.setRgIe(rs.getString("rg_ie"));
            fornecedor.setCpfCnpj(rs.getString("cpf_cnpj"));
            fornecedor.setEmail(rs.getString("email"));
            fornecedor.setCondicoesPagamentos(getCondicaoByFornecedor(fornecedor.getId()));


            Integer sexo = rs.getInt("sexo");
            switch (sexo) {
                case 0:
                    fornecedor.setSexo(Sexo.MASCULINO);
                    break;
                case 1:
                    fornecedor.setSexo(Sexo.FEMININO);
                    break;
                case 2:
                    fornecedor.setSexo(Sexo.OUTROS);
            }

            Integer tipo = rs.getInt("tipo");
            switch (tipo) {
                case 0:
                    fornecedor.setTipo(TipoPessoa.FISICA);
                    break;
                case 1:
                    fornecedor.setTipo(TipoPessoa.JURIDICA);
                    break;
                case 2:
                    fornecedor.setTipo(TipoPessoa.ESTRANGEIRO);
            }
        }
        return fornecedor;
    }

    public Fornecedor getByCpfCnpjExato(String cpf) throws Exception {
        PreparedStatement preparedStatement = st.getConnection().prepareStatement(
                " select * from fornecedor where cpf_cnpj = '" + cpf + "';");
        ResultSet rs = preparedStatement.executeQuery();
        Fornecedor fornecedor = null;
        if (rs.next()) {
            fornecedor = getByID(rs.getInt("id"));
        }
        return fornecedor;
    }
}
