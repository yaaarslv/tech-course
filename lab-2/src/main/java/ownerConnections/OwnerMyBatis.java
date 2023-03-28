package ownerConnections;


import entities.Cat;
import entities.Owner;
import models.MyBatisConnection;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import tools.OwnerException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class OwnerMyBatis implements OwnerConnection {
    private final SqlSessionFactory sqlSessionFactory;

    private final MyBatisConnection myBatisConnection;

    public OwnerMyBatis(MyBatisConnection myBatisConnection) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.myBatisConnection = myBatisConnection;
    }

    public Owner save(Owner entity) throws OwnerException {
        if (entity == null) {
            throw OwnerException.ownerIsNullException();
        }

        SqlSession session = sqlSessionFactory.openSession();
        OwnerMapper ownerMapper = session.getMapper(OwnerMapper.class);
        ownerMapper.save(entity);
        session.commit();
        session.close();
        return entity;
    }

    public void deleteById(long id) {
        List<Cat> childrenEntities = getByVId(id);
        for (Cat cat : childrenEntities) {
            myBatisConnection.getCatMyBatis().deleteById(cat.getId());
        }

        SqlSession session = sqlSessionFactory.openSession();
        OwnerMapper ownerMapper = session.getMapper(OwnerMapper.class);
        ownerMapper.deleteById(id);
        session.commit();
        session.close();
    }

    public void deleteByEntity(Owner entity) throws OwnerException {
        if (entity == null) {
            throw OwnerException.ownerIsNullException();
        }

        List<Cat> childrenEntities = getByVId(entity.getId());
        for (Cat cat : childrenEntities) {
            myBatisConnection.getCatMyBatis().deleteById(cat.getId());
        }

        SqlSession session = sqlSessionFactory.openSession();
        OwnerMapper ownerMapper = session.getMapper(OwnerMapper.class);
        ownerMapper.deleteByEntity(entity);
        session.commit();
        session.close();
    }

    public void deleteAll() {
        myBatisConnection.getCatMyBatis().deleteAll();
        SqlSession session = sqlSessionFactory.openSession();
        OwnerMapper ownerMapper = session.getMapper(OwnerMapper.class);
        ownerMapper.deleteAll();
        session.commit();
        session.close();
    }

    public Owner update(Owner entity) throws OwnerException {
        if (entity == null) {
            throw OwnerException.ownerIsNullException();
        }

        SqlSession session = sqlSessionFactory.openSession();
        OwnerMapper ownerMapper = session.getMapper(OwnerMapper.class);
        ownerMapper.update(entity);
        session.commit();
        session.close();
        return entity;
    }

    public Owner getById(long id) {
        SqlSession session = sqlSessionFactory.openSession();
        OwnerMapper ownerMapper = session.getMapper(OwnerMapper.class);
        Owner owner = ownerMapper.getById(id);
        session.close();
        return owner;
    }

    public List<Owner> getAll() {
        SqlSession session = sqlSessionFactory.openSession();
        OwnerMapper ownerMapper = session.getMapper(OwnerMapper.class);
        List<Owner> owners = ownerMapper.getAll();
        session.close();
        return owners;
    }

    public List<Cat> getAllByVId(long id) {
        List<Cat> cats = getByVId(id);

        if (cats.size() > 5) {
            return cats.stream().limit(5).toList();
        }

        return cats;
    }

    private List<Cat> getByVId(long id) {
        SqlSession session = sqlSessionFactory.openSession();
        OwnerMapper ownerMapper = session.getMapper(OwnerMapper.class);
        List<Cat> owners = ownerMapper.getAllByVId(id);
        session.close();
        return owners;
    }
}
