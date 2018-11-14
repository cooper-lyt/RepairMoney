package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.services.SubscribeFailException;
import cc.coopersoft.framework.services.TaskEditSubscribeComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.framework.tools.EnumHelper;
import cc.coopersoft.framework.tools.UUIDGenerator;
import cc.coopersoft.house.repair.data.AccountOperationDirection;
import cc.coopersoft.house.repair.data.PaymentType;
import cc.coopersoft.house.repair.data.model.*;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SubscribeComponent
public class RepairMakeupSplitService implements TaskEditSubscribeComponent<BusinessEntity> {

    @Inject
    private EnumHelper enumHelper;

    @Inject
    private HouseAccountService houseAccountService;

    @Override
    public boolean init(BusinessEntity business) {
         return true;
    }

    @Override
    public void doCreate(BusinessEntity business) {

    }

    @Override
    public void doAction(BusinessEntity business, boolean persistent) throws SubscribeFailException {

        for(RepairJoinHouseEntity house: business.getRepair().getRepairJoinHouses()){
            house.setMoney(house.getApplyMoney());
        }
        business.getRepair().setBudget(true);

        FixingPayEntity pay = new FixingPayEntity(UUIDGenerator.getUUID(),FixingPayEntity.Status.COMPLETE,business.getRepair());
        pay.setType(FixingPayEntity.Type.PAY);

        pay.setReceivePerson("  ");//TODO edit
        pay.setPayTime(business.getRepair().getApplyDate());//TODO edit
        pay.setPaymentType(PaymentType.CASH);//TODO edit

        business.getRepair().getFixingPays().add(pay);
        BigDecimal money = BigDecimal.ZERO;
        for(RepairJoinHouseEntity house : business.getRepair().getRepairJoinHouses()){
            if (business.getRepair().getSectionCode() == null){
                business.getRepair().setSectionCode(house.getHouseEntity().getSectionCode());//TODO edit
                business.getRepair().setSectionName(house.getHouseEntity().getSectionName());//TODO edit
            }
            AccountDetailsEntity details = new AccountDetailsEntity(business,AccountOperationDirection.OUT,UUIDGenerator.getUUID());
            business.getAccountDetails().add(details);
            details.setOperationTime(pay.getPayTime());
            details.setMoney(house.getMoney());
            HouseAccountEntity account =  houseAccountService.getAccountByHouseCode(house.getHouseEntity().getHouseCode());
            details.setHouseAccount(account);
            details.setHouse(house.getHouseEntity());
            details.setStatus(AccountDetailsEntity.Status.RUNNING);

            pay.getAccountDetails().add(details);
            money = money.add(house.getMoney());
        }

        pay.setPayMoney(money);


        //TODO change this
        business.setRegTime(business.getRepair().getApplyDate());
        business.setReg(true);
        business.setSource(BusinessInstance.Source.BEFORE_INPUT);

    }

    @Override
    public List<ValidMessage> valid(BusinessEntity business) {
        List<ValidMessage> result = new ArrayList<>();
        if (business.getRepair().getRepairJoinHouses().isEmpty()){
            result.add(new ValidMessage(ValidMessage.Level.OFF,"请添加房屋！","未选择任何房屋"));
        }
        for(RepairJoinHouseEntity house: business.getRepair().getRepairJoinHouses()){
            if (RepairJoinHouseEntity.Status.ACCOUNT_WARN.ordinal() < house.getStatus().ordinal()) {
                result.add(new ValidMessage(ValidMessage.Level.OFF,"房屋:" + house.getHouseEntity().getHouseCode() + enumHelper.getLabel(house.getStatus()),null));
                return result;
            }
        }
        return result;
    }
}
