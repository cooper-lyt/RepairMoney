package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.I18n;
import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.model.PaymentBusinessEntity;
import cc.coopersoft.house.repair.data.model.PaymentEntity;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public abstract class PaymentAccountValidService implements TaskActionComponent<BusinessEntity> {

    @Inject
    private HouseAccountService houseAccountService;

    @Inject
    private I18n i18n;


    @Override
    public List<ValidMessage> valid(BusinessEntity businessInstance) {
        PaymentEntity payment = businessInstance.getPayment();

        List<String> houseCodes = new ArrayList<>();
        for(PaymentBusinessEntity pb: payment.getPaymentBusinesses()){
            houseCodes.add(pb.getAccountDetails().getHouse().getHouseCode());
        }


        if (!houseAccountService.validTime(payment.getOperationDate(),houseCodes)){
            List<ValidMessage> result = new ArrayList<>(1);
            result.add(new ValidMessage(ValidMessage.Level.OFF,"交款时间必须在所有房屋账户变动之后！","最后变动时间：" + i18n.datetimeDisplay(houseAccountService.lastChangeTime(houseCodes))));
            return result;
        }


        return new ArrayList<>(0);
    }

}
