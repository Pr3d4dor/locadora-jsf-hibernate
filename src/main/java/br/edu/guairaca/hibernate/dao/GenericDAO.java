package br.edu.guairaca.hibernate.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public abstract class GenericDAO<T, I extends Serializable> {

    protected final EntityManager entityManager;
    protected final Class<T> clazz;

    protected GenericDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.clazz = clazz;
    }

    public void persist(T entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public T merge(T entity) {
        T updatedEntity = null;

        try {
            entityManager.getTransaction().begin();
            updatedEntity = entityManager.merge(entity);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }

        return updatedEntity;
    }

    public void remove(T entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public T find(I id) {
        return entityManager.find(clazz, id);
    }

    public List<T> findAll() {
        TypedQuery<T> query = entityManager.createQuery("FROM " + clazz.getName(), clazz);
        return query.getResultList();
    }
}
