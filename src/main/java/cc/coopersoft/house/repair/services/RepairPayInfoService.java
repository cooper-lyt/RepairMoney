package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.SubscribeFailException;
import cc.coopersoft.framework.services.TaskEditSubscribeComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.AccountDetailsEntity;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.model.HouseAccountEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SubscribeComponent
public class RepairPayInfoService implements TaskEditSubscribeComponent<BusinessEntity> {



    @Override
    public boolean init(BusinessEntity business) {
        return true;
    }

    @Override
    public void doCreate(BusinessEntity business) {

    }

    @Override
    public void doAction(BusinessEntity business, boolean persistent) throws SubscribeFailException {

    }

    @Override
    public List<ValidMessage> valid(BusinessEntity business) {
        List<ValidMessage> result = new ArrayList<>();
        String msg = "";
        for(AccountDetailsEntity details: business.getAccountDetails()){
            if (AccountDetailsEntity.Status.RUNNING.equals(details.getStatus())){
                HouseAccountEntity account = details.getHouseAccount();
                if (account.getValidBalance().subtract(details.getMoney()).compareTo(BigDecimal.ZERO) < 0){
                    if (!"".equals(msg)){
                        msg += "、";
                    }
                    msg += account.getHouseCode();
                }
            }
        }
        if (!"".equals(msg)){
            result.add(new ValidMessage(ValidMessage.Level.OFF,"账户余额不足!" , "账户:" +  msg + "余额不足"));
        }
        return result;
    }
}
