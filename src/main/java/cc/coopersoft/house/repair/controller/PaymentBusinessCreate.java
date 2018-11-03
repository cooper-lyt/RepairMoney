package cc.coopersoft.house.repair.controller;

import cc.coopersoft.framework.controller.BusinessOperationController;
import cc.coopersoft.framework.services.BusinessOperationService;
import cc.coopersoft.framework.tools.HttpJsonDataGet;
import cc.coopersoft.house.repair.Messages;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.model.HouseEntity;
import cc.coopersoft.house.repair.data.model.PaymentNoticeEntity;
import cc.coopersoft.house.repair.pages.Collect;
import cc.coopersoft.house.repair.services.PaymentService;
import cc.coopersoft.house.repair.services.RemoteDataService;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.jsf.api.message.JsfMessage;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Default;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ConversationScoped
public class PaymentBusinessCreate implements java.io.Serializable {

    @Inject
    @Default
    private Conversation conversation;

    @Inject
    private Logger logger;

    @Inject
    private FacesContext facesContext;

    @Inject
    private PaymentService paymentService;

    @Inject
    private JsfMessage<Messages> messages;

    @Inject
    private BusinessOperationService businessOperationService;

    @Inject
    private BusinessOperationController businessOperationController;

    @Inject
    private RemoteDataService remoteDataService;

    private String noticeNumber;

    private String mapNumber;

    private String blockNumber;

    private String buildNumber;

    private String houseOrder;

    private String houseCode;

    private String ownerBusinessNumber;

    private String contractNumber;

    private PaymentNoticeEntity paymentNotice;

    private HouseEntity house;

    public String getNoticeNumber() {
        return noticeNumber;
    }

    public void setNoticeNumber(String noticeNumber) {
        this.noticeNumber = noticeNumber;
    }

    public String getMapNumber() {
        return mapNumber;
    }

    public void setMapNumber(String mapNumber) {
        this.mapNumber = mapNumber;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getHouseOrder() {
        return houseOrder;
    }

    public void setHouseOrder(String houseOrder) {
        this.houseOrder = houseOrder;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    public String getOwnerBusinessNumber() {
        return ownerBusinessNumber;
    }

    public void setOwnerBusinessNumber(String ownerBusinessNumber) {
        this.ownerBusinessNumber = ownerBusinessNumber;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public PaymentNoticeEntity getPaymentNotice() {
        return paymentNotice;
    }

    public HouseEntity getHouse() {
        return house;
    }

    private Class <? extends ViewConfig> viewNotice(PaymentNoticeEntity notice){
        paymentNotice = notice;
        if(paymentNotice == null){
            messages.addError().paymentNoticeNotFound(noticeNumber);
            return null;
        }

        if (paymentService.isCharge(paymentNotice)){
            messages.addError().paymentNoticeIsUsing();
            return null;
        }
        beginConversation();
        return Collect.Notice.class;
    }

    public Class<? extends ViewConfig> viewNotice(){
        try {

            return viewNotice(paymentService.getPaymentNotice(noticeNumber));

        } catch (HttpJsonDataGet.ApiServerException e) {
            messages.addError().apiServerError();
            logger.log(Level.CONFIG,"连接交易服务器失败：",e);
            return null;
        }

    }

    public Class<? extends ViewConfig> viewHouseByMapNumber(){
        try {
            house = remoteDataService.getHouse(mapNumber,blockNumber,buildNumber,houseOrder);
            if(house == null){
                messages.addError().houseNotFound(mapNumber + "-" + blockNumber + "-" + buildNumber + "-" + houseOrder);
                return null;
            }
        } catch (HttpJsonDataGet.ApiServerException e) {
            messages.addError().apiServerError();
            logger.log(Level.CONFIG,"连接交易服务器失败：",e);
            return null;
        }
        beginConversation();
        return Collect.House.class;
    }

    public Class<? extends ViewConfig> viewHouseByHouseCode(){
        try {
            house = remoteDataService.getHouse(houseCode);
            if(house == null){
                messages.addError().houseNotFound(houseCode);
                return null;
            }
        } catch (HttpJsonDataGet.ApiServerException e) {
            messages.addError().apiServerError();
            logger.log(Level.CONFIG,"连接交易服务器失败：",e);
            return null;
        }
        beginConversation();
        return Collect.House.class;
    }

    private Class<? extends ViewConfig> viewByPaymentData(RemoteDataService.PaymentData data){
        if (data.getNotice() != null){
            return viewNotice(data.getNotice());
        }else if (data.getHouse() != null){
            house = data.getHouse();
            beginConversation();
            return Collect.House.class;
        }else{

            return null;
        }
    }

    public Class<? extends ViewConfig> viewByOwnerBusiness(){

        try {

            RemoteDataService.PaymentData data = remoteDataService.getPayByBusiness(ownerBusinessNumber);
            if (data.notFound()){
                messages.addError().ownerBusinessNotFound(ownerBusinessNumber);
                return null;
            }
            return viewByPaymentData(data);
        } catch (HttpJsonDataGet.ApiServerException e) {
            messages.addError().apiServerError();
            logger.log(Level.CONFIG,"连接交易服务器失败：",e);
            return null;
        }

    }

    public Class<? extends ViewConfig> viewByContract(){

        try {

            RemoteDataService.PaymentData data = remoteDataService.getPayByContract(contractNumber);
            if (data.notFound()){
                messages.addError().contractNotFound(contractNumber);
                return null;
            }
            return viewByPaymentData(data);
        } catch (HttpJsonDataGet.ApiServerException e) {
            messages.addError().apiServerError();
            logger.log(Level.CONFIG,"连接交易服务器失败：",e);
            return null;
        }
    }


    public Class<? extends ViewConfig> paymentByHouse(){
        paymentService.createBusinessByHouse((BusinessEntity) businessOperationService.createInstance(
                facesContext.getExternalContext().getRequestParameterMap().get(BusinessOperationController.BUSINESS_DEFINE_ID_PARAM_NAME),
                BusinessOperationService.CREATE_TASK_NAME),house);
        return businessOperationController.taskBegin();
    }



    public Class<? extends ViewConfig> paymentByNotice(){
        if (paymentService.isCharge(paymentNotice)){
            messages.addError().paymentNoticeIsUsing();
            return null;
        }
        paymentService.createBusinessByNotice((BusinessEntity) businessOperationService.createInstance(
                facesContext.getExternalContext().getRequestParameterMap().get(BusinessOperationController.BUSINESS_DEFINE_ID_PARAM_NAME),
                BusinessOperationService.CREATE_TASK_NAME),paymentNotice);
        return businessOperationController.taskBegin();
    }

    public Class<? extends ViewConfig> cancel(){
        endConversation();
        return Collect.Payment.class;
    }


    private void beginConversation(){
        logger.config("create conversation :" + conversation.isTransient());
        if ( conversation.isTransient() )
        {
            conversation.begin();
            logger.config("create conversation id:" + conversation.getId());
            conversation.setTimeout(10 * 60 * 1000); //10 minute
        }
    }

    private void endConversation() {
        if ( !conversation.isTransient() )
        {
            logger.config("end conversation id:" + conversation.getId());
            conversation.end();
        }
    }

}
