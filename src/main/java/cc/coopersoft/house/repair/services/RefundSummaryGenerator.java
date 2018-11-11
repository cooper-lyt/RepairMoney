package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.data.BusinessSummary;
import cc.coopersoft.framework.services.SubscribeFailException;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.framework.tools.DataHelper;
import cc.coopersoft.house.repair.data.model.*;

import java.util.ArrayList;
import java.util.List;

@SubscribeComponent
public class RefundSummaryGenerator extends HouseBaseSummaryGenerator implements TaskActionComponent<BusinessEntity> {

    @Override
    protected String padding(AccountDetailsEntity accountDetails, String value){
        return value + ":" + i18n.currencyDisplay(accountDetails.getMoney());
    }

    @Override
    public void doAction(BusinessEntity business, boolean persistent) throws SubscribeFailException {
        summaryList = new ArrayList<>();

        searchKey = "";

        RefundBusinessEntity refund = business.getRefund();

        if (refund.getBankAccountDetails() != null){
            searchKey += " " + refund.getBankAccountDetails().getBankOperationOrder();
        }

        List<AccountDetailsEntity> accountDetails = new ArrayList<>();
        if (refund.getAccountDetails() != null){
            accountDetails.add(refund.getAccountDetails());
        }


        if (accountDetails.size() > 1){
            summaryList.add(BusinessSummary.factorySummary("多账户退款！" + multiHouse(accountDetails),12));
        }else if (!accountDetails.isEmpty()){
            singleHouse(accountDetails.get(0));
        }else{
            throw new SubscribeFailException("没有选择任何账户！","");
        }


        String summary = enumHelper.getLabel(refund.getType());
        if (RefundBusinessEntity.Type.OTHER.equals(refund.getType()) && !DataHelper.empty(refund.getReason())){
            summary += refund.getReason();
        }

        summaryList.add(BusinessSummary.factorySummary("退款原因",summary,9));
        summaryList.add(BusinessSummary.factorySummary("退款金额",i18n.currencyDisplay(refund.getMoney()),3));

        business.setSummary(BusinessSummary.parseSummary(summaryList));
        business.setSearchKey(searchKey);
    }
}
