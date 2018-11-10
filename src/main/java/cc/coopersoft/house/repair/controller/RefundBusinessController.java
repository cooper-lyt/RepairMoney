package cc.coopersoft.house.repair.controller;

import cc.coopersoft.framework.controller.BusinessOperationController;
import cc.coopersoft.framework.tools.UUIDGenerator;
import cc.coopersoft.house.repair.data.AccountOperationDirection;
import cc.coopersoft.house.repair.data.model.BankAccountDetailsEntity;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.model.RefundBusinessEntity;
import cc.coopersoft.house.repair.services.RefundService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

@Named
@RequestScoped
public class RefundBusinessController extends AccountMoneyOperationController<RefundBusinessEntity> {

    @Inject
    private BusinessOperationController businessOperationController;

    @Inject
    private RefundService refundService;

    @Override
    protected BankAccountDetailsEntity createNewDetails() {
        return new BankAccountDetailsEntity(getOperation().getBusiness(),AccountOperationDirection.OUT,UUIDGenerator.getUUID());
    }

    @Override
    public RefundBusinessEntity getOperation() {
        return ((BusinessEntity) businessOperationController.getBusinessInstance()).getRefund();
    }

    public List<RefundBusinessEntity.Type> getTypes(){
        List<RefundBusinessEntity.Type> result = new ArrayList<>(EnumSet.of(RefundBusinessEntity.Type.DESTROY,RefundBusinessEntity.Type.OTHER));
        Collections.sort(result);
        return result;
    }

    public boolean isIncomeRefund(){
        return !getOperation().getIncomeRefunds().isEmpty();
    }

    public boolean isPaymentRefund(){
        return !getOperation().getPaymentRefunds().isEmpty();
    }

    public boolean isAccountRefund() {
        return getOperation().getAccountDetails() != null;
    }

    public BigDecimal getRefundFullMoney(){
        return refundService.getRefundFullMoney(getOperation());
    }

}
