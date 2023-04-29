package ownerConnections;

import entities.Cat;
import entities.Owner;
import models.HibernateConnection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import tools.CatException;
import tools.OwnerException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class OwnerHibernate implements OwnerConnection {
    private final SessionFactory sessionFactory;

    private final HibernateConnection hibernateConnection;

    public OwnerHibernate(HibernateConnection hibernateConnection) {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Cat.class);
        configuration.addAnnotatedClass(Owner.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
        this.hibernateConnection = hibernateConnection;
    }

    public Owner save(Owner entity) throws OwnerException {
        if (entity == null) {
            throw OwnerException.ownerIsNullException();
        }

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return entity;
    }

    public void deleteById(long id) throws CatException {
        List<Cat> childrenEntities = getByVId(id);
        for (Cat cat : childrenEntities) {
            hibernateConnection.getCatHibernate().deleteById(cat.getId());
        }

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Owner owner = session.load(Owner.class, id);
        session.delete(owner);
        transaction.commit();
        session.close();
    }

    public void deleteByEntity(Owner entity) throws OwnerException, CatException {
        if (entity == null) {
            throw OwnerException.ownerIsNullException();
        }

        List<Cat> childrenEntities = getByVId(entity.getId());
        for (Cat cat : childrenEntities) {
            hibernateConnection.getCatHibernate().deleteById(cat.getId());
        }

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(entity);
        transaction.commit();
        session.close();
    }

    public void deleteAll() {
        hibernateConnection.getCatHibernate().deleteAll();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "Delete from Owner";
        session.createQuery(hql).executeUpdate();
        transaction.commit();
        session.close();
    }

    public Owner update(Owner entity) throws OwnerException {
        if (entity == null) {
            throw OwnerException.ownerIsNullException();
        }

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();
        session.close();
        return entity;
    }

    public Owner getById(long id) {
        return sessionFactory.openSession().get(Owner.class, id);
    }

    public List<Owner> getAll() {
        return sessionFactory.openSession().createQuery("From Owner").stream().toList();
    }

    public List<Cat> getAllByVId(long id) {
        List<Cat> cats = getByVId(id);

        if (cats.size() > 5) {
            return cats.stream().limit(5).toList();
        }

        return cats;
    }

    private List<Cat> getByVId(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Cat> criteriaQuery = builder.createQuery(Cat.class);
        Root<Cat> root = criteriaQuery.from(Cat.class);
        criteriaQuery.select(root).where(builder.equal(root.get("ownerId"), id));
        List<Cat> results = session.createQuery(criteriaQuery).getResultList();
        transaction.commit();
        session.close();
        return results;
    }
}
