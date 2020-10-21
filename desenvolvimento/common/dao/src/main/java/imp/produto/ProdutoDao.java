package imp.produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import imp.AbstractDao;
import imp.grupo.GrupoDao;
import imp.marca.MarcaDao;
import imp.pessoa.FornecedorDao;
import imp.sistema.FuncionarioDao;
import lib.model.grupo.Grupo;
import lib.model.interno.Funcionario;
import lib.model.marca.Marca;
import lib.model.produto.Produto;

public class ProdutoDao<T> extends AbstractDao<T> {

    public Produto getLast() throws Exception {
        String sql = "Select id from produto order by ID desc limit 1;";
        ResultSet resultSet = this.st.executeQuery(sql);
        Integer id = null;
        if (resultSet.next()) {
            id = resultSet.getInt("id");
        }
        Optional<Object> cidade = Optional.ofNullable(this.getByID(id));
        return (Produto) cidade.get();
    }


    public void save(Produto produto) throws Exception {
        if (produto.getReferencia() == null)
            produto.setReferencia("");
        String sql = "INSERT INTO produto (nome, ativo, data_ultima_alteracao, unidade_medida, funcionario_cadastro, funcionario_ultima_alteracao, data_cadastro, quantidade_estoque, quantidade_minima, marca_id, grupo_id, referencia, codigo_barras, valor) values ('" + produto.getNome() + "', " + produto.getAtivo() +
                ", '" + produto.getDataUltimaAlteracao() + "', '" + produto.getUnidadeMedida() + "', " + produto.getFuncionarioCadastro().getId() + ", " + produto.getFuncionarioUltimaAtualizacao().getId() +
                ", '" + produto.getDataCadastro() + "', " + produto.getQuantidadeEstoque() + ", " + produto.getQuantidadeMinima() + ", " + produto.getMarca().getId() + ", " + produto.getGrupo().getId() +
                ", '" + produto.getReferencia() + "', '" + produto.getCodigoBarras() + "', " + produto.getValor() + " ) ; ";
        this.st.executeUpdate(sql);
    }

    public void update(Produto produto) throws SQLException {
        if (produto.getReferencia() == null)
            produto.setReferencia("");

        String sql = "update " +
                "produto set nome = '" + produto.getNome() + "', ativo = " + produto.getAtivo() + ", data_ultima_alteracao = '" + produto.getDataUltimaAlteracao() + "', unidade_medida = '" + produto.getUnidadeMedida() +
                "', data_cadastro = '" + produto.getDataCadastro() + "', quantidade_estoque = " + produto.getQuantidadeEstoque() + ", quantidade_minima = " + produto.getQuantidadeMinima() + ", marca_id = " + produto.getMarca().getId() + "" +
                ", grupo_id = " + produto.getGrupo().getId() + ", referencia = '" + produto.getReferencia() + "'" +
                ", codigo_barras = '" + produto.getCodigoBarras() + "', valor = " + produto.getValor() + ", preco_compra = " + produto.getPrecoCompra() + "";
        if (produto.getUltimoFornecedor() != null)
            sql += ", data_ultima_compra = '" + produto.getDataUltimaCompra() + "', ultimo_fornecedor_id = " + produto.getUltimoFornecedor().getId() + "";
        //if (produto.getDataUltimaVenda() != null)
        //  sql += ", data_ultima_venda = '" + produto.getDataUltimaVenda() + "' ";
        sql += ", funcionario_ultima_alteracao = " + produto.getFuncionarioUltimaAtualizacao().getId() + " where id = " + produto.getId() + " ;";
        this.st.executeUpdate(sql);
    }

    public List getAll(String termo) throws SQLException {
        String sql = "";
        if (termo.length() == 0)
            sql = "SELECT * FROM produto ORDER BY nome;";
        else if ((termo.matches("[0-9]")))
            sql = "Select * from produto where id = " + termo + " ORDER BY nome;";
        else
            sql = "SELECT * FROM produto WHERE nome =" + termo + " ORDER BY nome;";

        ResultSet rs = this.st.executeQuery(sql);
        List produtos = new ArrayList();
        while (rs.next()) {
            Produto produto = (Produto) this.getByID(rs.getInt("id"));
            if (produto != null)
                produtos.add(produto);
        }
        return produtos;
    }

