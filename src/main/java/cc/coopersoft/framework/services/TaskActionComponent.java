package cc.coopersoft.framework.services;

import cc.coopersoft.framework.data.BusinessInstance;

import java.util.List;

/** 任务完成组件
 * Created by cooper on 5/15/15.
 */
public interface TaskActionComponent<T extends BusinessInstance> {



    /** 完成的动做
     *  在建立业务或完成任务时调用
     *  valid 通过后调用
     *  执行队列前方组件失败后将中止运行
     *  EditAction 在完成本页时调用
     */
    void doAction(T business, boolean persistent) throws SubscribeFailException;

}
