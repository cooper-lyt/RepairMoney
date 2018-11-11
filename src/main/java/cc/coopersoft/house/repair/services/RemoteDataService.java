package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.services.SystemParamService;
import cc.coopersoft.framework.tools.HttpJsonDataGet;
import cc.coopersoft.house.repair.data.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class RemoteDataService implements java.io.Serializable{

    public static class PaymentData {

        private PaymentNoticeEntity notice;
        private HouseEntity house;

        public PaymentData() {
        }


        public PaymentNoticeEntity getNotice() {
            return notice;
        }

        public void setNotice(PaymentNoticeEntity notice) {
            this.notice = notice;
            if (notice != null){
                for (PaymentNoticeItemEntity item : notice.getNoticeItems()) {
                    item.setPaymentNotice(notice);
                    for (OwnerPersonEntity owner : item.getHouse().getOwnerPersons()) {
                        owner.setHouse(item.getHouse());
                    }
                }
                notice.setSource(PaymentNoticeEntity.Source.OWNER);
            }
        }

        public HouseEntity getHouse() {
            return house;
        }

        public void setHouse(HouseEntity house) {
            this.house = house;
            if (house != null){
                for(OwnerPersonEntity owner: house.getOwnerPersons()){
                    owner.setHouse(house);
                }
            }
        }

        public boolean notFound(){
            return (house == null) && (notice == null);
        }
    }

    public static final String API_SERVER_ADDRESS_PARAM_KEY = "config.server.api";

    private static final String PAYMENT_NOTICE_API_FUNCTION_ADDRESS = "/api/public/payment-notice";

    @Inject
    private SystemParamService systemParamService;

    private PaymentData getPaymentData(Map<String,String> params) throws HttpJsonDataGet.ApiServerException {

        PaymentData result = HttpJsonDataGet.getData(systemParamService.getStringParam(API_SERVER_ADDRESS_PARAM_KEY) + PAYMENT_NOTICE_API_FUNCTION_ADDRESS,params,PaymentData.class);
        if (result == null){
            return new PaymentData();
        }
        return result;
    }

    PaymentNoticeEntity getPaymentNotice(String number) throws HttpJsonDataGet.ApiServerException {
        Map<String,String> params = new HashMap<>(1);
        params.put("number",number.trim());

        return getPaymentData(params).getNotice();
    }

    public HouseEntity getHouse(String houseCode) throws HttpJsonDataGet.ApiServerException {
        Map<String,String> params = new HashMap<>(1);
        params.put("code",houseCode.trim());
        return getPaymentData(params).getHouse();
    }

    public HouseEntity getHouse(String mapNumber,String blockNumber, String buildNumber, String houseOrder) throws HttpJsonDataGet.ApiServerException {
        Map<String,String> params = new HashMap<>(4);
        params.put("map",mapNumber.trim());
        params.put("block",blockNumber.trim());
        params.put("build",buildNumber.trim());
        params.put("house",houseOrder.trim());
        return getPaymentData(params).getHouse();
    }


    public PaymentData getPayByBusiness(String businessId) throws HttpJsonDataGet.ApiServerException {
        Map<String,String> params = new HashMap<>(1);
        params.put("biz",businessId.trim());
        return getPaymentData(params);
    }

    public PaymentData getPayByContract(String contractNumber) throws HttpJsonDataGet.ApiServerException {
        Map<String,String> params = new HashMap<>(1);
        params.put("contract",contractNumber.trim());
        return getPaymentData(params);
    }

}
