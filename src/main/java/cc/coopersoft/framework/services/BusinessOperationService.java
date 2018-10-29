package cc.coopersoft.framework.services;

import cc.coopersoft.framework.SubscribeComponentQualifier;
import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.data.TaskAction;
import cc.coopersoft.framework.data.TaskSubscribe;
import cc.coopersoft.framework.data.TaskSubscribeGroup;
import cc.coopersoft.framework.data.model.BusinessDefineEntity;
import cc.coopersoft.framework.data.model.DeleteActionEntity;
import cc.coopersoft.framework.data.model.SubscribeGroupEntity;
import cc.coopersoft.framework.data.model.TaskActionEntity;
import cc.coopersoft.framework.data.repository.BusinessDefineRepository;
import cc.coopersoft.framework.tools.DataHelper;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ConversationScoped
public class BusinessOperationService implements java.io.Serializable {


    public final static String CREATE_TASK_NAME = "_CREATE";
    public final static String VIEW_TASK_NAME = "_VIEW";

    private final static String VALID_LEVEL_PARAM_NAME = "framework.business.validLevel";
    private final static String BUSINESS_NUMBER_POOL_ID = "business_id";

    public enum ValidLevel{
        STRICT,
        EASY
    }



    public static class ValidResult{

        private List<ValidMessage> messages;

        private boolean pass;


        public ValidResult(boolean pass) {
            this.pass = pass;
            messages = new ArrayList<>();
        }

        public ValidResult(List<ValidMessage> messages, boolean pass) {
            this.messages = messages;
            this.pass = pass;
        }

        public List<ValidMessage> getMessages() {
            return messages;
        }

        public boolean isPass() {
            return pass;
        }

        public void setPass(boolean pass) {
            this.pass = pass;
        }
    }



    @Inject
    private Logger logger;

    @Inject
    private BusinessDefineRepository businessDefineRepository;

    @Inject
    private BusinessSubscribeConfigService businessSubscribeConfigService;

    @Inject
    private BusinessInstanceService<? extends BusinessInstance> businessInstanceService;

    @Inject
    private NumberService numberService;

    @Inject
    private SystemParamService systemParamService;

    //private String defineId;

    private String taskName;

    private BusinessInstance businessInstance;

    private BusinessDefineEntity businessDefine;

    private List<TaskSubscribeGroup> editorGroups;

    private List<TaskSubscribeGroup> viewGroups;

    private List<TaskAction> beforeActions;

    private List<TaskAction> afterActions;


    public BusinessDefineEntity getBusinessDefine() {
        return businessDefine;
    }

    public BusinessInstance getBusinessInstance() {
        return businessInstance;
    }

    public String getTaskName() {
        return taskName;
    }

    public List<TaskSubscribeGroup> getEditorGroups() {
        return editorGroups;
    }

