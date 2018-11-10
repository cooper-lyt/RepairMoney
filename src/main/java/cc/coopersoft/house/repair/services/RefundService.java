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

    public BigDecimal getRefundFullMoney(RefundBusinessEntity refundBusiness){
        BigDecimal result = BigDecimal.ZERO;
        switch (refundBusiness.getType()){

            case WRONG_FULL:
            case WRONG_PART:
                for(PaymentRefundEntity payment: refundBusiness.getPaymentRefunds()){
                    result = result.add(payment.getPaymentBusiness().getMoney());
                }
                break;
            case INCOME_PART:
            case INCOME_FULL:
                for(IncomeRefundEntity income: refundBusiness.getIncomeRefunds()){
                    result = result.add(income.getAccountIncome().getMoney());
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
