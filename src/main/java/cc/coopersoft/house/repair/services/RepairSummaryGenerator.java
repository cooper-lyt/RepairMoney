package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.I18n;
import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.data.BusinessSummary;
import cc.coopersoft.framework.services.SubscribeFailException;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.framework.tools.DataHelper;
import cc.coopersoft.house.repair.data.model.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@SubscribeComponent
public class RepairSummaryGenerator implements TaskActionComponent<BusinessEntity> {

    private final static int MAX_SUMMARY_LENGTH = 400;

    @Inject
    private I18n i18n;

    @Override
    public void doAction(BusinessEntity business, boolean persistent) throws SubscribeFailException {
        List<BusinessSummary> summaryList = new ArrayList<>();


        RepairBusinessEntity repair = business.getRepair();

        String searchKey = repair.getName() + " " + repair.getApplyTel() + " " + repair.getApplyGroup() + " " + repair.getSectionName();

        summaryList.add(new BusinessSummary(repair.getName(),12));

        summaryList.add(new BusinessSummary("物业区域",repair.getSectionName(),6));
        summaryList.add(new BusinessSummary("申报单位",repair.getApplyGroup() + " " + (DataHelper.empty(repair.getApplyTel()) ? "" : repair.getApplyTel()),6));
        summaryList.add(new BusinessSummary("预算金额", i18n.currencyDisplay(repair.getApplyMoney()),4));

        summaryList.add(new BusinessSummary("决算金额", repair.isBudget() ?  i18n.currencyDisplay(repair.getApplyMoney())  : "未进行决算",4));
        summaryList.add(new BusinessSummary("划拨金额", i18n.currencyDisplay(repair.getPaymentMoney()),4));


        for(FixingPayEntity pay: repair.getFixingPays()){
            searchKey += " " + pay.getReceivePerson() + " " + pay.getReceiveNumber() + " " + pay.getReceiveName() + " " + pay.getDescription() ;
        }

        String summary = "";
        for(RepairJoinHouseEntity house: repair.getRepairJoinHouses()){

            searchKey += " " + house.getHouseEntity().getHouseCode() +
                    " " + house.getHouseEntity().getMapNumber() + "-" + house.getHouseEntity().getBlockNumber() + "-" + house.getHouseEntity().getBuildNumber() + "-" + house.getHouseEntity().getHouseOrder() +
                    " " + house.getHouseEntity().getHouseAddress();
            for(OwnerPersonEntity owner: house.getHouseEntity().getOwnerPersons()){
                searchKey += " " + owner.getName() + " " + owner.getCredentialsNumber();
            }

            if (summary.length() < MAX_SUMMARY_LENGTH) {
                summary += house.getHouseEntity().getHouseOrder() + "[" + (house.getHouseEntity().getOwnerPersons().isEmpty() ? "" : house.getHouseEntity().getOwnerPersonList().get(0).getName()) + "]、";
            } else {
                summary += "等 ";
            }
        }

        if (summary.endsWith("、")){
            summary = summary.substring(0,summary.length() - 1);
        }
        summaryList.add(new BusinessSummary("列支范围", summary, 12));

        business.setSummary(BusinessSummary.parseSummary(summaryList));
        business.setSearchKey(searchKey);
    }
}
