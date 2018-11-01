package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.I18n;
import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.TaskValidComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.model.PutAccountBookEntity;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@SubscribeComponent
public class PutAccountHoldService implements TaskValidComponent<BusinessEntity> {

    @Inject
    private I18n i18n;


    @Override
    public List<ValidMessage> valid(BusinessEntity businessInstance) {
        for(PutAccountBookEntity put: businessInstance.getPutAccountBooks()){
            if (put.isPut()){
                List<ValidMessage> result = new ArrayList<>(1);
                result.add(new ValidMessage(ValidMessage.Level.OFF,"业务已过账！不可进行此操作。", "业务已财务过账！过账时间：" + i18n.dateDisplay(put.getPutAccountBusiness().getPutDate())));
                return result;
            }
        }
        return new ArrayList<>(0);
    }
}
