package org.ledgerty.business;

import javax.persistence.EntityManager;

/**
 * Created by Héctor on 05/06/2017.
 */
public class BaseManager {

    private EntityManager entityManager;

    public BaseManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
