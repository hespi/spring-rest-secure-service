package org.ledgerty.business;

import org.ledgerty.business.interfaces.IBaseManager;
import org.ledgerty.common.database.GenericEntityManager;

import javax.persistence.EntityManager;

/**
 * Created by Héctor on 05/06/2017.
 */
public class BaseManager<T> implements IBaseManager<T> {

    private GenericEntityManager<T> entityManager;

    public BaseManager(GenericEntityManager<T> entityManager) {
        this.entityManager = entityManager;
    }

    public GenericEntityManager<T> getEntityManager() {
        return entityManager;
    }
}
