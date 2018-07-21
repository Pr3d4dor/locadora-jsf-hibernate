package br.edu.guairaca.hibernate.dao;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerProduces {

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("hibernate");

    @Produces
    @SessionScoped
    public EntityManager criaEntityManager() {
        return factory.createEntityManager();
    }

    public void finaliza(@Disposes EntityManager manager) {
        manager.close();
    }
}
