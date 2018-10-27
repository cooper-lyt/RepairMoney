package cc.coopersoft.framework.controller;

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
import java.util.List;
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


    @PostConstruct
    public void initFromParam(){
        String defineId = facesContext.getExternalContext().getRequestParameterMap().get(BUSINESS_DEFINE_ID_PARAM_NAME);
        String taskName = facesContext.getExternalContext().getRequestParameterMap().get(TASK_PARAM_NAME);
        String businessId = facesContext.getExternalContext().getRequestParameterMap().get(BUSINESS_INSTANCE_ID_PARAM_NAME);
        if (!DataHelper.empty(defineId) && !DataHelper.empty(taskName) && !DataHelper.empty(businessId)){
            businessOperationService.initInstance(defineId,taskName,businessId);
        }
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

    private void sendMessage(List<ValidMessage> messages){
        for(ValidMessage msg: messages){
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
                    severity = FacesMessage.SEVERITY_ERROR;
                    break;
            }

            String summary = bundle.getString(msg.getSummary());
            if (DataHelper.empty(summary)){
                summary = msg.getSummary();
            }
            String detail = bundle.getString(msg.getDetail());
            if (DataHelper.empty(detail)){
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
        sendMessage(result.getMessages());
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
        sendMessage(result.getMessages());
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
        sendMessage(result.getMessages());
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
        sendMessage(result.getMessages());
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
