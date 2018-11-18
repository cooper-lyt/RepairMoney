package cc.coopersoft.framework.services;

import cc.coopersoft.framework.SubscribeComponentQualifier;
import cc.coopersoft.framework.data.*;
import cc.coopersoft.framework.data.model.BusinessDefineEntity;
import cc.coopersoft.framework.data.model.SubscribeGroupEntity;
import cc.coopersoft.framework.data.model.TaskActionEntity;
import cc.coopersoft.framework.data.repository.BusinessDefineRepository;
import cc.coopersoft.framework.tools.DataHelper;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@ConversationScoped
public class BusinessOperationService implements java.io.Serializable {


    public final static String CREATE_TASK_NAME = "_CREATE";
    public final static String VIEW_TASK_NAME = "_VIEW";

    private final static String VALID_LEVEL_PARAM_NAME = "framework.business.validLevel";
    private final static String BUSINESS_NUMBER_POOL_ID = "business_id";


    @Inject
    private Logger logger;

    @Inject
    private BusinessDefineRepository businessDefineRepository;

    @Inject
    private BusinessSubscribeConfigService businessSubscribeConfigService;

    @Inject
    private BusinessInstanceService businessInstanceService;

    @Inject
    private NumberService numberService;

    @Inject
    private SystemParamService systemParamService;

    @Inject
    private LoginUser loginUser;

    //private String defineId;

    private String taskName;

    private BusinessInstance businessInstance;

    private BusinessDefineEntity businessDefine;

    private List<TaskSubscribeGroup> editorGroups;

    private List<TaskSubscribeGroup> viewGroups;




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

    public BusinessInstance createInstance(String defineId, String taskName){
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
        return businessInstance;
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
    }

    public void clearInstance(){
        clearDefine();
        businessInstance = null;
    }

    private List<TaskAction> getActions(TaskActionEntity.Type type, TaskActionEntity.Position position){
        List<TaskAction> result = new ArrayList<>();
        for(TaskActionEntity taskActionEntity: businessDefine.getTaskActionList()){
            if (taskName.equals(taskActionEntity.getTaskName())) {
                if (type.equals(taskActionEntity.getType()) && position.equals(taskActionEntity.getPosition())) {
                    if (TaskActionEntity.Type.ACTION.equals(type)) {
                        result.add(businessSubscribeConfigService.getAction(taskActionEntity.getRegName()));
                    } else if (TaskActionEntity.Type.VALID.equals(type)) {
                        result.add(businessSubscribeConfigService.getValid(taskActionEntity.getRegName()));
                    }
                }
            }
        }
        return result;
    }

    private void loadSubscribe(){
        editorGroups = new ArrayList<>();
        viewGroups = new ArrayList<>();
        for(SubscribeGroupEntity sge: businessDefine.getSubscribeGroupList()){
            if (sge.getTaskName().equals(taskName)) {
                if (SubscribeGroupEntity.Type.EDITOR.equals(sge.getType())) {
                    editorGroups.add(businessSubscribeConfigService.factoryGroup(sge));
                } else if (SubscribeGroupEntity.Type.VIEW.equals(sge.getType())) {
                    viewGroups.add(businessSubscribeConfigService.factoryGroup(sge));
                }
            }
        }
    }

    private Integer pageIndex;

    public List<TaskSubscribe> getEditor(){
        return editorGroups.get(pageIndex).getSubscribes();
    }



    public SubscribeValidResult taskBegin() throws SubscribeFailException {
        SubscribeValidResult result = doValidationsComponent(getActions(TaskActionEntity.Type.VALID,TaskActionEntity.Position.BEFORE));
        if (result.isPass()){
            doActionComponent(getActions(TaskActionEntity.Type.ACTION,TaskActionEntity.Position.BEFORE),isPersistent());
        }
        return result;
    }

    @Transactional
    public SubscribeValidResult savePage() throws SubscribeFailException {
        if (pageIndex != null) {
            SubscribeValidResult result = doValidationsComponent(getEditor());
            if (result.isPass()) {
                doActionComponent(getEditor(),isPersistent());
                if (isPersistent()){
                    businessInstanceService.saveEntity(businessInstance);
                }
            }

            return result;
        }else
            return factoryValidResult();
    }

