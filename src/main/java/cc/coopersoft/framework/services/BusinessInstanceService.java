package cc.coopersoft.framework.services;

import cc.coopersoft.framework.EntityDataPage;
import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.data.BusinessOperationLog;
import cc.coopersoft.framework.data.KeyAndCount;

import java.util.List;

public interface BusinessInstanceService extends EntityService<BusinessInstance,String>,java.io.Serializable {

    void putOperationLog(BusinessInstance business,BusinessOperationLog log);

    BusinessOperationLog createOperationLog();

    EntityDataPage<BusinessInstance> search(String condition, List<String> defineIds, int offset , int count);

    List<KeyAndCount> searchDefineCount(String condition);

}
