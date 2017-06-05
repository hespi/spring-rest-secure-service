package org.ledgerty.business;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * Created by Héctor on 05/06/2017.
 */
public class BaseTest {
    private static EntityManagerFactory entityManagerFactory;
    protected static EntityManager entityManager;
    private EntityTransaction transaction;

    @BeforeClass
    public static void init() throws FileNotFoundException, SQLException {
        entityManagerFactory = Persistence.createEntityManagerFactory("ledgerty-test");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterClass
    public static void tearDown(){
        entityManager.clear();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Before
    public void beforeTest() {
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    @After
    public void afterTest() {
        transaction.rollback();
    }
}
