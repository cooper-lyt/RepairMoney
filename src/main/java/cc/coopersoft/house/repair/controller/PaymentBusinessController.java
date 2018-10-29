package cc.coopersoft.house.repair.controller;

import cc.coopersoft.framework.controller.BusinessOperationController;
import cc.coopersoft.framework.tools.UUIDGenerator;
import cc.coopersoft.house.repair.data.model.*;
import cc.coopersoft.house.repair.services.BankAccountService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class PaymentBusinessController implements java.io.Serializable{

    @PostConstruct
    public void init(){
        if (getPaymentType() == null){
            getPaymentBusiness().setPaymentType(PaymentType.CASH);
        }
    }

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



}
