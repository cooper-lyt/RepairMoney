package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.framework.services.TaskValidComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.BusinessEntity;

import java.util.ArrayList;
import java.util.List;

@SubscribeComponent
public class OutsideBusinessHoldService implements TaskValidComponent<BusinessEntity> {


    @Override
    public List<ValidMessage> valid(BusinessEntity businessInstance) {
        List<ValidMessage> result = new ArrayList<>();
        if (BusinessInstance.Source.OUT_SIDE.equals(businessInstance.getSource())){
            result.add(new ValidMessage(ValidMessage.Level.OFF,"此业务为外部业务不可进行此操作！","此业务为外部业务不可进行此操作！"));
        }
        return result;
    }

}
