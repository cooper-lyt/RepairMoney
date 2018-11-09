package cc.coopersoft.house.repair.controller;

import cc.coopersoft.house.repair.data.AccountMoneyOperation;
import cc.coopersoft.house.repair.data.PaymentType;
import cc.coopersoft.house.repair.data.model.BankAccountDetailsEntity;
import cc.coopersoft.house.repair.services.BankAccountService;

import javax.inject.Inject;

public abstract class AccountMoneyOperationController <T extends AccountMoneyOperation> {


    @Inject
    protected BankAccountService bankAccountService;

    protected abstract BankAccountDetailsEntity createNewDetails();

    public abstract T getOperation();

    public BankAccountDetailsEntity getBankAccountDetails(){
        return getOperation().getBankAccountDetails();
    }

    public PaymentType getPaymentType(){
        return getOperation().getPaymentType();
    }

    public void setPaymentType(PaymentType type){
        getOperation().setPaymentType(type);
        if(PaymentType.CASH.equals(type) && (getBankAccountDetails() != null)){
            getOperation().setBankAccountDetails(null);
        }
        if(PaymentType.BANK.equals(type) && (getBankAccountDetails() == null)){
            getOperation().setBankAccountDetails(createNewDetails());
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
