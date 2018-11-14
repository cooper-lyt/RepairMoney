package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.SubscribeFailException;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.model.RepairBusinessEntity;

import java.util.Date;

@SubscribeComponent
public class RepairCreateService implements TaskActionComponent<BusinessEntity> {


    @Override
    public void doAction(BusinessEntity business, boolean persistent) throws SubscribeFailException {
        business.setRepair(new RepairBusinessEntity(business));
        business.getRepair().setApplyDate(new Date());
        business.getRepair().setUrgent(false);
    }
}
