package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.services.SystemParamService;
import cc.coopersoft.house.repair.data.AccountOperationDirection;
import cc.coopersoft.house.repair.data.PaymentType;
import cc.coopersoft.house.repair.data.model.*;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;

public class RefundService implements java.io.Serializable{

    @Inject
    private SystemParamService systemParamService;

    public void createBusinessByAccount(BusinessEntity business, HouseAccountEntity account, RefundBusinessEntity.Type type){
        RefundBusinessEntity refundBusiness = new RefundBusinessEntity();
        business.setRefund(refundBusiness);

        if (type == null){
            refundBusiness.setType(RefundBusinessEntity.Type.DESTROY);
        }else{
            refundBusiness.setType(type);
        }
        if (RefundBusinessEntity.Type.DESTROY.equals(refundBusiness.getType())){
            refundBusiness.setMoney(account.getBalance());
        }
        refundBusiness.setAccountDetails(new AccountDetailsEntity(business,AccountOperationDirection.OUT,business.getId()));
        business.getAccountDetails().add(refundBusiness.getAccountDetails());
        refundBusiness.setMoney(refundBusiness.getMoney());
        refundBusiness.getAccountDetails().setHouse(account.getHouse());
        refundBusiness.getAccountDetails().setHouseAccount(account);
        refundBusiness.getAccountDetails().setStatus(AccountDetailsEntity.Status.RUNNING);
        refundBusiness.setOperationTime(new Date());
        refundBusiness.setPaymentType(systemParamService.getEnumParam(PaymentType.class,"business.preferredPaymentType"));
    }

    public BigDecimal getRefundMoney(RefundBusinessEntity refundBusiness){
        BigDecimal result = BigDecimal.ZERO;
        switch (refundBusiness.getType()){

            case WRONG_FULL:
            case WRONG_PART:
                for(PaymentBusinessEntity payment: refundBusiness.getPaymentBusiness()){
                    result = result.add(payment.getMoney());
                }
                break;
            case INCOME_PART:
            case INCOME_FULL:
                for(AccountIncomeEntity income: refundBusiness.getAccountIncomes()){
                    result = result.add(income.getMoney());
                }
                break;
            case DESTROY:
                result = refundBusiness.getAccountDetails().getHouseAccount().getBalance();
                break;
            case OTHER:
                result = refundBusiness.getAccountDetails().getHouseAccount().getValidBalance();
                break;
        }
        return result;
    }
}
