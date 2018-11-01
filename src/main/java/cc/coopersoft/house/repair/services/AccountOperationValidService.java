package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.I18n;
import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.TaskValidComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.AccountDetailsEntity;
import cc.coopersoft.house.repair.data.model.BusinessEntity;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SubscribeComponent
public class AccountOperationValidService implements TaskValidComponent<BusinessEntity> {

    @Inject
    private HouseAccountService houseAccountService;

    @Inject
    private I18n i18n;

    protected Date getOperationTime(BusinessEntity businessInstance){
        return businessInstance.getRegTime();
    }


    @Override
    public List<ValidMessage> valid(BusinessEntity businessInstance) {

        List<String> houseCodes = new ArrayList<>();
        for(AccountDetailsEntity details: businessInstance.getAccountDetails()){
            houseCodes.add(details.getHouse().getHouseCode());
        }


        if (!houseAccountService.validTime(getOperationTime(businessInstance),houseCodes)){
            List<ValidMessage> result = new ArrayList<>(1);
            result.add(new ValidMessage(ValidMessage.Level.OFF,"操作时间必须在所有房屋账户变动之后！","最后变动时间：" + i18n.datetimeDisplay(houseAccountService.lastChangeTime(houseCodes))));
            return result;
        }


        return new ArrayList<>(0);
    }

}
