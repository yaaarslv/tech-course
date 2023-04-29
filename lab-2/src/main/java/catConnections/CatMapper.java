package catConnections;

import entities.Cat;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface CatMapper {
    @Insert("INSERT INTO Cat(Имя, \"Дата рождения\", Порода, Цвет, Владелец) VALUES(#{name}, #{birthdate}, #{breed}, #{color}, #{ownerId})")
    int save(Cat entity);

    @Delete("DELETE FROM Cat WHERE Идентификатор = #{id}")
    void deleteById(long id);

    @Delete("DELETE FROM Cat WHERE Идентификатор = #{id} AND Имя = #{name} AND \"Дата рождения\" = #{birthdate} AND Порода = #{breed} AND Цвет = #{color} AND Владелец = #{ownerId}")
    void deleteByEntity(Cat entity);

    @Delete("DELETE FROM Cat")
    void deleteAll();

    @Update("UPDATE Cat SET Имя = #{name}, \"Дата рождения\" = #{birthdate}, Порода = #{breed}, Цвет = #{color}, Владелец = #{ownerId} WHERE Идентификатор = #{id}")
    int update(Cat entity);

    @Select("SELECT * FROM Cat WHERE Идентификатор = #{id}")
    @Results({
            @Result(property = "id", column = "Идентификатор"),
            @Result(property = "name", column = "Имя"),
            @Result(property = "birthdate", column = "\"Дата рождения\""),
            @Result(property = "breed", column = "Порода"),
            @Result(property = "color", column = "Цвет"),
            @Result(property = "ownerId", column = "Владелец")
    })
    Cat getById(long id);

    @Select("SELECT * FROM Cat")
    @Results({
            @Result(property = "id", column = "Идентификатор"),
            @Result(property = "name", column = "Имя"),
            @Result(property = "birthdate", column = "\"Дата рождения\""),
            @Result(property = "breed", column = "Порода"),
            @Result(property = "color", column = "Цвет"),
            @Result(property = "ownerId", column = "Владелец")
    })
    List<Cat> getAll();
}
