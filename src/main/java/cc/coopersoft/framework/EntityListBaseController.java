package cc.coopersoft.framework;

import cc.coopersoft.framework.tools.DataHelper;
import org.omnifaces.cdi.Param;

import javax.inject.Inject;


public abstract class EntityListBaseController<E> {

    @Inject
    @Param(name = "offset")
    private Integer offset;

    @Inject @Param(name = "condition")
    private String condition;

    protected EntityDataPage<E> resultPage;


    public Integer getOffset() {
        if (offset == null){
            return 0;
        }
        return offset;
    }

    public void setOffset(Integer offset) {
        if (DataHelper.isDirty(this.offset,offset)){
            clearResult();
        }
        this.offset = offset;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        if (DataHelper.isDirty(this.condition,condition)){
            clearResult();
        }
        this.condition = condition;
    }

    public EntityDataPage<E> getResultPage() {
        if (resultPage == null){
            fillResult();
        }
        return resultPage;
    }


    public boolean isEmptyData(){
        return getResultPage().getDataCount() == 0;
    }

    protected abstract void fillResult();

    protected void clearResult(){
        resultPage = null;
    }
}
