package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.TaskEditSubscribeComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.model.PaymentBusinessEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SubscribeComponent
public class PaymentMoneyService implements TaskEditSubscribeComponent<BusinessEntity> {

    @Override
    public void init(BusinessEntity businessInstance) {

    }

    @Override
    public void persistent(BusinessEntity businessInstance) {

    }

    @Override
    public void doAction(BusinessEntity businessInstance) {
        if (businessInstance.getPayment().getPaymentNotice() == null) {
            BigDecimal mustMoney = BigDecimal.ZERO;
            BigDecimal money = BigDecimal.ZERO;
            for (PaymentBusinessEntity paymentItem : businessInstance.getPayment().getPaymentBusinesses()) {
                mustMoney = mustMoney.add(paymentItem.getMustMoney());
                money = money.add(paymentItem.getMoney());
                paymentItem.getAccountDetails().setMoney(paymentItem.getMoney());
            }
            businessInstance.getPayment().setMustMoney(mustMoney);
            businessInstance.getPayment().setMoney(money);
        }

    }

    @Override
    public List<ValidMessage> valid(BusinessEntity businessInstance) {
        return new ArrayList<>(0);
    }
}
