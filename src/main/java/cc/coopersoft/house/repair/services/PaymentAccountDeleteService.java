package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.framework.services.ValidMessage;

import java.util.List;

@SubscribeComponent
public class PaymentAccountDeleteService implements TaskActionComponent {
    @Override
    public List<ValidMessage> valid(BusinessInstance businessInstance) {
        return null;
    }

    @Override
    public void doAction(BusinessInstance businessInstance) {

    }
}
