package cc.coopersoft.framework;

import cc.coopersoft.framework.tools.DataHelper;
import org.omnifaces.cdi.Param;

import javax.inject.Inject;
import java.util.List;


public abstract class EntityListBaseController<E> {

    @Inject
    @Param(name = "offset")
    private Integer offset;

    @Inject @Param(name = "condition")
    private String condition;

    private PageResultData<E> result;

    protected long resultCount;

    protected void fillResult(List<E> resultData, int firstResult, long dataCount, int pageSize){
        result = new PageResultData<>(resultData,firstResult,dataCount,pageSize);
    }

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

    public PageResultData<E> getResult() {
        if (result == null){
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

    protected abstract void fillResult();

    protected void clearResult(){
        result = null;
    }
}
