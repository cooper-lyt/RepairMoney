package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.framework.tools.UUIDGenerator;
import cc.coopersoft.house.repair.data.model.*;
import cc.coopersoft.house.repair.data.repository.HouseAccountRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SubscribeComponent
@RequestScoped
public class PaymentCompleteService implements TaskActionComponent {

    @Inject
    private HouseAccountRepository houseAccountRepository;


    @Override
    public List<ValidMessage> valid(BusinessInstance businessInstance) {
        return new ArrayList<>(0);
    }

    @Override
    public void doAction(BusinessInstance businessInstance) {
       PaymentEntity paymentEntity =((BusinessEntity) businessInstance).getPayment();
       for(PaymentBusinessEntity pb: paymentEntity.getPaymentBusinesses()){
           HouseAccountEntity account = houseAccountRepository.findOptionalByHouseCode(pb.getHouse().getHouseCode());
           if (account == null){
               account = new HouseAccountEntity();
               account.setAccountNumber(UUIDGenerator.getUUID());
               account.setBalance(BigDecimal.ZERO);
               account.setFrozen(BigDecimal.ZERO);
               account.setProductDate(paymentEntity.getOperationDate());
               account.setCreateTime(paymentEntity.getOperationDate());
               account.setStatus(HouseAccountEntity.Status.NORMAL);
               account.setMustMoney(pb.getMustMoney());
               account.setHouseCode(pb.getHouse().getHouseCode());
           }
           account.setBalance(account.getBalance().add(pb.getMoney()));
           account.setHouse(pb.getHouse());
           account.getHouse().setDataTime(new Date());

           AccountDetailsEntity accountDetailsEntity = new AccountDetailsEntity(UUIDGenerator.getUUID(),AccountDetailsEntity.Type.PAYMENT);
           accountDetailsEntity.setOperationTime(paymentEntity.getOperationDate());
           accountDetailsEntity.setMoney(pb.getMoney());
           accountDetailsEntity.setBalance(account.getBalance());
           //TODO accountDetailsEntity.setDescription();
           accountDetailsEntity.setHouseAccount(account);
           account.getAccountDetails().add(accountDetailsEntity);
           pb.setAccountDetails(accountDetailsEntity);
           pb.setStatus(PaymentBusinessEntity.Status.NORMAL);
       }


       businessInstance.setReg(true);
       businessInstance.setRegTime(paymentEntity.getOperationDate());



    }
}
