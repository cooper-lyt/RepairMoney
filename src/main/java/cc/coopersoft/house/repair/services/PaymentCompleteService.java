package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.SubscribeFailException;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.framework.tools.UUIDGenerator;
import cc.coopersoft.house.repair.data.model.*;
import cc.coopersoft.house.repair.data.repository.HouseAccountRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.math.BigDecimal;

@SubscribeComponent
@RequestScoped
public class PaymentCompleteService  implements TaskActionComponent<BusinessEntity> {

    @Inject
    private HouseAccountRepository houseAccountRepository;


    @Override
    public boolean check(BusinessEntity businessInstance, boolean persistent) throws SubscribeFailException {
        return true;
    }

    @Override
    public void doAction(BusinessEntity businessInstance,boolean persistent) {
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
               account.setMustMoney(pb.getMustMoney());
               account.setHouseCode(pb.getAccountDetails().getHouse().getHouseCode());
           }
           if (PaymentBusinessEntity.Type.FIRST.equals(pb.getType())){
               account.setMustMoney(pb.getMustMoney());
               if (HouseAccountEntity.Status.DESTROY.equals(account.getStatus())){
                   account.setStatus(HouseAccountEntity.Status.NORMAL);
               }
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

}
