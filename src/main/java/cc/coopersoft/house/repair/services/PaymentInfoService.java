package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.services.SubscribeFailException;
import cc.coopersoft.framework.services.TaskEditSubscribeComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.PaymentType;
import cc.coopersoft.house.repair.data.model.*;

import java.util.Date;
import java.util.List;

@SubscribeComponent
public class PaymentInfoService extends AccountOperationInfoEditService<PaymentEntity> {


    @Override
    public void init(BusinessEntity business) {
    }

    @Override
    public void doCreate(BusinessEntity business) {
    }


    @Override
    protected PaymentEntity getOperationBusiness(BusinessEntity businessInstance) {
        return businessInstance.getPayment();
    }

}
