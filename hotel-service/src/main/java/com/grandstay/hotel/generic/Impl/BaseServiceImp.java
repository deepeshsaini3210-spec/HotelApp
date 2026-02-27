package com.grandstay.hotel.generic.Impl;

import com.grandstay.hotel.exceptions.ResourceNotFoundException;
import com.grandstay.hotel.generic.IBaseService;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Generic implementation of IBaseService using EntityManager (no Repository).
 * Extend this and pass EntityManager + entity class + ID extractor.
 * Override findById/findAll to use NamedQuery if needed.
 *
 * @param <T>  entity type
 * @param <ID> primary key type
 */
public abstract class BaseServiceImp<T, ID> implements IBaseService<T, ID> {

    protected final EntityManager entityManager;
    protected final Class<T> entityClass;
    protected final Function<T, ID> idExtractor;

    protected BaseServiceImp(EntityManager entityManager, Class<T> entityClass, Function<T, ID> idExtractor) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
        this.idExtractor = idExtractor;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public T findByIdOrThrow(ID id) {
        return findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found with id: " + id));
    }

    @Override
    public List<T> findAll() {
        String entityName = entityClass.getSimpleName();
        return entityManager.createQuery("SELECT e FROM " + entityName + " e", entityClass).getResultList();
    }

    @Override
    public T save(T entity) {
        if (idExtractor.apply(entity) == null) {
            entityManager.persist(entity);
            return entity;
        }
        return entityManager.merge(entity);
    }

    @Override
    public void deleteById(ID id) {
        T entity = findByIdOrThrow(id);
        entityManager.remove(entity);
    }
}
