package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.AccountDetailsEntity;
import cc.coopersoft.house.repair.data.model.HouseAccountEntity;
import cc.coopersoft.house.repair.data.repository.HouseAccountRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public abstract class HouseAccountDeleteService implements TaskActionComponent {

    @Inject
    private HouseAccountRepository houseAccountRepository;

    protected abstract List<AccountDetailsEntity> getOperations();

    @Override
    public List<ValidMessage> valid(BusinessInstance businessInstance) {
        String details = "";
        for (AccountDetailsEntity detailsEntity: getOperations() ){
            HouseAccountEntity account = detailsEntity.getHouseAccount();
            List<AccountDetailsEntity> allOperation = account.getAccountDetailsList();
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
            result.add(new ValidMessage(ValidMessage.Level.OFF,"此操作后房屋账户已发生变动，不可进此操作！","账户:" + details + " 发生变动。"));
        }
        return result;
    }

    @Override
    public void doAction(BusinessInstance businessInstance) {
        for (AccountDetailsEntity detailsEntity: getOperations() ) {
            HouseAccountEntity account = detailsEntity.getHouseAccount();
            if (account != null) {
                account.getAccountDetails().remove(detailsEntity);
                List<AccountDetailsEntity> allOperation = account.getAccountDetailsList();
                if (allOperation.isEmpty()){
                    houseAccountRepository.remove(account);
                }else{
                    AccountDetailsEntity last = allOperation.get(0);
                    account.setBalance(last.getBalance());
                    account.setHouse(last.getHouse());
                    houseAccountRepository.save(account);
                }

            }
        }
    }
}
