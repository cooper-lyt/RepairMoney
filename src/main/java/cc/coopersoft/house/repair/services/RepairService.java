package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.services.SystemParamService;
import cc.coopersoft.framework.tools.UUIDGenerator;
import cc.coopersoft.house.repair.data.HouseMapId;
import cc.coopersoft.house.repair.data.model.FixingPayEntity;
import cc.coopersoft.house.repair.data.model.HouseAccountEntity;
import cc.coopersoft.house.repair.data.model.RepairBusinessEntity;
import cc.coopersoft.house.repair.data.model.RepairJoinHouseEntity;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RepairService {

    @Inject
    private HouseAccountService houseAccountService;

    @Inject
    private SystemParamService systemParamService;


    public List<RepairJoinHouseEntity> searchAccountJoinRepair(RepairBusinessEntity business, HouseMapId houseMapId){
        List<RepairJoinHouseEntity> result = new ArrayList<>();
        List<HouseAccountEntity> accounts = houseAccountService.search(houseMapId);
        for(HouseAccountEntity account: accounts){
            result.add(new RepairJoinHouseEntity(UUIDGenerator.getUUID(),business,account.getMustMoney(),account.getValidBalance(),account.getHouse()));
        }
        return result;
    }

    public void splitRepairMoney(RepairBusinessEntity business){
        BigDecimal total = BigDecimal.ZERO;
        for(RepairJoinHouseEntity house: business.getRepairJoinHouses()){
            if (RepairBusinessEntity.SplitType.AREA.equals(business.getSplitType())) {
                total = total.add(house.getHouseEntity().getHouseArea());
            }else if (RepairBusinessEntity.SplitType.MONEY.equals(business.getSplitType())){
                total = total.add(house.getMustMoney());
            }else{
                throw new IllegalArgumentException("unknown split type : " + business.getSplitType());
            }
        }

        for(RepairJoinHouseEntity house: business.getRepairJoinHouses()){

            BigDecimal scale;
            if (RepairBusinessEntity.SplitType.AREA.equals(business.getSplitType())) {
                scale = house.getHouseEntity().getHouseArea().divide(total,2, business.getCalcType());
            }else if (RepairBusinessEntity.SplitType.MONEY.equals(business.getSplitType())){
                scale = house.getMustMoney().divide(total,2, business.getCalcType());
            }else{
                throw new IllegalArgumentException("unknown split type : " + business.getSplitType());
            }

            BigDecimal money;
            if (business.isBudget()){
                house.setMoney(business.getBudgetMoney().multiply(scale));
                money = house.getMoney();
            }else{
                house.setApplyMoney(business.getApplyMoney().multiply(scale));
                money = house.getApplyMoney();
            }

            HouseAccountEntity account = houseAccountService.getAccountByHouseCode(house.getHouseEntity().getHouseCode());

            if ((account == null) || HouseAccountEntity.Status.DESTROY.equals(account.getStatus())){
                house.setStatus(RepairJoinHouseEntity.Status.NO_ACCOUNT);
            }else{
                house.setStatus(RepairJoinHouseEntity.Status.SUCCESS);

                BigDecimal minMoney = house.getMustMoney().multiply(systemParamService.getNumberParam("wxzj.accountMinScale").divide(new BigDecimal(100), 2, BigDecimal.ROUND_FLOOR));

                //TODO 预警
                if (house.getBalance().subtract(money).compareTo(BigDecimal.ZERO) < 0){
                    house.setStatus(RepairJoinHouseEntity.Status.NOT_ENOUGH);
                }else if (HouseAccountEntity.Status.LOCKED.equals(account.getStatus())){
                    house.setStatus(RepairJoinHouseEntity.Status.ACCOUNT_LOCK);
                }else if (house.getBalance().subtract(minMoney).subtract(money).compareTo(minMoney) < 0) {
                    house.setStatus(RepairJoinHouseEntity.Status.OUT_MIN);
                }

            }
        }
    }
}
