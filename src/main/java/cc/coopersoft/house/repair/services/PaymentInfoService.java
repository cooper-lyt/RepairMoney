package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.services.SubscribeFailException;
import cc.coopersoft.framework.services.TaskEditSubscribeComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.*;

import java.util.Date;
import java.util.List;

@SubscribeComponent
public class PaymentInfoService extends AccountOperationValidService implements TaskEditSubscribeComponent<BusinessEntity> {



    @Override
    public void init(BusinessEntity businessInstance) {
        PaymentEntity payment = businessInstance.getPayment();
        if (payment.getOperationDate() == null){
            payment.setOperationDate(new Date());
        }
    }

    @Override
    public void doCreate(BusinessEntity business) {
    }


    @Override
    protected Date getOperationTime(BusinessEntity businessInstance){
        return businessInstance.getPayment().getOperationDate();
    }

    @Override
    public List<ValidMessage> valid(BusinessEntity businessInstance) {
        PaymentEntity payment = businessInstance.getPayment();

        List<ValidMessage> result = super.valid(businessInstance);
        if (PaymentType.BANK.equals(payment.getPaymentType()) &&
                (payment.getBankAccountDetails() == null)){

            result.add(new ValidMessage(ValidMessage.Level.OFF,"请选择交款银行！","交款方式为转账时必须选择交款银行！"));
        }

        return result;
    }

    @Override
    public void doAction(BusinessEntity businessInstance,boolean persistent) {
        PaymentEntity payment = businessInstance.getPayment();
        if (PaymentType.BANK.equals(payment.getPaymentType()) && !BusinessInstance.Source.OUT_SIDE.equals(businessInstance.getSource())){
            payment.getBankAccountDetails().setOperationTime(payment.getOperationDate());
        }else {
            businessInstance.setRegTime(payment.getOperationDate());
        }
        for(PaymentBusinessEntity paymentBusinessEntity: payment.getPaymentBusinesses()){
            paymentBusinessEntity.getAccountDetails().setOperationTime(payment.getOperationDate());

        }
    }
}
