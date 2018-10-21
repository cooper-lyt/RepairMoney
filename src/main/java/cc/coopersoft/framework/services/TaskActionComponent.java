package cc.coopersoft.framework.services;

import cc.coopersoft.framework.data.BusinessInstance;

import java.util.List;

/** 任务完成组件
 * Created by cooper on 5/15/15.
 */
public interface TaskActionComponent {

    /**验证数据 添加消息
     * 在确认面加载时执行
     */
    List<ValidMessage> valid(BusinessInstance businessInstance);


    /** 完成的动做
     *  在建立业务或完成任务时调用
     *  valid 通过后调用
     *  执行队列前方组件失败后将中止运行
     */
    void doAction(BusinessInstance businessInstance);

}
