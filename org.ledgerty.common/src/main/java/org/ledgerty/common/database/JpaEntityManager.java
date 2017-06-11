package org.ledgerty.common.database;

import org.hibernate.Session;
import org.ledgerty.common.ValidationUtils;
import org.ledgerty.exceptions.DatabaseException;
import org.ledgerty.exceptions.InvalidParametersException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by Héctor on 11/06/2017.
 */
public class JpaEntityManager<T> implements GenericEntityManager<T> {

    private EntityManager entityManager;
    private Class<T> entityClass;
    private Session session;

    public JpaEntityManager(Class<T> entityClass, EntityManager entityManager) throws ClassNotFoundException {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    public Stream<T> select(Query query) throws IllegalArgumentException, DatabaseException {
        ValidationUtils.validateIsTrue(InvalidParametersException.class, query != null && query.hasQueryString(), "The query is null or empty");
        return executeQuery(query);
    }

    public void insert(List<T> entityList) throws IllegalArgumentException, DatabaseException {
        entityList.stream().parallel().forEach((entity) -> {
            insert(entity);
        });
    }

    public T insert(T entity) throws IllegalArgumentException, DatabaseException {
        return executeDatabaseOperation(() -> {
            entityManager.persist(entity);
            return entity;
        });

    }

    public void update(List<T> entityList) throws IllegalArgumentException, DatabaseException {
        entityList.stream().parallel().forEach((entity) -> {
            update(entity);
        });
    }

    public T update(T entity) throws IllegalArgumentException, DatabaseException {
        return executeDatabaseOperation(() -> {
            Session session = getSession();
            if(session.contains(entity)) {
                session.evict(entity);
            }
            session.update(entity);
            return entity;
        });
    }

    public void delete(List<T> entityList) throws IllegalArgumentException, DatabaseException {
        entityList.stream().parallel().forEach((entity) -> {
            delete(entity);
        });
    }

    public T delete(T entity) throws IllegalArgumentException, DatabaseException {
        return executeDatabaseOperation(() -> {
            entityManager.remove(entity);
            return entity;
        });
    }

    /** FUNCTIONS **/

    private Session getSession() {
        if (this.session == null) {
            this.session = (Session)entityManager.getDelegate();
        }
        return this.session;
    }

    private Class getGenericClass() throws ClassNotFoundException {
        Type mySuperclass = this.getClass().getGenericSuperclass();
        Type genericType = (((ParameterizedType)mySuperclass).getActualTypeArguments()[0]);
        return Class.forName(genericType.getTypeName());
    }

    private <T> T executeDatabaseOperation(Supplier<T> action) {
        try {
            return action.get();
        } catch (Exception ex) {
            throw new DatabaseException(ex.getMessage());
        }
    }

    private Stream<T> executeQuery(Query query) {
        return executeDatabaseOperation(() -> {
            final TypedQuery<T> typedQuery = this.entityManager.createQuery(query.getQueryString(), this.entityClass);

            query.getQueryParameters().forEach((key, value) -> {
                typedQuery.setParameter(key, value);
            });

            if (query.hasPaginationInfo()) {
                typedQuery
                        .setFirstResult((query.getPageNumber() - 1) * query.getPageSize())
                        .setMaxResults(query.getPageSize());
            }

            return typedQuery.getResultList().stream();
        });
    }
}
