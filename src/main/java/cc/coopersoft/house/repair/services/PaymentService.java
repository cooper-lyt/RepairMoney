package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.services.SystemParamService;
import cc.coopersoft.framework.tools.DataHelper;
import cc.coopersoft.framework.tools.HttpJsonDataGet;
import cc.coopersoft.framework.tools.UUIDGenerator;
import cc.coopersoft.house.repair.data.AccountOperationDirection;
import cc.coopersoft.house.repair.data.PaymentType;
import cc.coopersoft.house.repair.data.model.PaymentEntity;
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
    private SystemParamService systemParamService;

    @Inject
    private RemoteDataService remoteDataService;


    public PaymentNoticeEntity getPaymentNotice(String noticeNumber) throws HttpJsonDataGet.ApiServerException {
        PaymentNoticeEntity result = paymentNoticeRepository.findBy(noticeNumber.trim());
        //TODO if House is empty
        if (result == null){
            result = remoteDataService.getPaymentNotice(noticeNumber);

        }
        return result;
    }

    public boolean isCharge(PaymentNoticeEntity paymentNotice){
        return paymentNoticeRepository.queryNoticeUseCount(paymentNotice.getId()) > 0;
    }

    public void createBusinessByHouse(BusinessEntity business, HouseEntity house){
        PaymentEntity payment = new PaymentEntity(business);
        business.setPayment(payment);
        payment.setOperationTime(new Date());
        payment.setSectionName(house.getSectionName());
        payment.setSectionCode(house.getSectionCode());
        payment.setPaymentType(systemParamService.getEnumParam(PaymentType.class,"wxzj.preferredPaymentType"));
        HouseAccountEntity houseAccountEntity = houseAccountRepository.findOptionalByHouseCode(house.getHouseCode());
        PaymentBusinessEntity.Type type = ((houseAccountEntity == null) || (HouseAccountEntity.Status.DESTROY.equals(houseAccountEntity.getStatus()))) ? PaymentBusinessEntity.Type.FIRST : PaymentBusinessEntity.Type.ADD;
        PaymentBusinessEntity paymentBusinessEntity = new PaymentBusinessEntity(
                type,
                business.getPayment()
        );
        payment.getPaymentBusinesses().add(paymentBusinessEntity);

        AccountDetailsEntity accountDetailsEntity =
                new AccountDetailsEntity(business,AccountOperationDirection.IN,UUIDGenerator.getUUID());
        accountDetailsEntity.setStatus(AccountDetailsEntity.Status.RUNNING);
        accountDetailsEntity.setHouse(house);
        business.getAccountDetails().add(accountDetailsEntity);
        paymentBusinessEntity.setAccountDetails(accountDetailsEntity);
        if (houseAccountEntity != null){
            accountDetailsEntity.setHouseAccount(houseAccountEntity);
            houseAccountEntity.getAccountDetails().add(accountDetailsEntity);
        }
    }

    public void createBusinessByNotice(BusinessEntity business, PaymentNoticeEntity paymentNotice){
        business.setPayment(new PaymentEntity(business));
        business.getPayment().setPaymentNotice(paymentNotice);
        business.getPayment().setOperationTime(new Date());
        business.getPayment().setMustMoney(paymentNotice.getMustMoney());
        business.getPayment().setMoney(paymentNotice.getMoney());
        business.getPayment().setPaymentType(systemParamService.getEnumParam(PaymentType.class,"wxzj.preferredPaymentType"));
        int itemCount = paymentNotice.getNoticeItems().size();
        int i = 0;
        for(PaymentNoticeItemEntity item: paymentNotice.getNoticeItems()){
            if(DataHelper.empty(business.getPayment().getSectionCode())){
                business.getPayment().setSectionCode(item.getHouse().getSectionCode());
                business.getPayment().setSectionName(item.getHouse().getSectionName());
            }
            HouseAccountEntity houseAccountEntity = houseAccountRepository.findOptionalByHouseCode(item.getHouse().getHouseCode());
            PaymentBusinessEntity.Type type = ((houseAccountEntity == null) || (HouseAccountEntity.Status.DESTROY.equals(houseAccountEntity.getStatus()))) ? PaymentBusinessEntity.Type.FIRST : PaymentBusinessEntity.Type.ADD;
            PaymentBusinessEntity paymentBusinessEntity = new PaymentBusinessEntity(
                    item.getMoney(),
                    item.getMustMoney(),
                    item.getCalcDetails(),
                    type,
                    business.getPayment()
            );
            business.getPayment().getPaymentBusinesses().add(paymentBusinessEntity);

            AccountDetailsEntity accountDetailsEntity =
                    new AccountDetailsEntity(business,AccountOperationDirection.IN,UUIDGenerator.getUUID());
            accountDetailsEntity.setStatus(AccountDetailsEntity.Status.RUNNING);
            accountDetailsEntity.setHouse(item.getHouse());
            accountDetailsEntity.setMoney(item.getMoney());
            business.getAccountDetails().add(accountDetailsEntity);
            paymentBusinessEntity.setAccountDetails(accountDetailsEntity);
            if (houseAccountEntity != null){
                accountDetailsEntity.setHouseAccount(houseAccountEntity);
                houseAccountEntity.getAccountDetails().add(accountDetailsEntity);
            }

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
