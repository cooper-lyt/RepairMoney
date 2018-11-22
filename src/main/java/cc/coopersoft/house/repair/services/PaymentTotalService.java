package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.I18n;
import cc.coopersoft.house.repair.data.model.PaymentBusinessEntity;
import cc.coopersoft.house.repair.data.repository.PaymentRepository;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

public class PaymentTotalService {

    @Inject
    private PaymentRepository paymentRepository;

    @Inject
    private I18n i18n;


    public List<PaymentBusinessEntity> paymentDayReport(Date date){
        return paymentRepository.queryDatePayment(i18n.getDayBeginTime(date),i18n.getDayEndTime(date));
    }

}