    public boolean next(){
        if (isHasNext()) {
            if (pageIndex != null) {
                pageIndex++;
            } else {
                pageIndex = 0;
            }

            if (loadPage()){
                return true;
            }else{
                return next();
            }
        }else{
            return false;
        }
    }

    public boolean toPage(int page){
        if ((pageIndex == null) || page > pageIndex || ((page + 1) >= editorGroups.size())){
            throw new IllegalArgumentException(" toPage cant to new Page");
        }
        pageIndex = page;
        if (loadPage()){
            return true;
        }else{
            return next();
        }
    }

    public boolean isHasPrevious(){
        return (pageIndex != null) && (pageIndex > 0);
    }

    public boolean previous(){
        return previous(pageIndex);
    }

    private boolean isHasNext(){
        if (pageIndex == null) {
            return isHasEditor();
        }else{
            return (pageIndex + 1) < editorGroups.size();
        }
    }

    private boolean isHasEditor(){
        return !editorGroups.isEmpty();
    }

    private boolean previous(Integer currentPage){
        if (isHasPrevious()){
            pageIndex = pageIndex - 1;
            if (loadPage()){
                return true;
            }else{
                return previous(currentPage);
            }
        }else{
            pageIndex = currentPage;
            return false;
        }
    }

    @Transactional
    public void continueBusiness(){
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
    public SubscribeValidResult abortBusiness() throws SubscribeFailException {
        SubscribeValidResult result = doValidationsComponent(getActions(TaskActionEntity.Type.VALID,TaskActionEntity.Position.ABORT));
        if (result.isPass()){
            doActionComponent(getActions(TaskActionEntity.Type.ACTION,TaskActionEntity.Position.ABORT),true);
            businessInstance.setStatus(BusinessInstance.BusinessStatus.ABORT);
            putLog(BusinessOperationLog.Type.ABORT);
            businessInstanceService.saveEntity(businessInstance);
        }
        return result;
    }

    @Transactional
    public SubscribeValidResult revokeBusiness() throws SubscribeFailException {
        SubscribeValidResult result = doValidationsComponent(getActions(TaskActionEntity.Type.VALID,TaskActionEntity.Position.REVOKE));
        if (result.isPass()){
            doActionComponent(getActions(TaskActionEntity.Type.ACTION,TaskActionEntity.Position.REVOKE),true);
            businessInstance.setStatus(BusinessInstance.BusinessStatus.DELETED);
            businessInstance.setReg(false);
            putLog(BusinessOperationLog.Type.DELETE);
            businessInstanceService.saveEntity(businessInstance);
        }
        return result;
    }

    @Transactional
    public SubscribeValidResult deleteBusiness() throws SubscribeFailException {
        SubscribeValidResult result = doValidationsComponent(getActions(TaskActionEntity.Type.VALID,TaskActionEntity.Position.DELETE));
        if (result.isPass()){
            doActionComponent(getActions(TaskActionEntity.Type.ACTION,TaskActionEntity.Position.DELETE),true);
            businessInstanceService.deleteEntity(businessInstance);
        }

        return result;
    }

    private BusinessOperationLog putLog(BusinessOperationLog.Type type){
        BusinessOperationLog log = businessInstanceService.createOperationLog();
        log.setOperationTime(new Date());
        log.setEmpCode(loginUser.getUseId());
        log.setEmpName(loginUser.getName());
        log.setTaskName(taskName);
        log.setType(type);
        businessInstanceService.putOperationLog(businessInstance,log);
        return log;
    }

    @Transactional
    public SubscribeValidResult taskComplete() throws SubscribeFailException {
        SubscribeValidResult result = doValidationsComponent(getActions(TaskActionEntity.Type.VALID,TaskActionEntity.Position.AFTER));
        if (result.isPass()){
            doActionComponent(getActions(TaskActionEntity.Type.ACTION,TaskActionEntity.Position.AFTER),true);

            businessInstance.setDataTime(new Date());
            if (DataHelper.empty(businessDefine.getWfName())) {
                businessInstance.setStatus(BusinessInstance.BusinessStatus.COMPLETE);
            }


            if (isPersistent()) {
                //TODO IS CHECK pass, check fail , back , task continue;
            }else {
                putLog(BusinessOperationLog.Type.CREATE);

            }
            businessInstanceService.saveEntity(businessInstance);



            if (!isPersistent()) {
                persistentEditor();
            }



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

    private SubscribeValidResult factoryValidResult(){
        return new SubscribeValidResult(systemParamService.getEnumParam(SubscribeValidResult.ValidLevel.class,VALID_LEVEL_PARAM_NAME));
    }

    private boolean loadPage(){
        boolean result = false;
        if (pageIndex < editorGroups.size()){
            for (TaskSubscribe ts: getEditor()){
                if (!DataHelper.empty(ts.getClassName())){
                    try {
                        logger.config("init subscribe component for:" + ts);
                        Iterator<TaskEditSubscribeComponent> it = getSubscribeComponents(ts.getClassName());
                        while (it.hasNext()){
                            if (it.next().init(businessInstance)){
                                result = true;
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        logger.log(Level.WARNING,ts.toString() + "subscribe component: " + ts.getClassName() + " not found:",e);
                    }

                }
            }
        }
        return result;
    }

    private void persistentEditor(){
        for(TaskSubscribeGroup group: editorGroups) {
            for (TaskSubscribe ts : group.getSubscribes()) {
                logger.config("call persistent for:" + ts);
                if (!DataHelper.empty(ts.getClassName())) {
                    try {
                        Iterator<TaskEditSubscribeComponent> it = getSubscribeComponents(ts.getClassName());
                        while (it.hasNext()) {
                            TaskEditSubscribeComponent component = it.next();
                            if (ts.getClassName().equals(component.getClass().getName())) {
                                logger.config("call persistent from component:" + component.getClass().getName());
                                component.doCreate(businessInstance);
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        logger.log(Level.WARNING, ts.toString() + "persistent subscribe component: " + ts.getClassName() + " not found:", e);
                    }
                }
            }
        }
    }


    private SubscribeValidResult doValidationsComponent(List<? extends TaskAction> validations) throws SubscribeFailException {
        SubscribeValidResult result = factoryValidResult();
        for(TaskAction ta: validations){
            logger.config("valid for:" + ta);
            if (!DataHelper.empty(ta.getClassName())) {
                try {
                    Iterator<TaskValidComponent> it = getTaskValidComponents(ta.getClassName());
                    while (it.hasNext()) {

                        TaskValidComponent component = it.next();
                        logger.config("valid from component:" + component.getClass().getName());
                        if (ta.getClassName().equals(component.getClass().getName())) {
                            result.putMessages(component.valid(businessInstance));
                            if (!result.canContinue()) {
                                throw new SubscribeFailException(result);
                            }
                        }
                    }
                } catch (ClassNotFoundException e) {
                    logger.log(Level.WARNING, ta.toString() + "valid subscribe component: " + ta.getClassName() + " not found:", e);
                }
            }
        }
        return result;
    }


    private void doActionComponent(List<? extends TaskAction> actions , boolean persistent) throws SubscribeFailException {

        //ValidResult result = factoryValidResult();
        for(TaskAction ta: actions) {
            logger.config("do action for:" + ta);
            if (!DataHelper.empty(ta.getClassName())) {
                try {
                    Iterator<TaskActionComponent> it = getTaskActionComponents(ta.getClassName());
                    while (it.hasNext()){
                        TaskActionComponent component = it.next();
                        if (ta.getClassName().equals(component.getClass().getName())){
                            component.doAction(businessInstance, persistent);
                        }
                    }
                } catch (ClassNotFoundException e) {
                    logger.log(Level.WARNING, ta.toString() + "action subscribe component: " + ta.getClassName() + " not found:", e);
                }
            }
        }

        //return result;
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

    @Inject @Any
    private Instance<TaskValidComponent> taskValidComponentInstance;

    private Iterator<TaskValidComponent> getTaskValidComponents(String className) throws ClassNotFoundException{
        Class clazz = Class.forName(className);
        return taskActionComponentInstance.select(clazz,new SubscribeComponentQualifier()).iterator();
    }

}
