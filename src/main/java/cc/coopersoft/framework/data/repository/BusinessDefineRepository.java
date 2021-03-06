package cc.coopersoft.framework.data.repository;

import cc.coopersoft.framework.data.FrameworkEntityManagerResolver;
import cc.coopersoft.framework.data.model.BusinessDefineEntity;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.jpa.api.entitymanager.EntityManagerConfig;

import javax.persistence.FlushModeType;
import java.util.List;

@Repository
@EntityManagerConfig(entityManagerResolver = FrameworkEntityManagerResolver.class,flushMode = FlushModeType.COMMIT)
public interface BusinessDefineRepository extends EntityRepository<BusinessDefineEntity,String> {

    @Query("select d from BusinessDefineEntity d where d.enable = true order by d.priority")
    List<BusinessDefineEntity> queryAllEnable();
}
