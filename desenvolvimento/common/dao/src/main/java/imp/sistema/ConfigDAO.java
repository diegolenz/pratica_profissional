package imp.sistema;

import imp.AbstractDao;
import imp.financeiro.condicaoPagamentoDAO.CondicaoPagamentoDAO;
import lib.model.sistema.ConfigGerais;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfigDAO extends AbstractDao<ConfigGerais> {


    public ConfigGerais get() throws Exception {
        String sql = "select * from sistema_config ;";
        ResultSet rs = this.st.getConnection().prepareStatement(sql).executeQuery();
        if (rs.next()) {
            ConfigGerais configGerais = new ConfigGerais();
            configGerais.setId(rs.getInt("id"));
            configGerais.setRazaoSocial(rs.getString("razao_social_empresa"));
            configGerais.setCnpj(rs.getString("cnpj_empresa"));
            configGerais.setCondPadrao(new CondicaoPagamentoDAO().getByID(rs.getInt("condicao_pagamento_padrao_id")));
            configGerais.setFuncionarioCadastro(new FuncionarioDao().getNomeAndId(rs.getInt("funcionario_cadastro_id")));
            configGerais.setFuncionarioUltimaAtualizacao(new FuncionarioDao().getNomeAndId(rs.getInt("funcionario_ultima_alteracao_id")));
            configGerais.setDataCadastro(rs.getDate("data_cadastro"));
            configGerais.setDataUltimaAlteracao(rs.getDate("data_ultima_alteracao"));
            return configGerais;
        }
        return null;
    }

    public void save(ConfigGerais configGerais) throws SQLException {
        String sql = "INSERT INTO public.sistema_config" +
                "(razao_social_empresa, cnpj_empresa, condicao_pagamento_padrao_id, funcionario_cadastro_id, funcionario_ultima_alteracao_id," +
                " data_cadastro, data_ultima_alteracao)" +
                "VALUES( '" + configGerais.getRazaoSocial() + "', '" + configGerais.getCnpj() + "', " +
                "" + configGerais.getCondPadrao().getId() + "," +
                " " + configGerais.getFuncionarioCadastro().getId() +
                ", " + configGerais.getFuncionarioUltimaAtualizacao().getId() + ", now(), now() );";
        this.st.getConnection().prepareStatement(sql).executeUpdate();
    }

    public void update(ConfigGerais configGerais) throws SQLException {
        String sql = "UPDATE public.sistema_config SET " +
                "razao_social_empresa='" + configGerais.getRazaoSocial() + "', cnpj_empresa='" + configGerais.getCnpj() + "'," +
                " condicao_pagamento_padrao_id = " + configGerais.getCondPadrao().getId() + ", " +
                " funcionario_ultima_alteracao_id = " + configGerais.getFuncionarioUltimaAtualizacao().getId() + ", " +
                " data_ultima_alteracao = now()" +
                " WHERE id= " + configGerais.getId() + " ";
        ;
        this.st.getConnection().prepareStatement(sql).executeUpdate();
    }


}
