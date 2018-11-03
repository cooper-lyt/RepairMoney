package cc.coopersoft.framework.controller;

import cc.coopersoft.framework.BusinessManagerRole;
import cc.coopersoft.framework.BusinessRunManagerRole;
import cc.coopersoft.framework.SystemManagerRole;
import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.data.TaskSubscribe;
import cc.coopersoft.framework.data.TaskSubscribeGroup;
import cc.coopersoft.framework.data.model.BusinessDefineEntity;
import cc.coopersoft.framework.pages.Business;

import cc.coopersoft.framework.services.BusinessOperationService;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.framework.tools.DataHelper;
import cc.coopersoft.framework.tools.DefaultMessageBundle;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.omnifaces.cdi.Param;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@RequestScoped
public class BusinessOperationController  implements java.io.Serializable {


    public final static String BUSINESS_DEFINE_ID_PARAM_NAME = "businessDefineId";
    private final static String TASK_PARAM_NAME = "taskName";

    private final static String BUSINESS_INSTANCE_ID_PARAM_NAME = "businessInstanceId";

    @Inject
    private Logger logger;

    @Inject
    private FacesContext facesContext;

    @Inject
    @Default
    private Conversation conversation;

    @Inject
    private BusinessOperationService businessOperationService;

    @Inject @DefaultMessageBundle
    private ResourceBundle bundle;

    @Inject @Param(name = BUSINESS_INSTANCE_ID_PARAM_NAME)
    private String businessId;

    @Inject @Param(name = TASK_PARAM_NAME)
    private String taskDefineName;

    @Inject @Param(name = "assignTo")
    private String assignToEmpId;


