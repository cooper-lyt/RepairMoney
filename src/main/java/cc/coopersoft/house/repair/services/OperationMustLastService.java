package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.TaskValidComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.AccountDetailsEntity;
import cc.coopersoft.house.repair.data.model.BusinessEntity;

import java.util.ArrayList;
import java.util.List;

@SubscribeComponent
public class OperationMustLastService implements TaskValidComponent<BusinessEntity> {


    @Override
    public List<ValidMessage> valid(BusinessEntity businessInstance) {

        String msg = "";
        for(AccountDetailsEntity details: businessInstance.getAccountDetails()){
            if (!AccountDetailsEntity.Status.DELETED.equals(details.getStatus()) && (details.getHouseAccount() != null)) {
                List<AccountDetailsEntity> accountDetails =  details.getHouseAccount().getValidDetailsList();
                if (!accountDetails.isEmpty()){
                    if(!accountDetails.get(0).equals(details)){
                        if (!"".equals(msg)){
                            msg += "、";
                        }
                        msg += details.getHouseAccount().getHouseCode();
                    }
                }
            }
        }
        if (!"".equals(msg)){
            List<ValidMessage> result = new ArrayList<>(1);
            result.add(new ValidMessage(ValidMessage.Level.FAIL,"账户在此业务之后又发生了变动！不能执行些操作。", "变动的账户：" + msg));
            return result;
        }
        return new ArrayList<>(0);
    }
}
