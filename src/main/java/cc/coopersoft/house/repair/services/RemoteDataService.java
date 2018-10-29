package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.services.SystemParamService;
import cc.coopersoft.framework.tools.HttpJsonDataGet;
import cc.coopersoft.house.repair.data.model.*;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class RemoteDataService implements java.io.Serializable{

    private static final String API_SERVER_ADDRESS_PARAM_KEY = "config.server.api";

    private static final String PAYMENT_NOTICE_API_FUNCTION_ADDRESS = "/api/public/payment-notice";

    @Inject
    private SystemParamService systemParamService;

    public PaymentNoticeEntity getPaymentNotice(String number) throws HttpJsonDataGet.ApiServerException {
        Map<String,String> params = new HashMap<String, String>(1);
        params.put("number",number);
        PaymentNoticeEntity result = HttpJsonDataGet.getData(systemParamService.getStringParam(API_SERVER_ADDRESS_PARAM_KEY) + PAYMENT_NOTICE_API_FUNCTION_ADDRESS,params,PaymentNoticeEntity.class);
        for(PaymentNoticeItemEntity item: result.getNoticeItems()){
            item.setPaymentNotice(result);
            for(OwnerPersonEntity owner: item.getHouse().getOwnerPersons()){
                owner.setHouse(item.getHouse());
            }
        }
        result.setSource(PaymentNoticeEntity.Source.OWNER);
        return result;
    }

}