    @PostConstruct
    public void initFromParam(){
        String defineId = facesContext.getExternalContext().getRequestParameterMap().get(BUSINESS_DEFINE_ID_PARAM_NAME);
        taskDefineName = facesContext.getExternalContext().getRequestParameterMap().get(TASK_PARAM_NAME);
        businessId = facesContext.getExternalContext().getRequestParameterMap().get(BUSINESS_INSTANCE_ID_PARAM_NAME);
        if (!DataHelper.empty(defineId) && !DataHelper.empty(taskDefineName) && !DataHelper.empty(businessId)){
            businessOperationService.initInstance(defineId,taskDefineName,businessId);
        }
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getTaskDefineName() {
        return taskDefineName;
    }

    public void setTaskDefineName(String taskDefineName) {
        this.taskDefineName = taskDefineName;
    }


    @BusinessRunManagerRole
    public Class<? extends ViewConfig> suspendBusiness(){
        businessOperationService.suspendBusiness();
        return Business.BusinessView.class;
    }

    @BusinessRunManagerRole
    public Class<? extends ViewConfig> continueBusiness(){
        businessOperationService.continueBusiness();
        return Business.BusinessView.class;
    }

    @BusinessRunManagerRole
    public Class<? extends ViewConfig> assignBusiness(){
        businessOperationService.assignTo(assignToEmpId);
        return Business.BusinessView.class;
    }

    @BusinessRunManagerRole
    public Class<? extends ViewConfig> abortBusiness(){
        viewBusiness();
        BusinessOperationService.ValidResult result = businessOperationService.abortBusiness();
        sendMessage(result);
        if (result.isPass()){
            return Business.BusinessView.class;
        }else{
            return null;
        }
    }

    @BusinessManagerRole
    public Class<? extends ViewConfig> revokeBusiness(){
        logger.config("begin revoke business:" + businessId);
        viewBusiness();
        BusinessOperationService.ValidResult result = businessOperationService.revokeBusiness();
        sendMessage(result);
        if (result.isPass()){
            return Business.BusinessView.class;
        }else{
            return null;
        }
    }


    @SystemManagerRole
    public Class<? extends ViewConfig> deleteBusiness(){
        viewBusiness();
        BusinessOperationService.ValidResult result = businessOperationService.deleteBusiness();
        sendMessage(result);
        if (result.isPass()){
            return Business.BusinessDeleted.class;
        }else{
            return null;
        }
    }

    public boolean isHasReport(){
        //TODO report
        return false;
    }

    public BusinessDefineEntity getBusinessDefine() {
        return businessOperationService.getBusinessDefine();
    }

    public BusinessInstance getBusinessInstance(){
        return businessOperationService.getBusinessInstance();
    }

    public boolean isPersistent(){
        return businessOperationService.isPersistent();
    }

    private void sendMessage(BusinessOperationService.ValidResult validResult){
        Iterator<ValidMessage> it = validResult.getMessages();
        while (it.hasNext()){
            ValidMessage msg = it.next();

            FacesMessage.Severity severity = null;
            switch (msg.getLevel()){

                case INFO:
                    severity = FacesMessage.SEVERITY_INFO;
                    break;
                case WARN:
                case SERVER:
                    severity = FacesMessage.SEVERITY_WARN;
                    break;
                case OFF:
                case FAIL:
                    severity = FacesMessage.SEVERITY_ERROR;
                    break;
            }

            String summary;
            try {
                summary = bundle.getString(msg.getSummary());
            }catch (MissingResourceException e){
                summary = msg.getSummary();
            }
            String detail;
            try {
                detail=bundle.getString(msg.getDetail());
            }catch (MissingResourceException e){
                detail = msg.getDetail();
            }
            if (msg.getParams() != null){
                summary = MessageFormat.format(summary, msg.getParams());
                detail = MessageFormat.format(detail,msg.getParams());
            }

            logger.config("add " + severity + " message:" + summary);
            facesContext.addMessage(null,new FacesMessage(severity,summary,detail));
        }

    }

    private Class<? extends ViewConfig> preparePage(){
        if (isPersistent()){
            return Business.PrepareComplete.class;
        }
        return Business.PrepareCreate.class;
    }

    public void viewBusiness(){
        logger.config("view business instance id:" + businessId);
        if (!DataHelper.empty(businessId)) {
            businessOperationService.initInstance(businessId, BusinessOperationService.VIEW_TASK_NAME);
        }else{
            businessOperationService.clearInstance();
        }
    }

    public void cancel(){
        endConversation();
        try {
            facesContext.getExternalContext().redirect( facesContext.getExternalContext().getRequestContextPath() + getBusinessDefine().getStartPage());
        } catch (IOException e) {
            logger.log(Level.WARNING,e.getMessage(),e);
        }
    }

    public Class<? extends ViewConfig> taskBegin(){
        BusinessOperationService.ValidResult result = businessOperationService.taskBegin();
        sendMessage(result);
        if (result.isPass()){
            beginConversation();
            if (businessOperationService.isHasEditor()){
                return next();
            }else{
                return preparePage();
            }
        }else{
            return null;
        }
    }

    public Class<? extends ViewConfig> taskComplete(){
        BusinessOperationService.ValidResult result = businessOperationService.taskComplete();
        sendMessage(result);
        if (result.isPass()){
            endConversation();
            return Business.Completed.class;
        }else{
            return null;
        }

    }

    //-- editor

    public boolean isHasPrevious(){
        return (businessOperationService.getPageIndex() != null) && (businessOperationService.getPageIndex() > 0);
    }

    public Class<? extends ViewConfig> previous(){
        if (isHasPrevious()){
            toPage(businessOperationService.getPageIndex() - 1);
            return Business.Editor.class;
        }else{
            return null;
        }
    }

    public Class<? extends ViewConfig> toPage(int page){
        businessOperationService.toPage(page);
        return Business.Editor.class;
    }

    public Class<? extends ViewConfig> next(){
        BusinessOperationService.ValidResult result = businessOperationService.savePage();
        sendMessage(result);
        if (result.isPass()){
            if (businessOperationService.isHasNext()){
                businessOperationService.next();
                return Business.Editor.class;
            }else{
                logger.config("business memo is:" + getBusinessInstance().getMemo());
                return preparePage();
            }
        }else{
            return null;
        }
    }



    public Class<? extends ViewConfig> save(){
        BusinessOperationService.ValidResult result = businessOperationService.savePage();
        sendMessage(result);
        if (result.isPass()){
            return Business.Editor.class;
        }else{
            return null;
        }
    }

    public String getTaskName(){
        return businessOperationService.getTaskName();
    }

    public List<TaskSubscribe> getEditor(){
        return businessOperationService.getEditor();
    }

    public boolean isHasNext(){
        return businessOperationService.isHasNext();
    }

    public List<String> getEditorDependencies(){
        return businessOperationService.getEditorDependencies();
    }

    public List<String> getViewDependencies(){
        return businessOperationService.getViewDependencies();
    }

    public List<TaskSubscribeGroup> getEditorGroups(){
        return businessOperationService.getEditorGroups();
    }

    public int getPageIndex(){
        Integer pageIndex = businessOperationService.getPageIndex();
        if (pageIndex == null){
            return -1;
        }else {
            return pageIndex;
        }
    }


    public List<TaskSubscribeGroup> getViewGroups(){
        return businessOperationService.getViewGroups();
    }

    private void beginConversation(){
        if ( conversation.isTransient() )
        {
            conversation.begin();
        }
        conversation.setTimeout(10 * 60 * 1000); //10 minute
    }

    private void endConversation() {
        if ( !conversation.isTransient() )
        {
            conversation.end();
            logger.config("end conversation id: " + conversation.getId() + " = " + conversation.isTransient());
        }
    }
}
