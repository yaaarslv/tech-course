package catConnections;

import entities.Cat;
import entities.Owner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import tools.CatException;
import java.util.List;

public class CatHibernate implements CatConnection {
    private final SessionFactory sessionFactory;

    public CatHibernate() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Cat.class);
        configuration.addAnnotatedClass(Owner.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }
     
    public Cat save(Cat entity) throws CatException {
        if (entity == null) {
            throw CatException.catIsNullException();
        }

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return entity;
    }

    public void deleteById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Cat cat = session.load(Cat.class, id);
        session.delete(cat);
        transaction.commit();
        session.close();
    }

    public void deleteByEntity(Cat entity) throws CatException {
        if (entity == null) {
            throw CatException.catIsNullException();
        }

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(entity);
        transaction.commit();
        session.close();
    }
     
    public void deleteAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "Delete from Cat";
        session.createQuery(hql).executeUpdate();
        transaction.commit();
        session.close();
    }
     
    public Cat update(Cat entity) throws CatException {
        if (entity == null) {
            throw CatException.catIsNullException();
        }

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();
        session.close();
        return entity;
    }
     
    public Cat getById(long id) {
        return sessionFactory.openSession().get(Cat.class, id);
    }

    public List<Cat> getAll() {
        return sessionFactory.openSession().createQuery("From Cat").stream().toList();
    }
}
