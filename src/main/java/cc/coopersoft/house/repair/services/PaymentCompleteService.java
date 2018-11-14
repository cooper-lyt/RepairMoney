package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.framework.tools.UUIDGenerator;
import cc.coopersoft.house.repair.data.model.PaymentEntity;
import cc.coopersoft.house.repair.data.model.*;
import cc.coopersoft.house.repair.data.repository.HouseAccountRepository;

import javax.inject.Inject;
import java.math.BigDecimal;

@SubscribeComponent
public class PaymentCompleteService  implements TaskActionComponent<BusinessEntity> {

    @Inject
    private HouseAccountService houseAccountService;


    @Override
    public void doAction(BusinessEntity businessInstance,boolean persistent) {
        PaymentEntity paymentEntity =businessInstance.getPayment();
        for(PaymentBusinessEntity pb: paymentEntity.getPaymentBusinesses()){
            HouseAccountEntity account = houseAccountService.getAccountByHouseCode(pb.getAccountDetails().getHouse().getHouseCode());
            if (account == null){
                account = new HouseAccountEntity();
                account.setAccountNumber(UUIDGenerator.getUUID());
                account.setFrozen(BigDecimal.ZERO);
                account.setBalance(BigDecimal.ZERO);
                account.setProductDate(paymentEntity.getOperationTime());
                account.setCreateTime(paymentEntity.getOperationTime());
                account.setStatus(HouseAccountEntity.Status.NORMAL);
                account.setMustMoney(pb.getMustMoney());
                account.setHouseCode(pb.getAccountDetails().getHouse().getHouseCode());
            }
            if (PaymentBusinessEntity.Type.FIRST.equals(pb.getType())){
                account.setMustMoney(pb.getMustMoney());
                account.setProductDate(paymentEntity.getOperationTime());
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
        businessInstance.setRegTime(paymentEntity.getOperationTime());
    }

}
