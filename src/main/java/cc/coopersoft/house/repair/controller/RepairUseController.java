package cc.coopersoft.house.repair.controller;

import cc.coopersoft.framework.controller.BusinessOperationController;
import cc.coopersoft.house.repair.Messages;
import cc.coopersoft.house.repair.data.model.HouseAccountEntity;
import cc.coopersoft.house.repair.data.HouseMapId;
import cc.coopersoft.house.repair.services.HouseAccountService;
import org.apache.deltaspike.core.api.scope.ViewAccessScoped;
import org.apache.deltaspike.jsf.api.message.JsfMessage;
import org.omnifaces.cdi.Param;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewAccessScoped
public class RepairUseController implements java.io.Serializable{

    @Inject
    private BusinessOperationController businessOperationController;

    @Inject
    private JsfMessage<Messages> messages;

    @Inject
    private HouseAccountService houseAccountService;

    @Inject @Param(name = "selectAccountId")
    private String selectAccountId;

    private HouseMapId houseMapId = new HouseMapId();

    private List<HouseAccountEntity> joinHouseAccount = new ArrayList<>();

    //public UseBusinessEntity useBusinessEntity


    public String getSelectAccountId() {
        return selectAccountId;
    }

    public void setSelectAccountId(String selectAccountId) {
        this.selectAccountId = selectAccountId;
    }

    public HouseMapId getHouseMapId() {
        return houseMapId;
    }

    public List<HouseAccountEntity> getJoinHouseAccount() {
        return joinHouseAccount;
    }

    public void join(){
        List<HouseAccountEntity> result = houseAccountService.search(houseMapId);
        if (result.isEmpty()){
            messages.addWarn().accountNotFound();
        }else{
            joinHouseAccount.addAll(result);
            messages.addInfo().accountJoin(result.size());
        }
    }

    public void remove(){
        for(HouseAccountEntity account: joinHouseAccount){
            if (account.getAccountNumber().equals(selectAccountId)){
                joinHouseAccount.remove(account);
                break;
            }
        }
    }

    public void clear(){
        joinHouseAccount.clear();
    }

}
