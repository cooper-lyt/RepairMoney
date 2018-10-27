package cc.coopersoft.framework.controller;

import cc.coopersoft.framework.PageResultData;
import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.data.KeyAndCount;
import cc.coopersoft.framework.data.model.BusinessDefineEntity;
import cc.coopersoft.framework.services.BusinessDefineService;
import cc.coopersoft.framework.services.BusinessInstanceService;
import cc.coopersoft.framework.tools.DataHelper;
import org.omnifaces.cdi.Param;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Named
@RequestScoped
public class BusinessListController {

    private static final int PAGE_SIZE = 10;

    @Inject
    private BusinessInstanceService<? extends BusinessInstance> businessInstanceService;

    @Inject
    private BusinessDefineService businessDefineService;

    @Inject @Param(name = "offset")
    private Integer offset;

    @Inject @Param(name = "condition")
    private String condition;

    @Inject @Param(name = "categoryId")
    private String categoryId;

    @Inject @Param(name = "defineId")
    private String defineId;

    private List<KeyAndCount> businessDefines;

    private PageResultData<BusinessInstance> result;

    private long resultCount;

    public String getDefineId() {
        return defineId;
    }

    public void setDefineId(String defineId) {
        this.defineId = defineId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getOffset() {
        if (offset == null){
            return 0;
        }
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public PageResultData<BusinessInstance> getResult() {
        if (businessDefines == null){
            fillResult();
        }
        return result;
    }

    public long getResultCount() {
        if (result == null){
            fillResult();
        }
        return resultCount;
    }

    public boolean isEmptyData(){
        return getResultCount() == 0;
    }

    public List<KeyAndCount> getBusinessDefines() {
        //TODO count to many change to category
        return businessDefines;
    }

    private List<String> getLimitDefineIds(){
        List<String> result = new ArrayList<>();
        if (DataHelper.empty(categoryId)){
            if (!DataHelper.empty(defineId)){
                result.add(defineId);
            }
        }else{
            //TODO read all define id from category;
        }
        return result;
    }


    private void fillResult(){
        resultCount = businessInstanceService.searchCount(condition,getLimitDefineIds());

        result = new PageResultData<>(new ArrayList<>(businessInstanceService.search(condition,getLimitDefineIds(),getOffset(),PAGE_SIZE)),getOffset(),resultCount,PAGE_SIZE);

        businessDefines = businessInstanceService.searchDefineCount(condition,getLimitDefineIds());
        for(KeyAndCount keyAndCount: businessDefines){
            keyAndCount.setPri(9999);
            for(BusinessDefineEntity e: businessDefineService.findAll()){
                if(keyAndCount.getKey().equals(e.getId())) {
                    keyAndCount.setName(e.getName());
                    keyAndCount.setPri(e.getPriority());
                    break;
                }
            }
        }
        Collections.sort(businessDefines);
    }

}
