package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.*;
import cc.coopersoft.house.repair.data.repository.HouseRepository;
import cc.coopersoft.house.repair.data.repository.PaymentNoticeRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@SubscribeComponent
public class PaymentDeleteService implements TaskActionComponent {

    @Inject
    private PaymentNoticeRepository paymentNoticeRepository;

    @Inject
    private HouseRepository houseRepository;

    @Override
    public List<ValidMessage> valid(BusinessInstance businessInstance) {
        List<ValidMessage> result = new ArrayList<>();
        //TODO 判读是否入账 如果是撤回要冲账，如果是删除则不允许
        PaymentEntity payment = ((BusinessEntity)businessInstance).getPayment();

        return null;
    }


    @Override
    public void doAction(BusinessInstance businessInstance) {
        PaymentEntity payment = ((BusinessEntity)businessInstance).getPayment();

            boolean deleteHouse = false;
            if (payment.getPaymentNotice() == null) {
                if (!PaymentNoticeEntity.Source.CREATE.equals(payment.getPaymentNotice().getSource())) {
                    paymentNoticeRepository.remove(payment.getPaymentNotice());
                    deleteHouse = true;
                }
            }else{
                deleteHouse = true;
            }
            if (deleteHouse){
                for(PaymentBusinessEntity pbe: payment.getPaymentBusinesses()){
                    //houseRepository.remove(pbe.getHouse());
                }
            }

    }
}
