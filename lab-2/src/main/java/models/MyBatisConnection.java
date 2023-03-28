package models;

import catConnections.CatMyBatis;
import ownerConnections.OwnerMyBatis;
import java.io.IOException;

public class MyBatisConnection {
    private final CatMyBatis catMyBatis;

    private final OwnerMyBatis ownerMyBatis;

    public MyBatisConnection() throws IOException {
        catMyBatis = new CatMyBatis();
        ownerMyBatis = new OwnerMyBatis(this);
    }

    public CatMyBatis getCatMyBatis() {
        return catMyBatis;
    }

    public OwnerMyBatis getOwnerMyBatis() {
        return ownerMyBatis;
    }
}
