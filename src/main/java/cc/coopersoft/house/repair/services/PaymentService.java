package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.I18n;
import cc.coopersoft.framework.tools.DataHelper;
import cc.coopersoft.framework.tools.HttpJsonDataGet;
import cc.coopersoft.framework.tools.UUIDGenerator;
import cc.coopersoft.house.repair.data.model.*;
import cc.coopersoft.house.repair.data.repository.HouseAccountRepository;
import cc.coopersoft.house.repair.data.repository.PaymentNoticeRepository;

import javax.inject.Inject;
import java.util.Date;

public class PaymentService implements java.io.Serializable {

    @Inject
    private PaymentNoticeRepository paymentNoticeRepository;

    @Inject
    private HouseAccountRepository houseAccountRepository;

    @Inject
    private RemoteDataService remoteDataService;


    public PaymentNoticeEntity getPaymentNotice(String noticeNumber) throws HttpJsonDataGet.ApiServerException {
        PaymentNoticeEntity result = paymentNoticeRepository.findBy(noticeNumber.trim());
        if (result == null){
            result = remoteDataService.getPaymentNotice(noticeNumber);

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

    public void createBusinessByHouse(BusinessEntity business, HouseEntity house){

    }

    public void createBusinessByNotice(BusinessEntity business, PaymentNoticeEntity paymentNotice){
        business.setPayment(new PaymentEntity(business));
        business.getPayment().setPaymentNotice(paymentNotice);
        business.getPayment().setMustMoney(paymentNotice.getMustMoney());
        business.getPayment().setMoney(paymentNotice.getMoney());

        int itemCount = paymentNotice.getNoticeItems().size();
        int i = 0;
        for(PaymentNoticeItemEntity item: paymentNotice.getNoticeItems()){
            if(DataHelper.empty(business.getPayment().getSectionCode())){
                business.getPayment().setSectionCode(item.getHouse().getSectionCode());
                business.getPayment().setSectionName(item.getHouse().getSectionName());
            }
            HouseAccountEntity houseAccountEntity = houseAccountRepository.findOptionalByHouseCode(item.getHouse().getHouseCode());
            PaymentBusinessEntity.Type type = ((houseAccountEntity == null) || (HouseAccountEntity.Status.DESTROY.equals(houseAccountEntity.getStatus()))) ? PaymentBusinessEntity.Type.FIRST : PaymentBusinessEntity.Type.ADD;
            String id = String.valueOf(i++);
            while (id.length() < itemCount){
                id = '0' + id;
            }
            id = business.getId() + id;
            PaymentBusinessEntity paymentBusinessEntity = new PaymentBusinessEntity(
                    id,
                    item.getMoney(),
                    item.getMustMoney(),
                    item.getCalcDetails(),
                    type,
                    business.getPayment()
            );
            item.getHouse().setDataTime(new Date());
            business.getPayment().getPaymentBusinesses().add(paymentBusinessEntity);

            AccountDetailsEntity accountDetailsEntity =
                    new AccountDetailsEntity(business,AccountOperationDirection.IN,UUIDGenerator.getUUID());
            accountDetailsEntity.setStatus(AccountDetailsEntity.Status.RUNNING);
            accountDetailsEntity.setHouse(item.getHouse());
            accountDetailsEntity.setMoney(item.getMoney());
            business.getAccountDetails().add(accountDetailsEntity);
            paymentBusinessEntity.setAccountDetails(accountDetailsEntity);

        }
    }



//    public List<ValidMessage> valid(BusinessEntity businessInstance) {
//        if (createSource == null){
//            throw new IllegalArgumentException("createSource is null");
//        }
//        List<ValidMessage> result = new ArrayList<>();
//        switch (createSource){
//            case NOTICE:
//                if (paymentNotice == null){
//                    throw new IllegalArgumentException("payment Notice not found!");
//                }
//                if (isCharge(paymentNotice)){
//                    result.add(new ValidMessage(ValidMessage.Level.OFF,"payment_notice_is_using","payment_notice_is_using",paymentNotice.getId()));
//                }else {
//                    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(i18n.getLocale());
//
//                    result.add(new ValidMessage(ValidMessage.Level.INFO, "payment_create_by_notice", "payment_create_by_notice",
//                            paymentNotice.getId(),currencyFormat.format(paymentNotice.getMustMoney()),currencyFormat.format(paymentNotice.getMoney())));
//                }
//
//                break;
//        }
//        return result;
//    }
//


}
