package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.SubscribeFailException;
import cc.coopersoft.framework.services.TaskEditSubscribeComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.BusinessEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SubscribeComponent
public class RepairMakeupInfoService implements TaskEditSubscribeComponent<BusinessEntity> {

    @Override
    public boolean init(BusinessEntity business) {
        return true;
    }

    @Override
    public void doCreate(BusinessEntity business) {

    }

    @Override
    public void doAction(BusinessEntity business, boolean persistent) throws SubscribeFailException {
        business.getRepair().setBudgetMoney(business.getRepair().getApplyMoney());
        business.getRepair().setCheckMoney(BigDecimal.ZERO);
        business.getRepair().setSuperviseMoney(BigDecimal.ZERO);
        business.getRepair().setUrgent(false);
        business.getRepair().setSaveMoney(BigDecimal.ZERO);
    }

    @Override
    public List<ValidMessage> valid(BusinessEntity business) {
        return new ArrayList<>(0);
    }
}
