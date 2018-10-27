package cc.coopersoft.framework.services;

import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.data.BusinessOperationLog;
import cc.coopersoft.framework.data.KeyAndCount;

import java.util.List;

public interface BusinessInstanceService<T extends BusinessInstance> extends EntityService<T,String>,java.io.Serializable {

    void putOperationLog(T business,BusinessOperationLog log);

    BusinessOperationLog createOperationLog();

    T getEntity(String id);

    BusinessInstance saveEntity(BusinessInstance businessInstance);

    T createNew();

    List<T> search(String condition, List<String> defineIds, int offset , int count);

    long searchCount(String condition, List<String> defineIds);

    List<KeyAndCount> searchDefineCount(String condition, List<String> defineIds);

}
