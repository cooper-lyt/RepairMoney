package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.SubscribeFailException;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.framework.tools.EnumHelper;
import cc.coopersoft.house.repair.data.model.AccountDetailsEntity;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.model.HouseAccountEntity;

import javax.inject.Inject;
import java.math.BigDecimal;

@SubscribeComponent
public class RepairPayService implements TaskActionComponent<BusinessEntity> {

    @Inject
    private EnumHelper enumHelper;

    @Override
    public void doAction(BusinessEntity business, boolean persistent) throws SubscribeFailException {

        for(AccountDetailsEntity details: business.getAccountDetails()){
            if (AccountDetailsEntity.Status.RUNNING.equals(details.getStatus())){
                HouseAccountEntity account = details.getHouseAccount();
                account.setBalance(account.getBalance().subtract(details.getMoney()));
                if (!HouseAccountEntity.Status.NORMAL.equals(account.getStatus())){
                    throw new SubscribeFailException("账户状态不允许支付！" , "账户:" + account.getHouseCode() + "状态为" + enumHelper.getLabel(account.getStatus()));
                }
                if (account.getBalance().compareTo(BigDecimal.ZERO) < 0 ){
                    throw new SubscribeFailException("账户余额不足!" , "账户:" +  account.getHouseCode() + "余额不足.");
                }
                details.setStatus(AccountDetailsEntity.Status.REG);
                details.setBalance(account.getBalance());
            }
        }

    }
}
