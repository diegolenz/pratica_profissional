package imp.comercial.venda;

import imp.AbstractDao;
import lib.model.comercial.ConfigVenda;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfigVendaDao extends AbstractDao {

    public ConfigVenda get()throws SQLException {
        String sql = "select * from config_venda ;";
        ResultSet rs = this.st.getConnection().prepareStatement(sql).executeQuery();
        if (rs.next()){
            ConfigVenda configVenda = new ConfigVenda();
            configVenda.setId(rs.getInt("id"));
            configVenda.setModeloProduto(rs.getString("modelo_produto"));
            configVenda.setModeloServico(rs.getString("modelo_servico"));
            configVenda.setSerieProduto(rs.getInt("serie_produto"));
            configVenda.setSerieServico(rs.getInt("serie_servico"));
            configVenda.setNumProd(rs.getInt("num_produto"));
            configVenda.setNumServico(rs.getInt("num_servico"));
            return configVenda;
        }
        return null;
    }

    public void save(ConfigVenda configVenda)throws SQLException{
        String sql = "INSERT INTO public.config_venda" +
                "(modelo_servico, modelo_produto, serie_servico, serie_produto, num_produto, num_servico)" +
                "VALUES( '"+configVenda.getModeloServico()+"', '"+configVenda.getModeloProduto()+"', "+configVenda.getSerieServico()+"," +
                " "+configVenda.getSerieProduto()+", "+configVenda.getNumProd()+", "+configVenda.getNumServico()+" );";
        this.st.getConnection().prepareStatement(sql).executeUpdate();
    }

    public void update(ConfigVenda configVenda)throws SQLException{
        String sql = "UPDATE public.config_venda SET modelo_servico='"+configVenda.getModeloServico()+"', modelo_produto='"+configVenda.getModeloProduto()+"'," +
                " serie_servico="+configVenda.getSerieServico()+", serie_produto="+configVenda.getSerieProduto()+", " +
                "num_produto="+configVenda.getNumProd()+", num_servico="+configVenda.getNumServico() +
       " WHERE id= "+configVenda.getId()+" ";
        ;
        this.st.getConnection().prepareStatement(sql).executeUpdate();
    }








}
