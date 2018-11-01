package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.model.PaymentNoticeEntity;
import cc.coopersoft.house.repair.data.repository.PaymentNoticeRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@SubscribeComponent
public class PaymentNoticeDeleteService implements TaskActionComponent<BusinessEntity> {


    @Inject
    private PaymentNoticeRepository paymentNoticeRepository;

    @Override
    public void doAction(BusinessEntity businessInstance) {
        PaymentNoticeEntity notice = businessInstance.getPayment().getPaymentNotice();
        if ((notice != null) && !PaymentNoticeEntity.Source.CREATE.equals(notice.getSource())){
            paymentNoticeRepository.remove(notice);
        }
    }

    @Override
    public List<ValidMessage> valid(BusinessEntity businessInstance) {

        return new ArrayList<>(0);
    }
}
