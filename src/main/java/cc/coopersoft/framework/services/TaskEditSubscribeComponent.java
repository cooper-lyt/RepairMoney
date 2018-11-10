package cc.coopersoft.framework.services;

import cc.coopersoft.framework.data.BusinessInstance;

public interface TaskEditSubscribeComponent<T extends BusinessInstance> extends TaskActionComponent<T>, TaskValidComponent<T>{


    /** 初始化
     * 在开始加载所有订阅者页时执行，可能执行多次
     *
     * @Return 返回false表示本订阅都没有要操作的项，如果本页所有订阅都没有要操作的项则跳过此页
     */
    boolean init(T business);


    void doCreate(T business);
}
