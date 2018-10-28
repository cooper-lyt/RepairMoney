package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.I18n;
import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.framework.tools.DataHelper;
import cc.coopersoft.framework.tools.HttpJsonDataGet;
import cc.coopersoft.house.repair.data.model.*;
import cc.coopersoft.house.repair.data.repository.HouseAccountRepository;
import cc.coopersoft.house.repair.data.repository.PaymentNoticeRepository;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ConversationScoped
@SubscribeComponent
public class PaymentService implements TaskActionComponent,java.io.Serializable {

    private enum CreateSource{
        NOTICE
    }

    @Inject
    private PaymentNoticeRepository paymentNoticeRepository;

    @Inject
    private HouseAccountRepository houseAccountRepository;

    @Inject
    private RemoteDataService remoteDataService;

    @Inject
    private I18n i18n;


    public PaymentNoticeEntity getPaymentNotice(String noticeNumber) throws HttpJsonDataGet.ApiServerException {
        PaymentNoticeEntity result = paymentNoticeRepository.findBy(noticeNumber.trim());
        if (result == null){
            result = remoteDataService.getPaymentNotice(noticeNumber.trim());

        }
        return result;
    }

    public boolean isCharge(PaymentNoticeEntity paymentNotice){
        for (PaymentEntity entity: paymentNotice.getPayments()){
            if (!BusinessEntity.BusinessStatus.ABORT.equals(entity.getBusiness().getStatus())){
                return true;
            }
        }
        return false;
    }

    public void createBusinessByNotice(PaymentNoticeEntity paymentNotice){
        createSource = CreateSource.NOTICE;
        this.paymentNotice = paymentNotice;
    }

    private CreateSource createSource;

    private PaymentNoticeEntity paymentNotice;

    @Override
    public List<ValidMessage> valid(BusinessInstance businessInstance) {
        if (createSource == null){
            throw new IllegalArgumentException("createSource is null");
        }
        List<ValidMessage> result = new ArrayList<>();
        switch (createSource){
            case NOTICE:
                if (paymentNotice == null){
                    throw new IllegalArgumentException("payment Notice not found!");
                }
                if (isCharge(paymentNotice)){
                    result.add(new ValidMessage(ValidMessage.Level.OFF,"payment_notice_is_using","payment_notice_is_using",paymentNotice.getId()));
                }else {
                    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(i18n.getLocale());

                    result.add(new ValidMessage(ValidMessage.Level.INFO, "payment_create_by_notice", "payment_create_by_notice",
                            paymentNotice.getId(),currencyFormat.format(paymentNotice.getMustMoney()),currencyFormat.format(paymentNotice.getMoney())));
                }

                break;
        }
        return result;
    }

    @Override
    public void doAction(BusinessInstance businessInstance) {

        BusinessEntity result = (BusinessEntity) businessInstance;
        switch (createSource){

            case NOTICE:
                result.setPayment(new PaymentEntity(result));
                result.getPayment().setPaymentNotice(paymentNotice);
                result.getPayment().setMustMoney(paymentNotice.getMustMoney());
                result.getPayment().setMoney(paymentNotice.getMoney());

                int itemCount = paymentNotice.getNoticeItems().size();
                int i = 0;
                for(PaymentNoticeItemEntity item: paymentNotice.getNoticeItems()){
                    if(DataHelper.empty(result.getPayment().getSectionCode())){
                        result.getPayment().setSectionCode(item.getHouse().getSectionCode());
                        result.getPayment().setSectionName(item.getHouse().getSectionName());
                    }
                    HouseAccountEntity houseAccountEntity = houseAccountRepository.findOptionalByHouseCode(item.getHouse().getHouseCode());
                    PaymentBusinessEntity.Type type = (houseAccountEntity == null) ? PaymentBusinessEntity.Type.FIRST : PaymentBusinessEntity.Type.ADD;
                    String id = String.valueOf(i++);
                    while (id.length() < itemCount){
                        id = '0' + id;
                    }
                    id = result.getId() + id;
                    PaymentBusinessEntity paymentBusinessEntity = new PaymentBusinessEntity(
                            id,
                            PaymentBusinessEntity.Status.PROCESS,
                            item.getMoney(),
                            item.getMustMoney(),
                            item.getCalcDetails(),
                            type,
                            result.getPayment(),
                            item.getHouse()
                    );
                    item.getHouse().setDataTime(new Date());
                    result.getPayment().getPaymentBusinesses().add(paymentBusinessEntity);
                }



        }
    }
}
