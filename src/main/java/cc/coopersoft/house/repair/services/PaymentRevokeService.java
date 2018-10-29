package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.house.repair.data.model.AccountDetailsEntity;

import java.util.List;

@SubscribeComponent
public class PaymentRevokeService extends HouseAccountDeleteService{



    @Override
    protected List<AccountDetailsEntity> getOperations() {
        return null;
    }
}
