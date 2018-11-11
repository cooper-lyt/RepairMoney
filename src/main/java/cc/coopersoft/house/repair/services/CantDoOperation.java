package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.TaskValidComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.BusinessEntity;

import java.util.ArrayList;
import java.util.List;

@SubscribeComponent
public class CantDoOperation implements TaskValidComponent<BusinessEntity> {
    @Override
    public List<ValidMessage> valid(BusinessEntity business) {
        List<ValidMessage> result = new ArrayList<>(1);
        result.add(new ValidMessage(ValidMessage.Level.OFF,"禁止此操作！","不支持进行此操作。"));
        return result;
    }
}
