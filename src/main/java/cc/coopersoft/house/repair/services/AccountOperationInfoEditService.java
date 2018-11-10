package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.services.SubscribeFailException;
import cc.coopersoft.framework.services.TaskEditSubscribeComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.AccountMoneyOperation;
import cc.coopersoft.house.repair.data.model.AccountDetailsEntity;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.PaymentType;

import java.util.Date;
import java.util.List;

public abstract class AccountOperationInfoEditService<T extends AccountMoneyOperation> extends AccountOperationValidService implements TaskEditSubscribeComponent<BusinessEntity> {

    protected abstract T getOperationBusiness(BusinessEntity businessInstance);

    @Override
    protected Date getOperationTime(AccountDetailsEntity details){
        return details.getBusiness().getPayment().getOperationTime();
    }

    @Override
    public List<ValidMessage> valid(BusinessEntity businessInstance) {
        T operation = getOperationBusiness(businessInstance);

        List<ValidMessage> result = super.valid(businessInstance);
        if (PaymentType.BANK.equals(operation.getPaymentType()) &&
                (operation.getBankAccountDetails() == null)){

            result.add(new ValidMessage(ValidMessage.Level.OFF,"请选择交款银行！","支付方式为转账时必须选择交款银行！"));
        }

        return result;
    }

    @Override
    public void doAction(BusinessEntity business, boolean persistent) throws SubscribeFailException {
        T operation = getOperationBusiness(business);
        if (PaymentType.BANK.equals(operation.getPaymentType()) && !BusinessInstance.Source.OUT_SIDE.equals(business.getSource())){
            operation.getBankAccountDetails().setOperationTime(operation.getOperationTime());
        }


        for(AccountDetailsEntity accountDetailsEntity: business.getAccountDetails()){
            accountDetailsEntity.setOperationTime(operation.getOperationTime());
        }
    }
}
