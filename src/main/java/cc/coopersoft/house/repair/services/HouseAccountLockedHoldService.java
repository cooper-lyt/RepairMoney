package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.AccountDetailsEntity;
import cc.coopersoft.house.repair.data.model.BusinessEntity;

import java.util.List;

@SubscribeComponent
public class HouseAccountLockedHoldService implements TaskActionComponent<BusinessEntity> {


    @Override
    public List<ValidMessage> valid(BusinessEntity businessInstance) {
        for(AccountDetailsEntity detail: businessInstance.getAccountDetails());
        return null;
    }

    @Override
    public void doAction(BusinessEntity businessInstance) {

    }
}
