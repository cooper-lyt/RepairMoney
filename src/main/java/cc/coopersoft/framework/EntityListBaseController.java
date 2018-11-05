package cc.coopersoft.framework;

import cc.coopersoft.framework.tools.DataHelper;
import org.omnifaces.cdi.Param;

import javax.inject.Inject;


public abstract class EntityListBaseController<E> {

    @Inject
    @Param(name = "offset")
    private Integer offset;

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
