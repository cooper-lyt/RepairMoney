package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.data.BusinessOperationLog;
import cc.coopersoft.framework.services.BusinessInstanceService;
import cc.coopersoft.framework.services.SimpleEntityService;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.repository.BusinessRepository;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.inject.Inject;

public class BusinessService extends SimpleEntityService<BusinessEntity,String> implements BusinessInstanceService<BusinessEntity> {


    @Inject
    private BusinessRepository businessRepository;


    @Override
    public void putOperationLog(BusinessEntity business, BusinessOperationLog log) {

    }

    @Override
    public BusinessOperationLog createOperationLog() {
        return null;
    }

    @Override
    public BusinessInstance saveEntity(BusinessInstance businessInstance) {
        return saveEntity(businessInstance);
    }

    @Override
    public EntityRepository<BusinessEntity, String> getEntityRepository() {
        return businessRepository;
    }

    @Override
    public BusinessEntity createNew() {
        return new BusinessEntity();
    }
}
