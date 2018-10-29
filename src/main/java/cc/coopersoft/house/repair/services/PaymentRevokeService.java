package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.house.repair.data.model.AccountDetailsEntity;

import java.util.List;

@SubscribeComponent
public class PaymentRevokeService extends HouseAccountDeleteService implements TaskActionComponent {



    @Override
    protected List<AccountDetailsEntity> getOperations() {
        return null;
    }


}
