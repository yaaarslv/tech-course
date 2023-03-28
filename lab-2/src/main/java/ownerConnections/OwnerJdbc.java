package ownerConnections;

import entities.Cat;
import entities.Owner;
import models.CatColor;
import models.JdbcConnection;
import tools.CatException;
import tools.ConnectionException;
import tools.OwnerException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OwnerJdbc implements OwnerConnection {
    private final Connection connection;

    private final JdbcConnection jdbcConnection;

    public OwnerJdbc(int localhost, String database, String user, String password, JdbcConnection jdbcConnection) throws SQLException, ConnectionException {
        if (database.isBlank()) {
            throw ConnectionException.databaseNameIsNullException();
        }

        if (user.isBlank()) {
            throw ConnectionException.userIsNullException();
        }

        if (password.isBlank()) {
            throw ConnectionException.passwordIsNullException();
        }

        connection = DriverManager.getConnection("jdbc:sqlserver://localhost:" + localhost + ";encrypt=true;database=" + database + ";trustServerCertificate=true;", user, password);
        this.jdbcConnection = jdbcConnection;
    }

    public Owner save(Owner entity) throws OwnerException, SQLException {
        if (entity == null) {
            throw OwnerException.ownerIsNullException();
        }

        String sql = "INSERT INTO Owner (Имя, \"Дата рождения\") VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, entity.getName());
        statement.setDate(2, entity.getBirthdate());
        statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        long id = rs.getLong(1);
        entity.setId(id);
        statement.close();
        return entity;
    }

    public void deleteById(long id) throws SQLException, CatException, OwnerException {
        List<Cat> childrenEntities = getByVId(id);
        for (Cat cat : childrenEntities) {
            jdbcConnection.getCatJdbc().deleteById(cat.getId());
        }

        String sql = "DELETE FROM Owner WHERE Идентификатор = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, id);
        statement.executeUpdate();
        statement.close();
    }

    public void deleteByEntity(Owner entity) throws OwnerException, SQLException, CatException {
        if (entity == null) {
            throw OwnerException.ownerIsNullException();
        }

        List<Cat> childrenEntities = getByVId(entity.getId());
        for (Cat cat : childrenEntities) {
            jdbcConnection.getCatJdbc().deleteById(cat.getId());
        }

        String sql = "DELETE FROM Owner WHERE Идентификатор = ? AND Имя = ? AND \"Дата рождения\" = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, entity.getId());
        statement.setString(2, entity.getName());
        statement.setDate(3, entity.getBirthdate());
        statement.executeUpdate();
        statement.close();
    }

    public void deleteAll() throws SQLException {
        jdbcConnection.getCatJdbc().deleteAll();
        String sql = "DELETE FROM Owner";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
        statement.close();
    }

    public Owner update(Owner entity) throws OwnerException, SQLException {
        if (entity == null) {
            throw OwnerException.ownerIsNullException();
        }

        String sql = "UPDATE Cat SET Имя = ?, \"Дата рождения\" = ? WHERE Идентификатор = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, entity.getName());
        statement.setDate(2, entity.getBirthdate());
        statement.executeUpdate();
        statement.close();
        return entity;
    }

    public Owner getById(long id) throws SQLException, OwnerException {
        String sql ="SELECT * FROM Owner WHERE Идентификатор = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, id);
        ResultSet rs = statement.executeQuery();
        rs.next();

        long ownerId = rs.getLong("Идентификатор");
        String name = rs.getString("Имя");
        Date birthdate = rs.getDate("Дата рождения");
        Owner owner = new Owner(name, birthdate);
        owner.setId(ownerId);
        statement.close();
        return owner;
    }

    public List<Owner> getAll() throws SQLException, OwnerException {
        String sql ="SELECT * FROM Owner";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();

        List<Owner> owners = new ArrayList<Owner>();
        while (rs.next()) {
            long ownerId = rs.getLong("Идентификатор");
            String name = rs.getString("Имя");
            Date birthdate = rs.getDate("Дата рождения");
            Owner owner = new Owner(name, birthdate);
            owner.setId(ownerId);
            owners.add(owner);
        }

        statement.close();
        return owners;
    }

    public List<Cat> getAllByVId(long id) throws OwnerException, SQLException, CatException {
        List<Cat> cats = getByVId(id);

        if (cats.size() > 5) {
            return cats.stream().limit(5).toList();
        }

        return cats;
    }

    private List<Cat> getByVId(long id) throws SQLException, OwnerException, CatException {
        String sql ="SELECT * FROM Cat WHERE Владелец = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, id);
        ResultSet rs = statement.executeQuery();

        List<Cat> cats = new ArrayList<Cat>();
        while (rs.next()) {
            long catId = rs.getLong("Идентификатор");
            String name = rs.getString("Имя");
            Date birthdate = rs.getDate("Дата рождения");
            String breed = rs.getString("Порода");
            CatColor color = CatColor.valueOf(rs.getString("Цвет"));
            long ownerId = rs.getLong("Владелец");
            Cat cat = new Cat(name, birthdate, breed, color, ownerId);
            cat.setId(catId);
            cats.add(cat);
        }

        statement.close();
        return cats;
    }
}
