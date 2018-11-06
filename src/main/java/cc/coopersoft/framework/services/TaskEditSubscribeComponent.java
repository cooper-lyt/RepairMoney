package cc.coopersoft.framework.services;

import cc.coopersoft.framework.data.BusinessInstance;

public interface TaskEditSubscribeComponent<T extends BusinessInstance> extends TaskActionComponent<T>, TaskValidComponent<T>{


    /** 初始化
     * 在开始加载所有订阅者页时执行，可能执行多次
     */
    void init(T businessInstance);


}
