package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.AccountDetailsEntity;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.model.HouseAccountEntity;
import cc.coopersoft.house.repair.data.repository.HouseAccountRepository;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@SubscribeComponent
public class HouseAccountDeleteService implements TaskActionComponent<BusinessEntity> {

    @Inject
    private Logger logger;

    @Inject
    private HouseAccountRepository houseAccountRepository;


    @Override
    public List<ValidMessage> valid(BusinessEntity businessInstance) {
        String details = "";
        for (AccountDetailsEntity detailsEntity: businessInstance.getAccountDetails() ){
            HouseAccountEntity account = detailsEntity.getHouseAccount();
            if (account != null) {
                List<AccountDetailsEntity> allOperation = account.getValidDetailsList();
                if (account != null && !allOperation.isEmpty()) {
                    if (!allOperation.get(0).equals(detailsEntity)) {
                        if (!"".equals(details)) {
                            details += "、";
                        }
                        details += account.getHouseCode();
                    }
                }
            }

        }
        List<ValidMessage> result = new ArrayList<>();
        if (!"".equals(details)){
            result.add(new ValidMessage(ValidMessage.Level.FAIL,"此操作后房屋账户已发生变动，不可进此操作！","账户:" + details + " 发生变动。"));
        }
        return result;
    }

    @Override
    public void doAction(BusinessEntity businessInstance) {
        for (AccountDetailsEntity detailsEntity: businessInstance.getAccountDetails() ) {
            HouseAccountEntity account = detailsEntity.getHouseAccount();
            if (account != null) {

                List<AccountDetailsEntity> validOperation = account.getValidDetailsList();
                validOperation.removeAll(businessInstance.getAccountDetails());
                if (validOperation.isEmpty()){
                    List<AccountDetailsEntity> allOperation = account.getAllDetailList();
                    allOperation.removeAll(businessInstance.getAccountDetails());
                    if (allOperation.isEmpty()){
                        //detailsEntity.setHouseAccount(null);
                        for(AccountDetailsEntity ad:account.getAccountDetails()) {
                            ad.setHouseAccount(null);
                        }
                        houseAccountRepository.remove(account);
                        logger.config("remove account: " + account);
                    }else{
                        account.setBalance(BigDecimal.ZERO);
                        account.setStatus(HouseAccountEntity.Status.DESTROY);
                    }
                }else{
                    AccountDetailsEntity last = validOperation.get(0);
                    account.setBalance(last.getBalance());
                    account.setHouse(last.getHouse());
                }

            }
        }
    }
}