    public List<TaskSubscribeGroup> getViewGroups() {
        return viewGroups;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public boolean isPersistent(){
        return !CREATE_TASK_NAME.equals(taskName);
    }


    public void initInstance(String instanceId,String taskName){
        if ((businessInstance != null) && DataHelper.isDirty(businessInstance.getId(),instanceId) ){
            clearInstance();

        }
        if (businessInstance == null){
            logger.config("load business instance id:" + instanceId);
            businessInstance = businessInstanceService.getEntity(instanceId);
        }
        if (businessInstance != null) {
            if ((businessDefine != null && DataHelper.isDirty(businessInstance.getDefineId(), businessDefine.getId())) || DataHelper.isDirty(taskName, this.taskName)) {
                clearDefine();
            }
            this.taskName = taskName;
            if (businessDefine == null) {
                businessDefine = businessDefineRepository.findBy(businessInstance.getDefineId());
                loadSubscribe();
            }
        }else{
            clearDefine();
        }
    }

    public void initInstance(String defineId,String taskName, String instanceId){
        initDefine(defineId,taskName);

        if ((businessInstance != null) && DataHelper.isDirty(businessInstance.getId(),instanceId) ){
            businessInstance = null;
        }
        if (businessInstance == null) {
            businessInstance = businessInstanceService.getEntity(instanceId);
            if ((businessInstance == null) || (!businessInstance.getDefineId().equals(defineId))) {
                logger.log(Level.WARNING, "init business fail! define id:[" + defineId + "]taskName:[" + taskName + "]instance id:[" + instanceId + "]");
                throw new IllegalArgumentException("init business fail! define id:[" + defineId + "]taskName:[" + taskName + "]instance id:[" + instanceId + "]");
            }
        }
    }

    public void createInstance(String defineId, String taskName){
        initDefine(defineId,taskName);
        if (businessInstance == null) {
            businessInstance = businessInstanceService.createNew();
            businessInstance.setId(numberService.getNumber(BUSINESS_NUMBER_POOL_ID, businessDefine.getId()));
            businessInstance.setSource(BusinessInstance.Source.BUSINESS);
            businessInstance.setDefineId(businessDefine.getId());
            businessInstance.setDefineName(businessDefine.getName());
            businessInstance.setDefineVersion(businessDefine.getDefineVersion());
            businessInstance.setStatus(BusinessInstance.BusinessStatus.RUNNING);
        }
    }

    private void initDefine(String defineId,String taskName){
        if ((defineId == null) || (taskName == null)){
            throw new IllegalArgumentException("defineId is not allow null !");
        }
        if ((businessDefine != null && DataHelper.isDirty(defineId,businessDefine.getId())) || DataHelper.isDirty(taskName, this.taskName)){
            clearInstance();
        }

        this.taskName = taskName;
        if (businessDefine == null){
            businessDefine = businessDefineRepository.findBy(defineId);
            loadSubscribe();
        }
    }

    private void clearDefine(){
        businessDefine = null;
        editorGroups = null;
        viewGroups = null;
        beforeActions = null;
        afterActions = null;
    }

    public void clearInstance(){
        clearDefine();
        businessInstance = null;
    }

    private void loadSubscribe(){
        editorGroups = new ArrayList<>();
        viewGroups = new ArrayList<>();
        beforeActions = new ArrayList<>();
        afterActions = new ArrayList<>();
        for(SubscribeGroupEntity sge: businessDefine.getSubscribeGroupList()){
            if (sge.getTaskName().equals(taskName)) {
                if (SubscribeGroupEntity.Type.EDITOR.equals(sge.getType())) {
                    editorGroups.add(businessSubscribeConfigService.factoryGroup(sge));
                } else if (SubscribeGroupEntity.Type.VIEW.equals(sge.getType())) {
                    viewGroups.add(businessSubscribeConfigService.factoryGroup(sge));
                }
            }
        }
        for(TaskActionEntity tae: businessDefine.getTaskActionList()){
            if (tae.getTaskName().equals(taskName)){
                if (TaskActionEntity.Type.BEFORE.equals(tae.getType())){
                    beforeActions.add(businessSubscribeConfigService.getAction(tae.getRegName()));
                }else if (TaskActionEntity.Type.AFTER.equals(tae.getType())){
                    afterActions.add(businessSubscribeConfigService.getAction(tae.getRegName()));
                }
            }

        }

    }

    private Integer pageIndex;

    public List<TaskSubscribe> getEditor(){
        return editorGroups.get(pageIndex).getSubscribes();
    }

    public boolean isHasNext(){
        if (pageIndex == null) {
            return isHasEditor();
        }else{
            return (pageIndex + 1) < editorGroups.size();
        }
    }

    public boolean isHasEditor(){
        return !editorGroups.isEmpty();
    }

    public ValidResult taskBegin(){
        return doActionComponent(beforeActions);
    }

    @Transactional
    public ValidResult savePage(){
        if (pageIndex != null) {
            ValidResult result = doActionComponent(getEditor());

            if (result.isPass() && isPersistent()) {
                businessInstanceService.saveEntity(businessInstance);
                persistentEditor(getEditor());
            }
            return result;
        }else
            return new ValidResult(true);
    }

    public void next(){
        if (pageIndex != null){
            pageIndex++;
        }else{
            pageIndex = 0;
        }
        loadPage();
    }

    public void toPage(int page){
        if ((pageIndex == null) || page > pageIndex){
            throw new IllegalArgumentException(" toPage cant to new Page");
        }
        pageIndex = page;
        loadPage();
    }

    private ValidResult doDeleteActions(){
        List<TaskAction> actions = new ArrayList<>();
        for (DeleteActionEntity actionEntity: businessDefine.getDeleteActionList()){
            actions.add(businessSubscribeConfigService.getAction(actionEntity.getRegName()));
        }
        return doActionComponent(actions);
    }

    @Transactional
    public void continueBusiness(){
        //TODO workflow
    }

    @Transactional
    public void terminateBusiness(){
        //TODO workflow
    }

    @Transactional
    public void suspendBusiness(){
        //TODO workflow
    }

    public void assignTo(String empId){
        //TODO workflow
    }

    @Transactional
    public ValidResult revokeBusiness(){
        ValidResult result = doDeleteActions();
        if (result.isPass()){
            businessInstance.setStatus(BusinessInstance.BusinessStatus.DELETED);
            businessInstanceService.saveEntity(businessInstance);
        }
        return result;
    }

    @Transactional
    public ValidResult deleteBusiness(){
        ValidResult result = doDeleteActions();
        if (result.isPass()){
            businessInstanceService.deleteEntity(businessInstance);
        }
        return result;
    }

    @Transactional
    public ValidResult taskComplete(){
        for (TaskSubscribeGroup tsg: editorGroups){
            for(TaskSubscribe ts: tsg.getSubscribes()){
                logger.config("assertion editor subscribe: " + ts);
                try {
                    Iterator<TaskEditSubscribeComponent> it = getSubscribeComponents(ts.getClassName());
                    while (it.hasNext()){
                        if (!it.next().assertion(businessInstance)){
                            throw new IllegalArgumentException("subscribe assertion fail:" + ts.toString());
                        }
                    }
                } catch (ClassNotFoundException e) {
                    logger.log(Level.WARNING,ts.toString() + "subscribe component: " + ts.getClassName() + " not found:",e);
                }
            }
        }
        ValidResult result = doActionComponent(afterActions);
        if (result.isPass()){
            businessInstance.setDataTime(new Date());
            if (DataHelper.empty(businessDefine.getWfName())){
                businessInstance.setStatus(BusinessInstance.BusinessStatus.COMPLETE);
            }
            businessInstanceService.saveEntity(businessInstance);
            persistentAllEditor();
        }
        return result;
    }

    private List<String> getDependencies(List<TaskSubscribe> taskSubscribes){
        List<String> result = new ArrayList<>();
        for (TaskSubscribe ts: taskSubscribes){
            for(String de: ts.getDependencies()){
                if (!result.contains(de)){
                    result.add(de);
                }
            }
        }
        return result;
    }


    public List<String> getEditorDependencies(){
        return getDependencies(getEditor());
    }

    public List<String> getViewDependencies(){
        List<String> result = new ArrayList<>();
        for(TaskSubscribeGroup tsg: getViewGroups()){
            result.addAll(getDependencies(tsg.getSubscribes()));
        }
        return result;
    }

    private ValidResult factoryValidResult(List<ValidMessage> messages){
        ValidResult result = new ValidResult(true);
        if ((messages == null) || messages.isEmpty()){
            return result;
        }
        ValidLevel validLevel = systemParamService.getEnumParam(ValidLevel.class,VALID_LEVEL_PARAM_NAME);

        for(ValidMessage message: messages){
            if (ValidMessage.Level.SERVER.equals(message.getLevel())){
                if (ValidLevel.STRICT.equals(validLevel)){
                    message.setLevel(ValidMessage.Level.OFF);
                }else{
                    message.setLevel(ValidMessage.Level.WARN);
                }
            }
            if (result.pass && ValidMessage.Level.OFF.equals(message.getLevel())){
                result.setPass(false);
            }
            result.getMessages().add(message);
        }
        return result;

    }

    private void loadPage(){
        if (pageIndex < editorGroups.size()){
            for (TaskSubscribe ts: getEditor()){
                if (!DataHelper.empty(ts.getClassName())){
                    try {
                        logger.config("init subscribe component for:" + ts);
                        Iterator<TaskEditSubscribeComponent> it = getSubscribeComponents(ts.getClassName());
                        while (it.hasNext()){
                            it.next().init(businessInstance);
                        }
                    } catch (ClassNotFoundException e) {
                        logger.log(Level.WARNING,ts.toString() + "subscribe component: " + ts.getClassName() + " not found:",e);
                    }

                }
            }
        }
    }

    private void persistentAllEditor(){
        for(TaskSubscribeGroup group: editorGroups){
            persistentEditor(group.getSubscribes());
        }
    }

    private void persistentEditor(List<TaskSubscribe> taskSubscribeList){
        for(TaskSubscribe ts: taskSubscribeList){
            logger.config("call persistent for:" + ts);
            if (!DataHelper.empty(ts.getClassName())) {
                try {
                    Iterator<TaskEditSubscribeComponent> it = getSubscribeComponents(ts.getClassName());
                    while (it.hasNext()) {
                        logger.config("call persistent from component:" + ts.getClassName());
                        it.next().persistent(businessInstance);
                    }
                } catch (ClassNotFoundException e) {
                    logger.log(Level.WARNING, ts.toString() + "persistent subscribe component: " + ts.getClassName() + " not found:", e);
                }
            }
        }
    }


    private ValidResult doActionComponent(List<? extends TaskAction> actions){

        List<ValidMessage> validMessages = new ArrayList<>();
        List<TaskActionComponent> componentList = new ArrayList<>();
        for(TaskAction ta: actions){
            logger.config("vail action for:" + ta);
            if (!DataHelper.empty(ta.getClassName())) {
                try {
                    Iterator<TaskActionComponent> it = getTaskActionComponents(ta.getClassName());
                    while (it.hasNext()) {
                        logger.config("vail action from component:" + ta.getClassName());
                        TaskActionComponent component = it.next();
                        componentList.add(component);
                        validMessages.addAll(component.valid(businessInstance));
                    }
                } catch (ClassNotFoundException e) {
                    logger.log(Level.WARNING, ta.toString() + "action subscribe component: " + ta.getClassName() + " not found:", e);
                }
            }
        }
        ValidResult result = factoryValidResult(validMessages);
        logger.config("do Action vaild: " + result.isPass());
        if (result.isPass()){
            for(TaskActionComponent component: componentList){
                logger.config("do action for:" + component.getClass().getName());
                component.doAction(businessInstance);
            }
        }
        return result;
    }

    @Inject @Any
    private Instance<TaskEditSubscribeComponent> subscribeComponentInstance;

    private Iterator<TaskEditSubscribeComponent> getSubscribeComponents(String className) throws ClassNotFoundException {
        Class clazz = Class.forName(className);
        return subscribeComponentInstance.select(clazz, new SubscribeComponentQualifier()).iterator();
    }

    @Inject @Any
    private Instance<TaskActionComponent> taskActionComponentInstance;

    private Iterator<TaskActionComponent> getTaskActionComponents(String className) throws ClassNotFoundException {
        Class clazz = Class.forName(className);
        return taskActionComponentInstance.select(clazz,new SubscribeComponentQualifier()).iterator();
    }

}
