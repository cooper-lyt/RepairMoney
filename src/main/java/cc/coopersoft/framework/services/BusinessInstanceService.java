package cc.coopersoft.framework.services;

import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.data.BusinessOperationLog;

public interface BusinessInstanceService<T extends BusinessInstance> extends EntityService<T,String>,java.io.Serializable {

    void putOperationLog(T business,BusinessOperationLog log);

    BusinessOperationLog createOperationLog();

    T getEntity(String id);

    BusinessInstance saveEntity(BusinessInstance businessInstance);

    T createNew();

}
