package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.services.SystemParamService;
import cc.coopersoft.house.repair.data.AccountOperationDirection;
import cc.coopersoft.house.repair.data.PaymentType;
import cc.coopersoft.house.repair.data.model.*;

import javax.inject.Inject;

public class RefundService implements java.io.Serializable{

    @Inject
    private SystemParamService systemParamService;

    public void createBusinessByAccount(BusinessEntity business, HouseAccountEntity account, RefundBusinessEntity.Type type){
        RefundBusinessEntity refundBusiness = new RefundBusinessEntity();
        business.setRefund(refundBusiness);
        refundBusiness.setType(type);
        if ((type != null) && (RefundBusinessEntity.Type.DESTROY.equals(type))){
            refundBusiness.setMoney(account.getBalance());
        }
//        refundBusiness.setAccountDetails(new AccountDetailsEntity(business,AccountOperationDirection.OUT,business.getId()));
//        business.getAccountDetails().add(refundBusiness.getAccountDetails());
//        refundBusiness.setMoney(refundBusiness.getMoney());
//        refundBusiness.getAccountDetails().setHouse(account.getHouse());
//        refundBusiness.getAccountDetails().setHouseAccount(account);
//        refundBusiness.getAccountDetails().setStatus(AccountDetailsEntity.Status.RUNNING);
//        refundBusiness.setPaymentType(systemParamService.getEnumParam(PaymentType.class,"business.preferredPaymentType"));
    }
}
