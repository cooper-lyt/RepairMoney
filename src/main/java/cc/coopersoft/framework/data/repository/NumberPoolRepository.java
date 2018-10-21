package cc.coopersoft.framework.data.repository;


import cc.coopersoft.framework.data.FrameworkEntityManagerResolver;
import cc.coopersoft.framework.data.model.NumberPoolEntity;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.jpa.api.entitymanager.EntityManagerConfig;

import javax.persistence.FlushModeType;

@Repository
@EntityManagerConfig(entityManagerResolver = FrameworkEntityManagerResolver.class,flushMode = FlushModeType.COMMIT)
public interface NumberPoolRepository extends EntityRepository<NumberPoolEntity,String> {
}
