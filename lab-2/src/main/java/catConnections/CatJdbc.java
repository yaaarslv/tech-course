package catConnections;

import entities.Cat;
import models.CatColor;
import tools.CatException;
import tools.ConnectionException;
import tools.OwnerException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatJdbc implements CatConnection {
    private final Connection connection;

    public CatJdbc(int localhost, String database, String user, String password) throws SQLException, ConnectionException {
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
    }
     
    public Cat save(Cat entity) throws SQLException, CatException {
        if (entity == null) {
            throw CatException.catIsNullException();
        }

        String sql = "INSERT INTO Cat (Имя, \"Дата рождения\", Порода, Цвет, Владелец) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, entity.getName());
        statement.setDate(2, entity.getBirthdate());
        statement.setString(3, entity.getBreed());
        statement.setString(4,entity.getColor().name());
        statement.setLong(5, entity.getOwnerId());
        statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        long id = rs.getLong(1);
        entity.setId(id);
        statement.close();
        return entity;
    }

    public void deleteById(long id) throws SQLException {
        String sql = "DELETE FROM Cat WHERE Идентификатор = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, id);
        statement.executeUpdate();
        statement.close();
    }

    public void deleteByEntity(Cat entity) throws CatException, SQLException {
        if (entity == null) {
            throw CatException.catIsNullException();
        }

        String sql = "DELETE FROM Cat WHERE Идентификатор = ? AND Имя = ? AND \"Дата рождения\" = ? AND Порода = ? AND Цвет = ? AND Владелец = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, entity.getId());
        statement.setString(2, entity.getName());
        statement.setDate(3, entity.getBirthdate());
        statement.setString(4, entity.getBreed());
        statement.setString(5, entity.getColor().name());
        statement.setLong(6, entity.getOwnerId());
        statement.executeUpdate();
        statement.close();
    }

    public void deleteAll() throws SQLException {
        String sql = "DELETE FROM Cat";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
        statement.close();
    }

    public Cat update(Cat entity) throws CatException, SQLException {
        if (entity == null) {
            throw CatException.catIsNullException();
        }

        String sql = "UPDATE Cat SET Имя = ?, \"Дата рождения\" = ?, Порода = ?, Цвет = ?, Владелец = ? WHERE Идентификатор = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, entity.getName());
        statement.setDate(2, entity.getBirthdate());
        statement.setString(3, entity.getBreed());
        statement.setString(4, entity.getColor().name());
        statement.setLong(5, entity.getOwnerId());
        statement.executeUpdate();
        statement.close();
        return entity;
    }

    public Cat getById(long id) throws SQLException, OwnerException, CatException {
        String sql ="SELECT * FROM Cat WHERE Идентификатор = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, id);
        ResultSet rs = statement.executeQuery();
        rs.next();

        long catId = rs.getLong("Идентификатор");
        String name = rs.getString("Имя");
        Date birthdate = rs.getDate("Дата рождения");
        String breed = rs.getString("Порода");
        CatColor color = CatColor.valueOf(rs.getString("Цвет"));
        long ownerId = rs.getLong("Владелец");
        Cat cat = new Cat(name, birthdate, breed, color, ownerId);
        cat.setId(catId);
        statement.close();
        return cat;
    }

    public List<Cat> getAll() throws SQLException, OwnerException, CatException {
        String sql ="SELECT * FROM Cat";
        PreparedStatement statement = connection.prepareStatement(sql);
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
