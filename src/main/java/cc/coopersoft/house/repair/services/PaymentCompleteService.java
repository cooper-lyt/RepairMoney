package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.framework.tools.UUIDGenerator;
import cc.coopersoft.house.repair.data.model.*;
import cc.coopersoft.house.repair.data.repository.HouseAccountRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SubscribeComponent
@RequestScoped
public class PaymentCompleteService  implements TaskActionComponent<BusinessEntity> {

    @Inject
    private HouseAccountRepository houseAccountRepository;


    @Override
    public void doAction(BusinessEntity businessInstance) {
       PaymentEntity paymentEntity =businessInstance.getPayment();
       for(PaymentBusinessEntity pb: paymentEntity.getPaymentBusinesses()){
           HouseAccountEntity account = houseAccountRepository.findOptionalByHouseCode(pb.getAccountDetails().getHouse().getHouseCode());
           if (account == null){
               account = new HouseAccountEntity();
               account.setAccountNumber(UUIDGenerator.getUUID());
               account.setFrozen(BigDecimal.ZERO);
               account.setBalance(BigDecimal.ZERO);
               account.setProductDate(paymentEntity.getOperationDate());
               account.setCreateTime(paymentEntity.getOperationDate());
               account.setStatus(HouseAccountEntity.Status.NORMAL);
               //account.setMustMoney(pb.getMustMoney());
               account.setHouseCode(pb.getAccountDetails().getHouse().getHouseCode());
           }
           if ((account == null)  || PaymentBusinessEntity.Type.FIRST.equals(pb.getType())){
               account.setMustMoney(pb.getMustMoney());
           }
           pb.getAccountDetails().setStatus(AccountDetailsEntity.Status.REG);
           pb.getAccountDetails().setBalance(account.getBalance().add(pb.getAccountDetails().getMoney()));

           account.setBalance(pb.getAccountDetails().getBalance());
           account.setHouse(pb.getAccountDetails().getHouse());
           account.getAccountDetails().add(pb.getAccountDetails());
           pb.getAccountDetails().setHouseAccount(account);


       }

       businessInstance.setReg(true);

    }

    @Override
    public List<ValidMessage> valid(BusinessEntity businessInstance) {
        return new ArrayList<>(0);
    }
}
