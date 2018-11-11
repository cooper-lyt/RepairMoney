package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.I18n;
import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.SubscribeFailException;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.house.repair.data.model.AccountDetailsEntity;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.model.HouseAccountEntity;
import cc.coopersoft.house.repair.data.model.RefundBusinessEntity;

import javax.inject.Inject;
import java.math.BigDecimal;

@SubscribeComponent
public class RefundCompleteService implements TaskActionComponent<BusinessEntity> {

    @Inject
    private I18n i18n;

    @Override
    public void doAction(BusinessEntity business, boolean persistent) throws SubscribeFailException {
        RefundBusinessEntity refundBusiness = business.getRefund();
        if (refundBusiness.getAccountDetails() != null){
            HouseAccountEntity account = refundBusiness.getAccountDetails().getHouseAccount();
            if (RefundBusinessEntity.Type.DESTROY.equals(refundBusiness.getType())){
                account.setBalance(BigDecimal.ZERO);
                account.setStatus(HouseAccountEntity.Status.DESTROY);
            }else if (RefundBusinessEntity.Type.OTHER.equals(refundBusiness.getType())){
                account.setBalance(account.getBalance().subtract(refundBusiness.getMoney()));
            }else{
                throw new SubscribeFailException("退款类型不正确！",refundBusiness.getType().name());
            }
            if (BigDecimal.ZERO.compareTo(account.getValidBalance()) > 0){
                throw new SubscribeFailException("退款金额不正确！","最大退款金额：" + i18n.currencyDisplay(account.getValidBalance()));
            }
            refundBusiness.getAccountDetails().setBalance(account.getBalance());
            refundBusiness.getAccountDetails().setMoney(refundBusiness.getMoney());
            refundBusiness.getAccountDetails().setStatus(AccountDetailsEntity.Status.REG);
        }else{
            //TODO other TYPE refund
        }

        business.setReg(true);
        business.setRegTime(refundBusiness.getOperationTime());


    }
}
