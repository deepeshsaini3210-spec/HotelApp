package com.grandstay.hotel.generic;

import java.util.List;
import java.util.Optional;

/**
 * Generic base service interface for common CRUD operations.
 * Use this wherever entity find/save/delete logic is repeated in business logic.
 *
 * @param <T>  entity type
 * @param <ID> primary key type
 */
public interface IBaseService<T, ID> {

    Optional<T> findById(ID id);

    /**
     * Find entity by id or throw RuntimeException if not found.
     */
    T findByIdOrThrow(ID id);

    List<T> findAll();

    T save(T entity);

    void deleteById(ID id);
}
