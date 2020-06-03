package de.bord.festival.dataSource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * Class is implemented as singleton pattern
 * Is responsible for managing the EntityManager class
 */
public class DataSource {
    private static final String PERSISTENCE_UNIT_NAME = "bord-unit";
    private EntityManagerFactory entityManagerFactory;
    private static DataSource dataSourceUniqueInstance;

    private DataSource() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        entityManagerFactory.createEntityManager();
    }

    public static DataSource getDataSource() {
        if (dataSourceUniqueInstance == null) {
            dataSourceUniqueInstance = new DataSource();
        }
        return dataSourceUniqueInstance;
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

}
