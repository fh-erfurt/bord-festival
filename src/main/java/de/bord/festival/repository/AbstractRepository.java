package de.bord.festival.repository;


import de.bord.festival.dataSource.DataSource;
import de.bord.festival.models.AbstractModel;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Is responsible for basic database operations
 *
 * @param <T> is type of object which should use database operations
 */
public abstract class AbstractRepository<T extends AbstractModel> {

    private DataSource dataSource;

    public AbstractRepository() {
        dataSource = DataSource.getDataSource();
    }

    protected abstract void updateOperation(T model, String argument);


    public Long create(T model) {
        EntityManager entityManager = dataSource.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(model);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return model.getId();

    }

    public void update(T model, String argument) {
        EntityManager entityManager = dataSource.getEntityManager();
        entityManager.getTransaction().begin();

        updateOperation(model, argument);

        entityManager.merge(model);
        entityManager.getTransaction().commit();
    }

    public void delete(T model) {
        EntityManager entityManager = dataSource.getEntityManager();
        entityManager.getTransaction().begin();
        model = entityManager.merge(model);

        entityManager.remove(model);
        entityManager.getTransaction().commit();
    }

    public T findOne(T model) {
        EntityManager entityManager = dataSource.getEntityManager();
        return (T) entityManager.find(model.getClass(), model.getId());
    }


    public List<T> findAll(String className) {
        EntityManager entityManager = dataSource.getEntityManager();
        Query query = entityManager.createQuery(
                "SELECT c FROM " + className + " c");
        return query.getResultList();
    }

    public void delete(List<T> entries) {
        EntityManager entityManager = dataSource.getEntityManager();
        entityManager.getTransaction().begin();
        for (T entry : entries) {
            entityManager.remove(entry);
        }
        entityManager.getTransaction().commit();
    }
}
