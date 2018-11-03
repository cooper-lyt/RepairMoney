package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.TaskValidComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.model.PaymentNoticeEntity;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@SubscribeComponent
public class PaymentNoticeUseHoldService implements TaskValidComponent<BusinessEntity> {

    @Inject
    private PaymentService paymentService;

    @Override
    public List<ValidMessage> valid(BusinessEntity businessInstance) {
        PaymentNoticeEntity notice = businessInstance.getPayment().getPaymentNotice();
        if (notice != null){
            if (paymentService.isCharge(notice)){
                List<ValidMessage> result = new ArrayList<>(1);
                result.add(new ValidMessage(ValidMessage.Level.FAIL,"缴款通知单已被使用！同一通知单不能重复缴费。", "缴款通知单编号：" + notice.getId()));
                return result;
            }
        }
        return new ArrayList<>(0);
    }
}
