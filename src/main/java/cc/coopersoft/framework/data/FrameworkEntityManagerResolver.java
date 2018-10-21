package cc.coopersoft.framework.data;

import cc.coopersoft.framework.BizDefine;
import org.apache.deltaspike.jpa.api.entitymanager.EntityManagerResolver;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class FrameworkEntityManagerResolver implements EntityManagerResolver {

    @Inject @BizDefine
    private EntityManager em;

    public EntityManager resolveEntityManager() {
        return em;
    }
}
