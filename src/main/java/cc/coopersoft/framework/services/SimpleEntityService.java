package cc.coopersoft.framework.services;

import org.apache.deltaspike.data.api.EntityRepository;

public abstract class SimpleEntityService<E,PK extends java.io.Serializable> implements EntityService<E,PK> , java.io.Serializable{

    public abstract EntityRepository<E, PK> getEntityRepository();

    @Override
    public E getEntity(PK id) {
        return getEntityRepository().findBy(id);
    }

    @Override
    public E saveEntity(E entity) {
        return getEntityRepository().saveAndFlushAndRefresh(entity);
    }

    @Override
    public void deleteEntity(E entity) {
        getEntityRepository().removeAndFlush(entity);
    }


}
