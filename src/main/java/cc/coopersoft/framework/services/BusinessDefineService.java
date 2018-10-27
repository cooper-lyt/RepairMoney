package cc.coopersoft.framework.services;

import cc.coopersoft.framework.data.model.BusinessDefineEntity;
import cc.coopersoft.framework.data.repository.BusinessDefineRepository;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.inject.Inject;
import java.util.List;

public class BusinessDefineService extends SimpleEntityService<BusinessDefineEntity,String> {


    @Inject
    private BusinessDefineRepository businessDefineRepository;

    @Override
    public EntityRepository<BusinessDefineEntity, String> getEntityRepository() {
        return businessDefineRepository;
    }

    @Override
    public BusinessDefineEntity createNew() {
        return new BusinessDefineEntity();
    }

    public List<BusinessDefineEntity> allEnableDefine(){
        return businessDefineRepository.queryAllEnable();
    }

    public List<BusinessDefineEntity> findAll(){
        return businessDefineRepository.findAll();
    }

}
