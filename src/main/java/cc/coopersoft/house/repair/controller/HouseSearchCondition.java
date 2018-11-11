package cc.coopersoft.house.repair.controller;

import cc.coopersoft.house.repair.data.HouseMapId;
import cc.coopersoft.house.repair.services.HouseAccountService;
import org.omnifaces.cdi.Param;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class HouseSearchCondition implements java.io.Serializable{


    @Inject @Param(name = "condition")
    private String condition;

    @Inject @Param(name = "map")
    private String mapNumber;

    @Inject @Param(name = "block")
    private String blockNumber;

    @Inject @Param(name = "build")
    private String buildNumber;

    @Inject @Param(name = "house")
    private String houseOrder;

    @Inject @Param(name = "type")
    private HouseAccountService.Type type;

    public HouseAccountService.Type[] getAllType(){
        return HouseAccountService.Type.values();
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

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

    public HouseAccountService.Type getType() {
        return type;
    }

    public void setType(HouseAccountService.Type type) {
        this.type = type;
    }

    public HouseMapId getHouseMapId(){
        return new HouseMapId(mapNumber,blockNumber,buildNumber,houseOrder);
    }
}
