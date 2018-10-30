package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.AccountDetailsEntity;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.model.HouseAccountEntity;
import cc.coopersoft.house.repair.data.repository.HouseAccountRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@SubscribeComponent
public class HouseAccountDeleteService implements TaskActionComponent<BusinessEntity> {

    @Inject
    private HouseAccountRepository houseAccountRepository;


    @Override
    public List<ValidMessage> valid(BusinessEntity businessInstance) {
        String details = "";
        for (AccountDetailsEntity detailsEntity: businessInstance.getAccountDetails() ){
            HouseAccountEntity account = detailsEntity.getHouseAccount();
            List<AccountDetailsEntity> allOperation = account.getValidDetailsList();
            if (account != null && !allOperation.isEmpty()){
                if (!allOperation.get(0).equals(detailsEntity)){
                    if (!"".equals(details)){
                        details += "、";
                    }
                    details += account.getHouseCode() + "[" + account.getAccountNumber() + "]";
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
                account.getAccountDetails().remove(detailsEntity);
                List<AccountDetailsEntity> allOperation = account.getValidDetailsList();


                if (allOperation.isEmpty()){
                    if (account.getAllDetailList().isEmpty()){
                        houseAccountRepository.remove(account);
                    }else{
                        account.setStatus(HouseAccountEntity.Status.DESTROY);
                    }
                }else{
                    AccountDetailsEntity last = allOperation.get(0);
                    account.setBalance(last.getBalance());
                    account.setHouse(last.getHouse());
                }

            }
        }
    }
}
