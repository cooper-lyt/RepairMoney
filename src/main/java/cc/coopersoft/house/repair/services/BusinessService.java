package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.ConditionAdapter;
import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.data.BusinessOperationLog;
import cc.coopersoft.framework.data.KeyAndCount;
import cc.coopersoft.framework.services.BusinessInstanceService;
import cc.coopersoft.framework.services.SimpleEntityService;
import cc.coopersoft.framework.tools.DataHelper;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.repository.BusinessRepository;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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
        return saveEntity((BusinessEntity) businessInstance);
    }

    @Override
    public EntityRepository<BusinessEntity, String> getEntityRepository() {
        return businessRepository;
    }

    @Override
    public BusinessEntity createNew() {
        return new BusinessEntity();
    }

    private List<ConditionAdapter> splitCondition(String condition){
        if (DataHelper.empty(condition)){
            return new ArrayList<>(0);
        }
        List<ConditionAdapter> result = new ArrayList<>();
        for(String s: condition.split(" ")){
            result.add(ConditionAdapter.instance(s));
        }
        return result;
    }

    @Override
    public List<BusinessEntity> search(String condition, List<String> defineIds, int offset, int count) {
        return businessRepository.queryByKey(splitCondition(condition),defineIds,offset,count);
    }

    @Override
    public long searchCount(String condition, List<String> defineIds) {
        return businessRepository.countByKey(splitCondition(condition),defineIds);
    }

    @Override
    public List<KeyAndCount> searchDefineCount(String condition, List<String> defineIds) {
        return businessRepository.queryByKeyDefineGroup(splitCondition(condition),defineIds);
    }
}
