package cc.coopersoft.house.repair.controller;

import cc.coopersoft.framework.controller.BusinessOperationController;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.model.RefundBusinessEntity;
import cc.coopersoft.house.repair.services.RefundService;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.omnifaces.cdi.Param;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ConversationScoped
public class RefundBusinessCreate  implements java.io.Serializable{

    @Inject @Param(name = "refund")
    private RefundBusinessEntity.Type type;

    @Inject
    private HouseAccountController houseAccountController;

    @Inject
    private BusinessOperationController businessOperationController;

    @Inject
    private RefundService refundService;

    public Class<? extends ViewConfig> refundByAccount(){
        refundService.createBusinessByAccount((BusinessEntity) businessOperationController.createInstance(),
                houseAccountController.getInstance(),type);
        return businessOperationController.taskBegin();
    }

}
