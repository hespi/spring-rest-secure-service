package org.ledgerty.services.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Héctor on 14/06/2017.
 */
@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    private EntityManagerFactory entityManagerFactory;

    @Bean
    public EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("ledgerty");
        }
        return  entityManagerFactory;
    }

    @Bean
    @Primary
    EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

}
