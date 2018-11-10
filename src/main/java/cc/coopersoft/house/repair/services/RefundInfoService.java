package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.SubscribeFailException;
import cc.coopersoft.framework.services.TaskEditSubscribeComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.model.RefundBusinessEntity;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

@SubscribeComponent
public class RefundInfoService extends AccountOperationInfoEditService<RefundBusinessEntity> {


    @Inject
    private RefundService refundService;

    @Override
    public boolean init(BusinessEntity business) {
        return true;
    }

    @Override
    public void doCreate(BusinessEntity business) {
    }

    @Override
    protected RefundBusinessEntity getOperationBusiness(BusinessEntity businessInstance) {
        return businessInstance.getRefund();
    }

    @Override
    public List<ValidMessage> valid(BusinessEntity businessInstance) {
        List<ValidMessage> result = super.valid(businessInstance);
        if(RefundBusinessEntity.Type.OTHER.equals(businessInstance.getRefund().getType()) &&
                (businessInstance.getRefund().getMoney().compareTo(businessInstance.getRefund().getAccountDetails().getHouseAccount().getValidBalance()) < 0)){
            result.add(new ValidMessage(ValidMessage.Level.OFF,"退款金额必须小于账户可用余额!","可用金额为：" + i18n.currencyDisplay(businessInstance.getRefund().getAccountDetails().getHouseAccount().getValidBalance())));
        }
        if (RefundBusinessEntity.Type.DESTROY.equals(businessInstance.getRefund().getType()) &&
                (BigDecimal.ZERO.compareTo(businessInstance.getRefund().getAccountDetails().getHouseAccount().getFrozen()) < 0)){
            result.add(new ValidMessage(ValidMessage.Level.OFF,"账户有冻结金额不能进行销户!","冻结金额为：" + i18n.currencyDisplay(businessInstance.getRefund().getAccountDetails().getHouseAccount().getFrozen())));
        }
        return result;
    }

    @Override
    public void doAction(BusinessEntity business, boolean persistent) throws SubscribeFailException {
        super.doAction(business,persistent);
        if (!RefundBusinessEntity.Type.OTHER.equals(business.getRefund().getType())){
            business.getRefund().setMoney(refundService.getRefundMoney(business.getRefund()));
        }
    }

}
