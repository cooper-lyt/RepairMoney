package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.AccountDetailsEntity;
import cc.coopersoft.house.repair.data.model.BankAccountDetailsEntity;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.repository.BankAccountDetailsRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@SubscribeComponent
public class AccountOperationRevokeService implements TaskActionComponent<BusinessEntity> {

    @Inject
    private BankAccountDetailsRepository bankAccountDetailsRepository;


    @Override
    public List<ValidMessage> valid(BusinessEntity businessInstance) {
        return new ArrayList<>(0);
    }


    @Override
    public void doAction(BusinessEntity businessInstance) {
        for(AccountDetailsEntity details: businessInstance.getAccountDetails()){
            details.setStatus(AccountDetailsEntity.Status.DELETED);
        }
        for(BankAccountDetailsEntity details: businessInstance.getBankAccountDetails()) {
            //TODO ADD any bank account business
            if (businessInstance.getPayment() != null){
                businessInstance.getPayment().setBankAccountDetails(null);
            }
            bankAccountDetailsRepository.remove(details);
        }
    }
}
