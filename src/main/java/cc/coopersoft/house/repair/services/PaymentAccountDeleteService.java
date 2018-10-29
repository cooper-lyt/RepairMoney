package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.BusinessEntity;

import java.util.List;

@SubscribeComponent
public class PaymentAccountDeleteService implements TaskActionComponent<BusinessEntity> {
    @Override
    public List<ValidMessage> valid(BusinessEntity businessInstance) {
        return null;
    }

    @Override
    public void doAction(BusinessEntity businessInstance) {

    }
}
