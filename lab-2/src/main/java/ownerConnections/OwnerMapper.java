package ownerConnections;

import entities.Cat;
import entities.Owner;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface OwnerMapper{
    @Insert("INSERT INTO Owner(Имя, \"Дата рождения\") VALUES(#{name}, #{birthdate})")
    int save(Owner entity);

    @Delete("DELETE FROM Owner WHERE Идентификатор = #{id}")
    void deleteById(long id);

    @Delete("DELETE FROM Owner WHERE Идентификатор = #{id} AND Имя = #{name} AND \"Дата рождения\" = #{birthdate}")
    void deleteByEntity(Owner entity);

    @Delete("DELETE FROM Owner")
    void deleteAll();

    @Update("UPDATE Owner SET Имя = #{name}, \"Дата рождения\" = #{birthdate} WHERE Идентификатор = #{id}")
    int update(Owner entity);

    @Select("SELECT * FROM Owner WHERE Идентификатор = #{id}")
    @Results({
            @Result(property = "id", column = "Идентификатор"),
            @Result(property = "name", column = "Имя"),
            @Result(property = "birthdate", column = "\"Дата рождения\"")
    })
    Owner getById(long id);

    @Select("SELECT * FROM Owner")
    @Results({
            @Result(property = "id", column = "Идентификатор"),
            @Result(property = "name", column = "Имя"),
            @Result(property = "birthdate", column = "\"Дата рождения\"")
    })
    List<Owner> getAll();

    @Select("SELECT * FROM Cat WHERE Владелец = #{id}")
    @Results({
            @Result(property = "id", column = "Идентификатор"),
            @Result(property = "name", column = "Имя"),
            @Result(property = "birthdate", column = "\"Дата рождения\""),
            @Result(property = "breed", column = "Порода"),
            @Result(property = "color", column = "Цвет"),
            @Result(property = "ownerId", column = "Владелец")
    })
    List<Cat> getAllByVId(long id);
}
