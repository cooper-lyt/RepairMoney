package cc.coopersoft.house.repair.controller;

import cc.coopersoft.framework.EntityListBaseController;
import cc.coopersoft.house.repair.data.UseTypeCount;
import cc.coopersoft.house.repair.data.model.HouseAccountEntity;
import cc.coopersoft.house.repair.data.model.HouseEntity;
import cc.coopersoft.house.repair.services.HouseAccountService;
import org.omnifaces.cdi.Param;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class HouseAccountListController extends EntityListBaseController<HouseAccountEntity> {

    private static final int PAGE_SIZE = 10;

    @Inject
    private HouseAccountService houseAccountService;

    @Inject
    private HouseSearchCondition houseSearchCondition;

    @Inject @Param(name = "useType")
    private HouseEntity.UseType useType;

    private List<UseTypeCount> useTypeGroup;

    public HouseEntity.UseType getUseType() {
        return useType;
    }

    public void setUseType(HouseEntity.UseType useType) {
        this.useType = useType;
    }

    public List<UseTypeCount> getUseTypeGroup(){
        if (useTypeGroup == null){
            fillResult();
        }
        return useTypeGroup;
    }

    @Override
    protected void fillResult() {
        List<HouseEntity.UseType> useTypes = new ArrayList<>();
        //TODO useType
        resultPage = houseAccountService.search(houseSearchCondition.getCondition(),useTypes,getOffset(),PAGE_SIZE);
        useTypeGroup = houseAccountService.searchUseTypeCount(houseSearchCondition.getCondition(),useTypes);
    }


}
