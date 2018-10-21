package cc.coopersoft.framework.services;

import java.io.Serializable;

public interface EntityService<E, PK extends Serializable> {

    E getEntity(PK id);

    E saveEntity(E entity);

    void deleteEntity(E entity);

    E createNew();
}
