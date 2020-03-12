package imp.os;


import imp.AbstractDao;
import imp.comercial.venda.VendaProdutoDao;
import imp.comercial.venda.VendaServicoDao;
import imp.financeiro.condicaoPagamentoDAO.CondicaoPagamentoDAO;
import imp.os.modelo.ModeloDao;
import imp.pessoa.ClienteDAO;
import imp.produto.ProdutoDao;
import imp.servico.ServicoDao;
import imp.sistema.FuncionarioDao;
import lib.model.comercial.ItemProduto;
import lib.model.comercial.VendaProduto;
import lib.model.comercial.VendaServico;
import lib.model.os.OrdemServico;
import lib.model.produto.Produto;
import lib.model.servico.ItemServico;
import lib.model.servico.Servico;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdemServicoDao extends AbstractDao {

    public List<OrdemServico> getAll() throws SQLException {
        ResultSet rs = this.st.getConnection().prepareStatement("select * from ordem_servico ").executeQuery();
        List<OrdemServico> ordens = new ArrayList<>();
        while (rs.next()) {
            ordens.add(getById(rs.getInt("id")));
        }
        return ordens;
    }

    public List<ItemServico> getItensByIdOrdem(Integer id, OrdemServico ordemServico) throws SQLException {
        ResultSet rs = this.st.getConnection().prepareStatement("select * from item_servico_os where os_id = " + id + " ;").executeQuery();
        List<ItemServico> itens = new ArrayList<>();
        while (rs.next()) {
            ItemServico item = new ItemServico();
            item.setValorUnitario(rs.getDouble("valor_unitario"));
            item.setValorTotal(rs.getDouble("valor_total"));
            item.setDescontoUnitario(rs.getDouble("desconto_unitario"));
            item.setAcrescimoUnitario(rs.getDouble("acrescimo_unitario"));
            item.setQuantidade(rs.getDouble("quantidade"));
            item.setValorRateio(rs.getDouble("valor_rateio"));
            item.setOs(ordemServico);
            item.buildItem((Servico) new ServicoDao<Servico>().getByID(rs.getInt("servico_id")));
            itens.add(item);
            // item.setVenda(new VendaServicoService().getByNumSerieModelo(rs.getInt("numero_venda_produto")));
        }
        return itens;
    }

    public List<ItemProduto> getItensProdutosByIdOrdem(Integer id, OrdemServico ordemServico) throws SQLException {
        ResultSet rs = this.st.getConnection().prepareStatement("select * from item_produto_os where os_id = " + id + " ;").executeQuery();
        List<ItemProduto> itens = new ArrayList<>();
        while (rs.next()) {
            ItemProduto item = new ItemProduto();
            //    item.setId("");
            item.setValorUnitario(rs.getDouble("valor_unitario"));
            item.setValorTotal(rs.getDouble("valor_total"));
            item.setDescontoUnitario(rs.getDouble("desconto_unitario"));
            item.setAcrescimoUnitario(rs.getDouble("acrescimo_unitario"));
            item.setQuantidade(rs.getDouble("quantidade"));
            item.setValorRateio(rs.getDouble("valor_rateio"));
            item.setOs(ordemServico);
            item.buildItem((Produto) new ProdutoDao<Produto>().getByID(rs.getInt("produto_id")));
            itens.add(item);
            // item.setVenda(new VendaServicoService().getByNumSerieModelo(rs.getInt("numero_venda_produto")));
        }
        return itens;
    }

    public Integer getUltimoIdOs() throws SQLException {
        ResultSet resultSet = this.st.executeQuery("Select id from ordem_servico order by ID desc limit 1");
        Integer id = null;
        while (resultSet.next())
            id = resultSet.getInt("id");
        return id;
    }

    public void save(OrdemServico os) throws SQLException {
        Integer idCond = os.getCondicaoPagamento() != null ? os.getCondicaoPagamento().getId() : null;
        Integer idResp = os.getResponsavel() != null ? os.getResponsavel().getId() : null;

        String sql = "insert into ordem_servico (data_cadastro, data_ultima_alteracao, funcionario_cadastro_id, " +
                "funcionario_ultima_alteracao_id, condicao_pagamento_id, cancelada, numero_venda_produto, serie_venda_produto, modelo_venda_produto," +
                "numero_venda_servico, serie_venda_servico, modelo_venda_servico, cliente_id, responsavel_id, modelo_id, chassis, placa, cor, ano, descricao_problema, descricao_solucao) values ('" + new Date() + "', '" + new Date() + "'," +
                os.getFuncionarioUltimaAtualizacao().getId() + ", " + os.getFuncionarioUltimaAtualizacao().getId() + ", " + idCond +
                ", " + os.getCancelada();
        if (os.getVendaProduto() != null) {
            sql += "," + os.getVendaProduto().getNumeroNota() + ", " + os.getVendaProduto().getNumSerieNota() + ", '" + os.getVendaProduto().getModeloNota() + "'";
        } else {
            sql += "," + null + "," + null + ", " + null + "";
        }
        if (os.getVendaServico() != null) {
            sql += "," + os.getVendaServico().getNumeroNota() + ", " + os.getVendaServico().getNumSerieNota() + ", '" + os.getVendaServico().getModeloNota() + "'";
        } else {
            sql += "," + null + "," + null + ", " + null + "";
        }
        sql += ", " + os.getCliente().getId() + ", "+idResp+", "+os.getModelo().getId()+", '"+os.getChassis()+"', '"+os.getPlaca()+"', '"+os.getCor()+"', "+os.getAno()+", " +
                "'"+os.getDescricaoProblema() + "', '"+ os.getDescricaoConclusao()+"' );";

        this.st.getConnection().prepareStatement(sql).executeUpdate();
        os.setId(getUltimoIdOs());
        this.saveItensProdutos(os.getItensProdutos(), os.getId());
        this.saveItensServicos(os.getItensServicos(), os.getId());

    }

    public void saveItensServicos(List<ItemServico> itens, Integer idOs) throws SQLException {
        for (ItemServico item : itens) {
            this.st.getConnection().prepareStatement("INSERT INTO public.item_servico_os " +
                    "(  quantidade, desconto_unitario, acrescimo_unitario, valor_unitario, valor_total, valor_rateio, os_id, servico_id) " +
                    "VALUES( "+item.getQuantidade() + ", " + item.getDescontoUnitario() + "," + item.getAcrescimoUnitario() + ", " + item.getValorUnitario() + ", " + item.getValorTotal() + ", "
                    + item.getValorRateio() + "," + idOs + ", " + item.getId() + " ) ").executeUpdate();
        }
    }

    public void saveItensProdutos(List<ItemProduto> itens, Integer id) throws SQLException {
        for (ItemProduto item : itens) {
            this.st.getConnection().prepareStatement("INSERT INTO item_produto_os " +
                    "( quantidade, desconto_unitario, acrescimo_unitario, valor_unitario, valor_total, valor_rateio, os_id, produto_id) " +
                    "VALUES(" + item.getQuantidade() + ", " + item.getDescontoUnitario() + "," + item.getAcrescimoUnitario() + ", " + item.getValorUnitario() + ", " + item.getValorTotal() + ", "
                    + item.getValorRateio() + "," + id + ", " + item.getId() + ") ").executeUpdate();
        }
    }

    public void update(OrdemServico os) throws SQLException{
        Integer idCondicao = os.getCondicaoPagamento() != null ? os.getCondicaoPagamento().getId() : null;
        Integer numVendaProd = os.getVendaProduto() != null ? os.getVendaProduto().getNumeroNota() : null;
        Integer serieVendaProd = os.getVendaProduto() != null ? os.getVendaProduto().getNumSerieNota() : null;
        String modVendaProd = os.getVendaProduto() != null ? os.getVendaProduto().getModeloNota() : null;
        Integer numVendaS = os.getVendaServico() != null ? os.getVendaServico().getNumeroNota() : null;
        Integer serieVendaS = os.getVendaServico() != null ? os.getVendaServico().getNumSerieNota() : null;
        String modVendaS = os.getVendaServico() != null ? os.getVendaServico().getModeloNota() : null;

        String sql = "UPDATE ordem_servico " +
                "SET data_ultima_alteracao='" + new Date() + "'," +
                " funcionario_ultima_alteracao_id="+ os.getFuncionarioUltimaAtualizacao().getId()+", condicao_pagamento_id=" + idCondicao + ", cancelada=" + os.getCancelada() + "," +
                " numero_venda_produto=" + numVendaProd + ", serie_venda_produto=" + serieVendaProd + ", modelo_venda_produto='" + modVendaProd + "', numero_venda_servico=" + numVendaS + ", " +
                "serie_venda_servico=" + serieVendaS + ", modelo_venda_servico='" + modVendaS + "', cliente_id=" + os.getCliente().getId() + " " +
                ", chassis = '"+os.getChassis() + "', km = "+os.getKm() + ", placa = '"+ os.getPlaca() + "', " +
                "cor = '"+os.getCor()+"', ano = "+os.getAno()+", descricao_problema = '" + os.getDescricaoProblema() + "' , descricao_solucao = '" + os.getDescricaoConclusao() +
                "' WHERE id= "+os.getId()+" ;";

        this.st.getConnection().prepareStatement(sql).executeUpdate();
    }

    public void deleteItensServicos(List<ItemServico> itemServicos) throws SQLException {
        for (ItemServico item : itemServicos) {
            this.st.getConnection().prepareStatement("delete from item_servico_os where servico_id = " + item.getId() + " and os_id = "+ item.getOs().getId() +" ").executeUpdate();
        }
    }

    public void deleteItensProdutos(List<ItemProduto> itemServicos) throws SQLException {
        for (ItemProduto item : itemServicos) {
            this.st.getConnection().prepareStatement("delete from item_produto_os where produto_id = " + item.getId() + " and os_id = "+ item.getOs().getId() +" ;").executeUpdate();
        }
    }

    public OrdemServico getById(Integer id) throws SQLException {
        ResultSet rs = this.st.getConnection().prepareStatement("select * from ordem_servico where id = " + id + " ;").executeQuery();
        if (rs.next()) {
            OrdemServico ordemServico = new OrdemServico();
            ordemServico.setId(rs.getInt("ID"));
            ordemServico.setDataCadastro(rs.getDate("data_cadastro"));
            ordemServico.setDatUltimaAlteracao(rs.getDate("data_ultima_alteracao"));
            ordemServico.setFuncionarioCadastro(new FuncionarioDao().getByID(rs.getInt("funcionario_cadastro_id")));
            ordemServico.setFuncionarioCadastro(new FuncionarioDao().getByID(rs.getInt("funcionario_ultima_alteracao_id")));
            ordemServico.setItensServicos(this.getItensByIdOrdem(ordemServico.getId(), ordemServico));
            ordemServico.setItensProdutos(this.getItensProdutosByIdOrdem(ordemServico.getId(), ordemServico));
            ordemServico.setCondicaoPagamento(new CondicaoPagamentoDAO().getByID(rs.getInt("condicao_pagamento_id")));
            ordemServico.setCancelada(rs.getBoolean("cancelada"));
            ordemServico.setModelo(new ModeloDao().getByID(rs.getInt("modelo_id")));
            ordemServico.setChassis(rs.getString("chassis"));
            ordemServico.setAno(rs.getInt("ano"));
            ordemServico.setDescricaoProblema(rs.getString("descricao_problema"));
            ordemServico.setDescricaoConclusao(rs.getString("descricao_solucao"));
            ordemServico.setCor(rs.getString("cor"));
            ordemServico.setKm(rs.getDouble("km"));
            ordemServico.setPlaca(rs.getString("placa"));
            ordemServico.setCliente(new ClienteDAO().getByID(rs.getInt("cliente_id")));
            ordemServico.setResponsavel(new FuncionarioDao().getByID(rs.getInt("responsavel_id")));
            ordemServico.setFuncionarioCadastro(new FuncionarioDao().getByID(rs.getInt("funcionario_cadastro_id")));
            ordemServico.setFuncionarioUltimaAtualizacao(new FuncionarioDao().getByID(rs.getInt("funcionario_ultima_alteracao_id")));
            ordemServico.setVendaProduto((VendaProduto) new VendaProdutoDao().getByID(
                    rs.getString("modelo_venda_produto"),
                    rs.getInt("numero_venda_produto"),
                    rs.getInt("serie_venda_produto")));
            ordemServico.setVendaServico((VendaServico) new VendaServicoDao()
                    .getByID(rs.getString("modelo_venda_servico"),
                            rs.getInt("numero_venda_servico"),
                            rs.getInt("serie_venda_servico")));
            return ordemServico;

        }
        return null;
    }

}
