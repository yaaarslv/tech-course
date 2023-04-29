package catConnections;

import entities.Cat;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import tools.CatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CatMyBatis implements CatConnection {
    private final SqlSessionFactory sqlSessionFactory;

    public CatMyBatis() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    public Cat save(Cat entity) throws CatException {
        if (entity == null) {
            throw CatException.catIsNullException();
        }

        SqlSession session = sqlSessionFactory.openSession();
        CatMapper catMapper = session.getMapper(CatMapper.class);
        catMapper.save(entity);
        session.commit();
        session.close();
        return entity;
    }

    public void deleteById(long id) {
        SqlSession session = sqlSessionFactory.openSession();
        CatMapper catMapper = session.getMapper(CatMapper.class);
        catMapper.deleteById(id);
        session.commit();
        session.close();
    }

    public void deleteByEntity(Cat entity) throws CatException {
        if (entity == null) {
            throw CatException.catIsNullException();
        }

        SqlSession session = sqlSessionFactory.openSession();
        CatMapper catMapper = session.getMapper(CatMapper.class);
        catMapper.deleteByEntity(entity);
        session.commit();
        session.close();
    }

    public void deleteAll() {
        SqlSession session = sqlSessionFactory.openSession();
        CatMapper catMapper = session.getMapper(CatMapper.class);
        catMapper.deleteAll();
        session.commit();
        session.close();
    }

    public Cat update(Cat entity) throws CatException {
        if (entity == null) {
            throw CatException.catIsNullException();
        }

        SqlSession session = sqlSessionFactory.openSession();
        CatMapper catMapper = session.getMapper(CatMapper.class);
        catMapper.update(entity);
        session.commit();
        session.close();
        return entity;
    }

    public Cat getById(long id) {
        SqlSession session = sqlSessionFactory.openSession();
        CatMapper catMapper = session.getMapper(CatMapper.class);
        Cat cat = catMapper.getById(id);
        session.close();
        return cat;
    }

    public List<Cat> getAll() {
        SqlSession session = sqlSessionFactory.openSession();
        CatMapper catMapper = session.getMapper(CatMapper.class);
        List<Cat> cats = catMapper.getAll();
        session.close();
        return cats;
    }
}
