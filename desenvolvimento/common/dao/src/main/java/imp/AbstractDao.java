package imp;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class AbstractDao<T> {

    private boolean autoCommit = true;
    private AbstractDao entityManagerHolder;
    public static Statement st;
    protected Connection con;

    public void Conexão() {

        try{

            String url ="jdbc:postgresql://localhost:5432/gestao";
            String usuario="postgres";
            String senha = "postgres";
            Class.forName("org.postgresql.Driver");
            con=null;
            con=  (Connection)  DriverManager.getConnection(url,usuario,senha);
            this.st = con.createStatement();
            System.out.println("Conexão realizada com sucesso.");


        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Erro ao se conectar com o banco de dados : " + e);
        }

    }

    protected String corrigirTermosBusca(String termos) {
        return "%" + termos.replace(" ", "%") + "%";
    }

    public void rollback()throws SQLException {
        this.st.getConnection().rollback();
    }

    public void  rollBack()throws SQLException{
        this.st.getConnection().rollback();
    }



    public void commit()throws SQLException{
        this.st.getConnection().setAutoCommit(false);
        this.st.getConnection().commit();
    }

    public void setAutoCommit(Boolean is)throws SQLException{
        this.st.getConnection().setAutoCommit(is);
    }

    public void clear() throws SQLException {
        this.st.getConnection().clearWarnings();
        this.st.getConnection().rollback();
    }
}





