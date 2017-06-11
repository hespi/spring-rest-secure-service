package org.ledgerty.common.database;
import org.ledgerty.exceptions.DatabaseException;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Héctor on 11/06/2017.
 */
public interface GenericEntityManager<T> {

    Stream<T> select(Query query) throws IllegalArgumentException, DatabaseException;

    void insert(List<T> entityList) throws IllegalArgumentException, DatabaseException;
    T insert(T entity) throws IllegalArgumentException, DatabaseException;

    void update(List<T> entityList) throws IllegalArgumentException, DatabaseException;
    T update(T entity) throws IllegalArgumentException, DatabaseException;

    void delete(List<T> entityList) throws IllegalArgumentException, DatabaseException;
    T delete(T entity) throws IllegalArgumentException, DatabaseException;
}
