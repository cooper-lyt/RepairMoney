package cc.coopersoft.framework.services;

import cc.coopersoft.framework.data.BusinessInstance;

public interface TaskEditSubscribeComponent<T extends BusinessInstance> extends TaskActionComponent<T>{


    /** 初始化
     * 在开始加载所有订阅者页时执行，可能执行多次
     */
    void init(T businessInstance);


    /** 断言
     *  在建立业务前或完成任务前时调用
     * @return true:成功 false: 不成功，返回false系统会抛出异常
     */
    boolean assertion(T businessInstance);


    /** 持久化
     *  数据要保存到数据库中时调用
     *  在此方法中持久化订阅都的数据
     */
    void persistent(T businessInstance);

}
