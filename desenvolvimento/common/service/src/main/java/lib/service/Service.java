package lib.service;

import imp.AbstractDao;

import java.sql.SQLException;

public class Service {
    private AbstractDao abstractDao;

    public Service(){
        abstractDao = new AbstractDao();
    }

    public void setAutoCommit(Boolean is)throws SQLException{
        this.abstractDao.setAutoCommit(is);
    }

    public void rollBack()throws SQLException{
        this.abstractDao.rollBack();
    }

    public void commit()throws SQLException {
        abstractDao.commit();
    }

    public void clear()throws SQLException{
        abstractDao.clear();
    }
}
