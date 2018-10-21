package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.services.SimpleEntityService;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.repository.BusinessRepository;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.inject.Inject;


public class RepairMoneyBusinessService extends SimpleEntityService<BusinessEntity,String> {



    @Inject
    private BusinessRepository businessRepository;


    @Override
    public EntityRepository<BusinessEntity, String> getEntityRepository() {
        return businessRepository;
    }

    @Override
    public BusinessEntity createNew() {
        return new BusinessEntity();
    }
}
