package cc.coopersoft.house.repair.controller;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.controller.BusinessOperationController;
import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.services.TaskEditSubscribeComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.framework.tools.UUIDGenerator;
import cc.coopersoft.house.repair.data.model.*;
import cc.coopersoft.house.repair.services.BankAccountService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@RequestScoped
@SubscribeComponent
public class PaymentBusinessController implements TaskEditSubscribeComponent,java.io.Serializable{



    @Inject
    private BusinessOperationController businessOperationController;

    @Inject
    private BankAccountService bankAccountService;


    public PaymentEntity getPaymentBusiness(){
        return ((BusinessEntity) businessOperationController.getBusinessInstance()).getPayment();
    }


    public BankAccountDetailsEntity getBankAccountDetails(){
        return getPaymentBusiness().getBankAccountDetails();
    }

    public PaymentType getPaymentType(){
        return getPaymentBusiness().getPaymentType();
    }

    public void setPaymentType(PaymentType type){
        getPaymentBusiness().setPaymentType(type);
        if(PaymentType.CASH.equals(type) && (getBankAccountDetails() != null)){
            getPaymentBusiness().setBankAccountDetails(null);
        }
        if(PaymentType.BANK.equals(type) && (getBankAccountDetails() == null)){
            getPaymentBusiness().setBankAccountDetails(new BankAccountDetailsEntity(getPaymentBusiness().getBusiness(),AccountOperationDirection.IN,UUIDGenerator.getUUID()));
        }
    }

    public String getBank(){
        if ((getBankAccountDetails() == null) || (getBankAccountDetails().getBankAccount() == null)){
            return null;
        }

        return getBankAccountDetails().getBankAccount().getBankAccountId();
    }

    public void setBank(String id){
        if (getBankAccountDetails() != null){
            getBankAccountDetails().setBankAccount(bankAccountService.getEntity(id));
        }
    }


    @Override
    public void init(BusinessInstance businessInstance) {
        if (getPaymentBusiness().getOperationDate() == null){
            getPaymentBusiness().setOperationDate(new Date());
        }
        if (getPaymentType() == null){
            getPaymentBusiness().setPaymentType(PaymentType.CASH);
        }
    }

    @Override
    public boolean assertion(BusinessInstance businessInstance) {

        return (PaymentType.CASH.equals(getPaymentType()) && (getPaymentBusiness().getBankAccountDetails() == null)) ||
                (PaymentType.BANK.equals(getPaymentType()) && getPaymentBusiness().getBankAccountDetails() != null);
    }

    @Override
    public void persistent(BusinessInstance businessInstance) {

    }

    @Override
    public List<ValidMessage> valid(BusinessInstance businessInstance) {
        if (PaymentType.BANK.equals(getPaymentBusiness().getPaymentType()) &&
                (getBankAccountDetails() == null)){
            List<ValidMessage> result = new ArrayList<>(1);
            result.add(new ValidMessage(ValidMessage.Level.OFF,"请选择交款银行！","交款方式为转账时必须选择交款银行！"));
            return result;
        }
        return new ArrayList<>(0);
    }

    @Override
    public void doAction(BusinessInstance businessInstance) {
        if (PaymentType.BANK.equals(getPaymentType())){
            getBankAccountDetails().setOperationTime(getPaymentBusiness().getOperationDate());
        }
    }
}
