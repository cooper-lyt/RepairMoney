package cc.coopersoft.house.repair.controller;

import cc.coopersoft.framework.controller.BusinessOperationController;
import cc.coopersoft.framework.tools.UUIDGenerator;
import cc.coopersoft.house.repair.data.AccountOperationDirection;
import cc.coopersoft.house.repair.data.model.BankAccountDetailsEntity;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.model.RefundBusinessEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class RefundBusinessController extends AccountMoneyOperationController<RefundBusinessEntity> {

    @Inject
    private BusinessOperationController businessOperationController;

    @Override
    protected BankAccountDetailsEntity createNewDetails() {
        return new BankAccountDetailsEntity(getOperation().getBusiness(),AccountOperationDirection.OUT,UUIDGenerator.getUUID());
    }

    @Override
    public RefundBusinessEntity getOperation() {
        return ((BusinessEntity) businessOperationController.getBusinessInstance()).getRefund();
    }
}
