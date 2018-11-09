package cc.coopersoft.house.repair.controller;

import cc.coopersoft.framework.controller.BusinessOperationController;
import cc.coopersoft.framework.tools.UUIDGenerator;
import cc.coopersoft.house.repair.data.AccountOperationDirection;
import cc.coopersoft.house.repair.data.PaymentType;
import cc.coopersoft.house.repair.data.model.PaymentEntity;
import cc.coopersoft.house.repair.data.model.*;
import cc.coopersoft.house.repair.services.BankAccountService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class PaymentBusinessController extends AccountMoneyOperationController<PaymentEntity> implements java.io.Serializable{

    @Inject
    private BusinessOperationController businessOperationController;

    private List<PaymentBusinessEntity> paymentItems;


    public List<PaymentBusinessEntity> getPaymentItem() {
        if (paymentItems == null){
            paymentItems = getOperation().getPaymentBusinessList();
        }
        return paymentItems;
    }


    @Override
    protected BankAccountDetailsEntity createNewDetails() {
        return new BankAccountDetailsEntity(getOperation().getBusiness(),AccountOperationDirection.IN,UUIDGenerator.getUUID());
    }

    @Override
    public PaymentEntity getOperation() {
        return ((BusinessEntity) businessOperationController.getBusinessInstance()).getPayment();
    }
}
