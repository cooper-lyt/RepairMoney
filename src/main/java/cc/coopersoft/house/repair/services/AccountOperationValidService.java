package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.I18n;
import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.TaskValidComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.AccountDetailsEntity;
import cc.coopersoft.house.repair.data.model.BusinessEntity;

import javax.inject.Inject;
import java.util.*;

@SubscribeComponent
public class AccountOperationValidService implements TaskValidComponent<BusinessEntity> {

    @Inject
    private HouseAccountService houseAccountService;

    @Inject
    private I18n i18n;

    protected Date getOperationTime(AccountDetailsEntity details){
        return details.getOperationTime();
    }


    @Override
    public List<ValidMessage> valid(BusinessEntity businessInstance) {

        String detailsStr = "";
        for(AccountDetailsEntity details: businessInstance.getAccountDetails()){
            String houseCode = details.getHouse().getHouseCode();
            Date last = houseAccountService.lastChangeTime(houseCode);

            if ((last != null) && last.after(getOperationTime(details))){
               if (!"".equals(detailsStr)){
                   detailsStr += "、";
               }
               detailsStr += "房屋【" + houseCode + "】最后操作时间：" + i18n.datetimeDisplay(last);
            }

        }

        if (!"".equals(detailsStr)){
            List<ValidMessage> result = new ArrayList<>(1);
            result.add(new ValidMessage(ValidMessage.Level.OFF,"操作时间必须在所有房屋账户变动之后！",detailsStr));
            return result;
        }


        return new ArrayList<>(0);
    }

}
