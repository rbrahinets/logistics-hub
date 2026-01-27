package com.translogistics.hub.logistics_hub.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Abstract base repository providing generic CRUD operations using Hibernate Session API.
 *
 * @param <T>  the entity type
 * @param <ID> the primary key type
 */
@Transactional
public abstract class AbstractHibernateRepository<T, ID extends Serializable> {

    private final Class<T> entityClass;
    private final SessionFactory sessionFactory;

    protected AbstractHibernateRepository(Class<T> entityClass, SessionFactory sessionFactory) {
        this.entityClass = entityClass;
        this.sessionFactory = sessionFactory;
    }

    /**
     * Returns the current Hibernate session.
     */
    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Persists a new entity.
     */
    public void save(T entity) {
        getCurrentSession().persist(entity);
    }

    /**
     * Finds an entity by its primary key.
     */
    @Transactional(readOnly = true)
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(getCurrentSession().get(entityClass, id));
    }

    /**
     * Returns all entities of this type.
     */
    @Transactional(readOnly = true)
    public List<T> findAll() {
        CriteriaBuilder cb = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        cq.from(entityClass);
        return getCurrentSession().createQuery(cq).getResultList();
    }

    /**
     * Merges the state of a detached entity.
     */
    public T update(T entity) {
        return getCurrentSession().merge(entity);
    }

    /**
     * Deletes an entity.
     */
    public void delete(T entity) {
        getCurrentSession().remove(entity);
    }

    /**
     * Returns the total count of entities.
     */
    @Transactional(readOnly = true)
    public long count() {
        CriteriaBuilder cb = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(entityClass)));
        return getCurrentSession().createQuery(cq).getSingleResult();
    }
}
