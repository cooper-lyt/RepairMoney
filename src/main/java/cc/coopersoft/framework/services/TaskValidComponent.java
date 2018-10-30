package cc.coopersoft.framework.services;

import cc.coopersoft.framework.data.BusinessInstance;

import java.util.List;

public interface TaskValidComponent<T extends BusinessInstance> {

    /** 验证数据 添加消息
     *
     */
    List<ValidMessage> valid(T businessInstance);
}
