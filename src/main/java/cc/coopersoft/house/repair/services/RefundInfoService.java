package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.TaskEditSubscribeComponent;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.model.RefundBusinessEntity;

@SubscribeComponent
public class RefundInfoService extends AccountOperationInfoEditService<RefundBusinessEntity> {


    @Override
    public void init(BusinessEntity business) {
    }

    @Override
    public void doCreate(BusinessEntity business) {
    }

    @Override
    protected RefundBusinessEntity getOperationBusiness(BusinessEntity businessInstance) {
        return businessInstance.getRefund();
    }

}
