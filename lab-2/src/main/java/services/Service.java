package services;

import models.HibernateConnection;
import models.JdbcConnection;
import models.MyBatisConnection;
import tools.ConnectionException;
import java.io.IOException;
import java.sql.SQLException;

public class Service {
    private final JdbcConnection jdbcConnection;

    private final HibernateConnection hibernateConnection;

    private final MyBatisConnection myBatisConnection;

    public Service(int localhost, String database, String user, String password) throws ConnectionException, SQLException, IOException {
        if (database.isBlank()) {
            throw ConnectionException.databaseNameIsNullException();
        }

        if (user.isBlank()) {
            throw ConnectionException.userIsNullException();
        }

        if (password.isBlank()) {
            throw ConnectionException.passwordIsNullException();
        }

        jdbcConnection = new JdbcConnection(localhost, database, user, password);
        hibernateConnection = new HibernateConnection();
        myBatisConnection = new MyBatisConnection();
    }

    public JdbcConnection getJdbcConnection() {
        return jdbcConnection;
    }

    public HibernateConnection getHibernateConnection() {
        return hibernateConnection;
    }

    public MyBatisConnection getMyBatisConnection() {
        return myBatisConnection;
    }
}
