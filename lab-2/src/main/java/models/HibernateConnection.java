package models;

import catConnections.CatHibernate;
import ownerConnections.OwnerHibernate;
import tools.ConnectionException;
import java.sql.SQLException;

public class HibernateConnection {
    private final CatHibernate catHibernate;

    private final OwnerHibernate ownerHibernate;

    public HibernateConnection() throws ConnectionException, SQLException {
        catHibernate = new CatHibernate();
        ownerHibernate = new OwnerHibernate(this);
    }

    public CatHibernate getCatHibernate() {
        return catHibernate;
    }

    public OwnerHibernate getOwnerHibernate() {
        return ownerHibernate;
    }
}
