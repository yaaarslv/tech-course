package ownerConnections;

import entities.Cat;
import entities.Owner;
import tools.CatException;
import tools.OwnerException;
import java.sql.SQLException;
import java.util.List;

public interface OwnerConnection {
    Owner save(Owner entity) throws OwnerException, SQLException;
    void deleteById(long id) throws SQLException, CatException, OwnerException;
    void deleteByEntity(Owner entity) throws OwnerException, SQLException, CatException;
    void deleteAll() throws SQLException;
    Owner update(Owner entity) throws OwnerException, SQLException;
    Owner getById(long id) throws SQLException, OwnerException;
    List<Owner> getAll() throws SQLException, OwnerException;
    List<Cat> getAllByVId(long id) throws SQLException, OwnerException, CatException;
}
