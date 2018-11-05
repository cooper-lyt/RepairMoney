package cc.coopersoft.framework.controller;

import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.data.KeyAndCount;
import cc.coopersoft.framework.services.BusinessInstanceService;
import cc.coopersoft.framework.tools.DataHelper;
import org.omnifaces.cdi.Param;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class BusinessListController extends cc.coopersoft.framework.EntityListBaseController<BusinessInstance> {

    private static final int PAGE_SIZE = 10;

    @Inject
    private BusinessInstanceService businessInstanceService;

    @Inject @Param(name = "condition")
    private String condition;

    @Inject @Param(name = "categoryId")
    private String categoryId;

    @Inject @Param(name = "defineId")
    private String defineId;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    private List<KeyAndCount> businessDefines;

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

    public List<KeyAndCount> getBusinessDefines() {
        //TODO count to many change to category
        if (businessDefines == null){
            fillResult();
        }
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


    @Override
    protected void fillResult(){
        resultPage = businessInstanceService.search(getCondition(),getLimitDefineIds(),getOffset(),PAGE_SIZE);
        businessDefines = businessInstanceService.searchDefineCount(getCondition(),getLimitDefineIds());
    }

}
