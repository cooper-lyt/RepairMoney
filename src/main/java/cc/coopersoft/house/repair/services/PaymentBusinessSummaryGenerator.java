package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.data.BusinessSummary;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.framework.tools.DataHelper;
import cc.coopersoft.house.repair.data.model.PaymentEntity;
import cc.coopersoft.house.repair.data.model.*;

import java.util.ArrayList;
import java.util.List;

@SubscribeComponent
public class PaymentBusinessSummaryGenerator extends HouseBaseSummaryGenerator implements TaskActionComponent<BusinessEntity> {



    @Override
    public void doAction(BusinessEntity businessInstance, boolean persistent) {
        summaryList = new ArrayList<>();
        searchKey = "";
        PaymentEntity payment = businessInstance.getPayment();
        if (payment.getBankAccountDetails() != null){
            searchKey += " " + payment.getBankAccountDetails().getBankOperationOrder();
        }
        searchKey += " " + payment.getSectionCode() + " " + payment.getSectionName();
        if (!DataHelper.empty(payment.getPayPerson()))
            searchKey += " " + payment.getPayPerson();

        if(payment.getPaymentBusinesses().size() > 1){
            String summary = "多户合并交款！";
            List<AccountDetailsEntity> accountDetails = new ArrayList<>();
            for(PaymentBusinessEntity p : payment.getPaymentBusinesses()){
                accountDetails.add(p.getAccountDetails());
            }
            summary += multiHouse(accountDetails);

            summaryList.add(BusinessSummary.factorySummary("物业区域",payment.getSectionName(),12));
            summaryList.add(BusinessSummary.factorySummary("交款人/单位",payment.getPayPerson(),6));
            summaryList.add(BusinessSummary.factorySummary("应缴金额",i18n.currencyDisplay(payment.getMustMoney()),3));
            summaryList.add(BusinessSummary.factorySummary("实缴金额",i18n.currencyDisplay(payment.getMoney()),3));
            summaryList.add(BusinessSummary.factorySummary(summary));

        }else if (!payment.getPaymentBusinesses().isEmpty()){

            PaymentBusinessEntity p = payment.getPaymentBusinesses().iterator().next();
            singleHouse(p.getAccountDetails());
            summaryList.add(BusinessSummary.factorySummary(enumHelper.getLabel(p.getType()),1));
            summaryList.add(BusinessSummary.factorySummary("应缴金额",i18n.currencyDisplay(p.getMustMoney()),3));
            summaryList.add(BusinessSummary.factorySummary("实缴金额",i18n.currencyDisplay(p.getMoney()),3));
        }


        businessInstance.setSummary(BusinessSummary.parseSummary(summaryList));
        businessInstance.setSearchKey(searchKey);

    }
}
