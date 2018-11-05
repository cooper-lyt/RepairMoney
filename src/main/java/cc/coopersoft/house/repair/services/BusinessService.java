package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.ConditionAdapter;
import cc.coopersoft.framework.EntityDataPage;
import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.data.BusinessOperationLog;
import cc.coopersoft.framework.data.KeyAndCount;
import cc.coopersoft.framework.data.model.BusinessDefineEntity;
import cc.coopersoft.framework.services.BusinessDefineService;
import cc.coopersoft.framework.services.BusinessInstanceService;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.repository.BusinessRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BusinessService implements BusinessInstanceService {


    @Inject
    private BusinessRepository businessRepository;

    @Inject
    private MainBusinessService mainBusinessService;

    @Inject
    private BusinessDefineService businessDefineService;

    @Override
    public void putOperationLog(BusinessInstance business, BusinessOperationLog log) {

    }

    @Override
    public BusinessOperationLog createOperationLog() {
        return null;
    }

    @Override
    public BusinessInstance getEntity(String id) {
        return mainBusinessService.getEntity(id);
    }

    @Override
    public BusinessInstance saveEntity(BusinessInstance businessInstance) {
        return mainBusinessService.saveEntity((BusinessEntity) businessInstance);
    }

    @Override
    public void deleteEntity(BusinessInstance businessInstance) {
        mainBusinessService.deleteEntity((BusinessEntity) businessInstance);
    }

    @Override
    public BusinessInstance createNew() {
        return mainBusinessService.createNew();
    }


    @Override
    public EntityDataPage<BusinessInstance> search(String condition, List<String> defineIds, int offset, int count) {
        List<ConditionAdapter> conditions = ConditionAdapter.instance(condition," ");
        return new EntityDataPage<>(
                new ArrayList<>(businessRepository.queryByKey(conditions,defineIds,offset,count)),
                offset,
                businessRepository.queryCountByKey(conditions,defineIds)
                ,count
        );
    }



    @Override
    public List<KeyAndCount> searchDefineCount(String condition, List<String> defineIds) {
        List<KeyAndCount> result = businessRepository.queryByKeyDefineGroup(ConditionAdapter.instance(condition," "),defineIds);

        for(KeyAndCount keyAndCount: result){
            keyAndCount.setPri(9999);
            for(BusinessDefineEntity e: businessDefineService.findAll()){
                if(keyAndCount.getKey().equals(e.getId())) {
                    keyAndCount.setName(e.getName());
                    keyAndCount.setPri(e.getPriority());
                    break;
                }
            }
        }
        Collections.sort(result);
        return result;
    }
}
