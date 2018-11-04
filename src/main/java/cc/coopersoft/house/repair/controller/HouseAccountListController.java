package cc.coopersoft.house.repair.controller;

import cc.coopersoft.framework.EntityListBaseController;
import cc.coopersoft.house.repair.data.model.HouseAccountEntity;
import org.omnifaces.cdi.Param;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class HouseAccountListController extends EntityListBaseController<HouseAccountEntity> {

    public enum Type{
        IDENTITY,
        PERSON_NAME,
        MAP_ID,
        HOUSE_CODE,
        HOUSE_ADDRESS,
        TEL

    }

    @Inject @Param(name = "map")
    private String mapNumber;

    @Inject @Param(name = "block")
    private String blockNumber;

    @Inject @Param(name = "build")
    private String buildNumber;

    @Inject @Param(name = "house")
    private String houseOrder;

    @Inject @Param(name = "type")
    private Type searchType;

    public String getMapNumber() {
        return mapNumber;
    }

    public void setMapNumber(String mapNumber) {
        this.mapNumber = mapNumber;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getHouseOrder() {
        return houseOrder;
    }

    public void setHouseOrder(String houseOrder) {
        this.houseOrder = houseOrder;
    }

    public Type getSearchType() {
        return searchType;
    }

    public void setSearchType(Type searchType) {
        this.searchType = searchType;
    }

    @Override
    protected void fillResult() {
        resultCount = 0;
        fillResult();

    }


}
