package cc.coopersoft.house.repair.controller;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.controller.BusinessOperationController;
import cc.coopersoft.framework.services.BusinessOperationService;
import cc.coopersoft.framework.tools.HttpJsonDataGet;
import cc.coopersoft.house.repair.Messages;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.model.PaymentNoticeEntity;
import cc.coopersoft.house.repair.pages.Collect;
import cc.coopersoft.house.repair.services.PaymentService;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.jsf.api.message.JsfMessage;

import javax.annotation.PostConstruct;
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

    private String noticeNumber;

    private PaymentNoticeEntity paymentNotice;

    public String getNoticeNumber() {
        return noticeNumber;
    }

    public void setNoticeNumber(String noticeNumber) {
        this.noticeNumber = noticeNumber;
    }

    public PaymentNoticeEntity getPaymentNotice() {
        return paymentNotice;
    }

    @PostConstruct
    public void create(){
        logger.config("component PaymentBusinessCreate is create!");
    }

    public Class<? extends ViewConfig> viewNotice(){
        try {
            paymentNotice = paymentService.getPaymentNotice(noticeNumber);
            if(paymentNotice == null){
                messages.addError().paymentNoticeNotFound(noticeNumber);
                return null;
            }

            if (paymentService.isCharge(paymentNotice)){
                messages.addError().paymentNoticeIsUsing();
                return null;
            }


        } catch (HttpJsonDataGet.ApiServerException e) {
            messages.addError().apiServerError();
            logger.log(Level.CONFIG,"取得交款通知单失败，单号：" + noticeNumber,e);
            return null;
        }
        beginConversation();
        return Collect.Notice.class;
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