    public List findByFilters(String termo, Boolean ativo) throws SQLException {
        String sql = "SELECT * FROM produto ";
        if ((termo != null && !termo.isEmpty()) || ativo != null) {
            sql += " WHERE ";
        }
        Boolean addAnd = false;
        if (termo != null && !termo.isEmpty()) {
            sql += " upper(nome) like upper('%" + termo + "%') ";
            addAnd = true;
        }
        if (ativo != null) {
            if (addAnd)
                sql += " and ";
            sql += " ativo = " + ativo;
        }
        sql += " ORDER BY nome;";

        ResultSet rs = this.st.executeQuery(sql);
        List produtos = new ArrayList();
        while (rs.next()) {
            Produto produto = (Produto) this.getByID(rs.getInt("id"));
            if (produto != null)
                produtos.add(produto);
        }
        return produtos;
    }

    public List getAllAtivos(String termo) throws SQLException {
        String sql = "";
        if (termo.length() == 0)
            sql = "SELECT * FROM produto where ativo = true ORDER BY nome;";
        else if ((termo.matches("[0-9]")))
            sql = "Select * from produto where id = " + termo + " and ativo = true ORDER BY nome;";
        else
            sql = "SELECT * FROM produto WHERE nome like '%" + termo + "%' and ativo = true ORDER BY nome";
        ResultSet rs = this.st.executeQuery(sql);
        List produtos = new ArrayList();
        while (rs.next()) {
            Produto produto = (Produto) this.getByID(rs.getInt("id"));
            if (produto != null)
                produtos.add(produto);
        }
        return produtos;
    }

    public Object getByID(Integer id) throws SQLException {
        String sql = "Select * from produto where id =" + id + " ;";
        PreparedStatement preparedStatement = st.getConnection().prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        Produto produto = null;
        while (rs.next()) {
            produto = new Produto();
            produto.setId(rs.getInt("id"));
            produto.setAtivo(rs.getBoolean("ativo"));
            produto.setNome(rs.getString("nome"));
            produto.setUnidadeMedida(rs.getString("unidade_medida"));
            produto.setGrupo((Grupo) new GrupoDao<>().getByID(rs.getInt("grupo_id")).get());
            produto.setMarca((Marca) new MarcaDao<>().getByID(rs.getInt("marca_id")));
            produto.setValor(rs.getDouble("valor"));
            produto.setPrecoCompra(rs.getDouble("preco_compra"));
            produto.setQuantidadeEstoque(rs.getDouble("quantidade_estoque"));
            try {
                produto.setUltimoFornecedor(new FornecedorDao().getNomeByID(rs.getInt("ultimo_fornecedor_id")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            produto.setDataUltimaCompra(rs.getDate("data_ultima_compra"));
            produto.setDataUltimaAlteracao(rs.getDate("data_ultima_alteracao"));
            produto.setDataCadastro(rs.getDate("data_cadastro"));
            produto.setQuantidadeMinima(rs.getDouble("quantidade_minima"));
            produto.setAtivo(rs.getBoolean("ativo"));
            produto.setCodigoBarras(rs.getString("codigo_barras"));
            produto.setFuncionarioCadastro(new FuncionarioDao().getByID(rs.getInt("funcionario_cadastro")));
            produto.setFuncionarioUltimaAtualizacao(new FuncionarioDao().getByID(rs.getInt("funcionario_ultima_alteracao")));

        }
        return produto;
    }

    public void deleteByID(Integer id) throws Exception {
        String sql = "DELETE  FROM produto WHERE id = " + id + " ;";
        this.st.executeUpdate(sql);
    }

    public List<Produto> findByMarca(Integer idMarca) throws SQLException {
        String sql = "select id from produto where marca_id = " + idMarca + " ;";
        ResultSet rs = this.st.getConnection().prepareStatement(sql).executeQuery();
        List<Produto> produtos = new ArrayList<>();
        while (rs.next()) {
            produtos.add((Produto) getByID(rs.getInt("id")));
        }
        return produtos;
    }


}
