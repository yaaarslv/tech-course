package catConnections;

import entities.Cat;
import tools.CatException;
import tools.OwnerException;
import java.sql.SQLException;
import java.util.List;

public interface CatConnection {
    Cat save(Cat entity) throws SQLException, CatException;
    void deleteById(long id) throws SQLException;
    void deleteByEntity(Cat entity) throws CatException, SQLException;
    void deleteAll() throws SQLException;
    Cat update(Cat entity) throws CatException, SQLException;
    Cat getById(long id) throws SQLException, OwnerException, CatException;
    List<Cat> getAll() throws SQLException, OwnerException, CatException;
}
