package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.TaskValidComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.AccountDetailsEntity;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.model.HouseAccountEntity;

import java.util.ArrayList;
import java.util.List;

@SubscribeComponent
public class HouseAccountLockedHoldService implements TaskValidComponent<BusinessEntity> {



    @Override
    public List<ValidMessage> valid(BusinessEntity businessInstance) {

        List<ValidMessage> result = new ArrayList<>();
        String details = "";
        for(AccountDetailsEntity detail: businessInstance.getAccountDetails()){
            HouseAccountEntity account = detail.getHouseAccount();
            if (account != null){
                if (HouseAccountEntity.Status.LOCKED.equals(account.getStatus())){
                    if (!"".equals(details)){
                        details += "、";
                    }
                    details += account.getHouseCode();
                }
            }
        }
        if (!"".equals(details))
            result.add(new ValidMessage(ValidMessage.Level.FAIL,"房屋账户处于冻结状态！不可进行此操作","账户:" + details + "处于冻结状态"));
        return result;
    }

}
