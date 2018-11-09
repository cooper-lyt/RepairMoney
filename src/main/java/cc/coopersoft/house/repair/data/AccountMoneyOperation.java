package cc.coopersoft.house.repair.data;

import cc.coopersoft.house.repair.data.model.BankAccountDetailsEntity;

import java.util.Date;

public interface AccountMoneyOperation {

    PaymentType getPaymentType();

    void setPaymentType(PaymentType paymentType);


    BankAccountDetailsEntity getBankAccountDetails();

    void setBankAccountDetails(BankAccountDetailsEntity bankAccountDetails);

    Date getOperationTime();

    void setOperationTime(Date operationDate);

}
