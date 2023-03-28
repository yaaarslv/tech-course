package models;

import catConnections.CatJdbc;
import ownerConnections.OwnerJdbc;
import tools.ConnectionException;
import java.sql.SQLException;

public class JdbcConnection {
    private final CatJdbc catJdbc;

    private final OwnerJdbc ownerJdbc;

    public JdbcConnection(int localhost, String database, String user, String password) throws ConnectionException, SQLException {
        if (database.isBlank()) {
            throw ConnectionException.databaseNameIsNullException();
        }

        if (user.isBlank()) {
            throw ConnectionException.userIsNullException();
        }

        if (password.isBlank()) {
            throw ConnectionException.passwordIsNullException();
        }

        catJdbc = new CatJdbc(localhost, database, user, password);
        ownerJdbc = new OwnerJdbc(localhost, database, user, password, this);
    }

    public CatJdbc getCatJdbc() {
        return catJdbc;
    }

    public OwnerJdbc getOwnerJdbc() {
        return ownerJdbc;
    }
}
