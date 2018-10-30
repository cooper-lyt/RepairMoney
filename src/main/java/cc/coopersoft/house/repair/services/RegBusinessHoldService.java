package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.I18n;
import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.services.TaskValidComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.house.repair.data.model.BusinessEntity;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@SubscribeComponent
public class RegBusinessHoldService implements TaskValidComponent<BusinessEntity> {

    @Inject
    private I18n i18n;

    @Override
    public List<ValidMessage> valid(BusinessEntity businessInstance) {
        List<ValidMessage> result = new ArrayList<>();
        if (businessInstance.isReg()){
            result.add(new ValidMessage(ValidMessage.Level.OFF,"此业务已生效不可进行此操作！","业务已生效，生效时间：" + i18n.datetimeDisplay(businessInstance.getRegTime())));
        }
        return result;
    }

}
