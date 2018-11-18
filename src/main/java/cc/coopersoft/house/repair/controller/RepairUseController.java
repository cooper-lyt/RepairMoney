package cc.coopersoft.house.repair.controller;

import cc.coopersoft.framework.controller.BusinessOperationController;
import cc.coopersoft.house.repair.Messages;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.HouseMapId;
import cc.coopersoft.house.repair.data.model.RepairBusinessEntity;
import cc.coopersoft.house.repair.data.model.RepairJoinHouseEntity;
import cc.coopersoft.house.repair.services.RepairService;
import org.apache.deltaspike.jsf.api.message.JsfMessage;
import org.omnifaces.cdi.Param;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

@Named
@RequestScoped
public class RepairUseController implements java.io.Serializable{

    @Inject
    private BusinessOperationController businessOperationController;

    @Inject
    private JsfMessage<Messages> messages;

    @Inject
    private RepairService repairService;

    @Inject @Param(name = "selectHouseId")
    private String selectHouseId;

    @Inject
    private Logger logger;

    private HouseMapId houseMapId = new HouseMapId();

    private List<RepairJoinHouseEntity>  joinHouses;

    public RepairBusinessEntity getRepairBusiness(){
        return ((BusinessEntity)businessOperationController.getBusinessInstance()).getRepair();
    }

    public HouseMapId getHouseMapId() {
        return houseMapId;
    }

    public List<RepairJoinHouseEntity> getJoinHouseAccount() {
        if (joinHouses == null){
            joinHouses = getRepairBusiness().getRepairJoinHouseList();
        }
        return joinHouses;
    }

    public BigDecimal getTotalMoney(){
        BigDecimal result = BigDecimal.ZERO;
        for(RepairJoinHouseEntity house: getJoinHouseAccount()){
            if (getRepairBusiness().isBudget()){
                result = result.add(house.getMoney());
            }else {
                result = result.add(house.getApplyMoney());
            }
        }
        return result;
    }

    public void join(){
        List<RepairJoinHouseEntity> result = repairService.searchAccountJoinRepair(getRepairBusiness(),houseMapId);


        int count = 0;
        for(RepairJoinHouseEntity house: result){
            if (getJoinHouseAccount().add(house)){
                house.setPriority(getJoinHouseAccount().size());
                count++;
            }
        }
        if (count > 0 ){
            repairService.splitRepairMoney(getRepairBusiness());
            messages.addInfo().accountJoin(count);
        }else{
            messages.addWarn().accountNotFound();
        }
    }

    public void remove(){
        logger.config("prepare remove house:" + selectHouseId);
        for(RepairJoinHouseEntity joinHouse: getJoinHouseAccount()){
            if (joinHouse.getId().equals(selectHouseId)){
                getJoinHouseAccount().remove(joinHouse);
                logger.config("house:" + selectHouseId + " is removed!");
                repairService.splitRepairMoney(getRepairBusiness());
                return;
            }
        }

    }

    public void clear(){
        getJoinHouseAccount().clear();
        repairService.splitRepairMoney(getRepairBusiness());
    }

    public void splitMoney(){
        repairService.splitRepairMoney(getRepairBusiness());
    }

}
