package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.services.TaskEditSubscribeComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SubscribeComponent
public class PaymentInfoService extends PaymentAccountValidService implements TaskEditSubscribeComponent {



    @Override
    public void init(BusinessInstance businessInstance) {
        PaymentEntity payment = ((BusinessEntity) businessInstance).getPayment();
        if (payment.getOperationDate() == null){
            payment.setOperationDate(new Date());
        }
    }

    @Override
    public boolean assertion(BusinessInstance businessInstance) {
        PaymentEntity payment = ((BusinessEntity) businessInstance).getPayment();
        return  ((PaymentType.CASH.equals(payment.getPaymentType()) && (payment.getBankAccountDetails() == null)) ||
                (PaymentType.BANK.equals(payment.getPaymentType()) && payment.getBankAccountDetails() != null));
    }

    @Override
    public void persistent(BusinessInstance businessInstance) {
        // do nothing
    }

    @Override
    public List<ValidMessage> valid( BusinessInstance businessInstance) {
        PaymentEntity payment = ((BusinessEntity) businessInstance).getPayment();

        List<ValidMessage> result = new ArrayList<>();
        if (PaymentType.BANK.equals(payment.getPaymentType()) &&
                (payment.getBankAccountDetails() == null)){

            result.add(new ValidMessage(ValidMessage.Level.OFF,"请选择交款银行！","交款方式为转账时必须选择交款银行！"));
        }
        List<String> houseCodes = new ArrayList<>();
        for(PaymentBusinessEntity pb: payment.getPaymentBusinesses()){
            houseCodes.add(pb.getAccountDetails().getHouse().getHouseCode());
        }


        result.addAll(super.valid(businessInstance));
        return result;
    }

    @Override
    public void doAction(BusinessInstance businessInstance) {
        PaymentEntity payment = ((BusinessEntity) businessInstance).getPayment();
        if (PaymentType.BANK.equals(payment.getPaymentType()) && !BusinessInstance.Source.OUT_SIDE.equals(businessInstance.getSource())){
            payment.getBankAccountDetails().setOperationTime(payment.getOperationDate());
        }
        for(PaymentBusinessEntity paymentBusinessEntity: payment.getPaymentBusinesses()){
            paymentBusinessEntity.getAccountDetails().setOperationTime(payment.getOperationDate());
        }
    }
}
