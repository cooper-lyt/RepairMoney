package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.I18n;
import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.data.BusinessSummary;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.framework.tools.DataHelper;
import cc.coopersoft.framework.tools.EnumHelper;
import cc.coopersoft.house.repair.data.model.*;

import javax.inject.Inject;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@SubscribeComponent
public class PaymentBusinessSummaryGenerator implements TaskActionComponent<BusinessEntity> {

    @Inject
    public EnumHelper enumHelper;

    @Inject
    public I18n i18n;

    @Override
    public List<ValidMessage> valid(BusinessEntity businessInstance) {
        return new ArrayList<>(0);
    }

    @Override
    public void doAction(BusinessEntity businessInstance) {
        List<BusinessSummary> result = new ArrayList<>();

        String searchKey = "";
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(i18n.getLocale());
        DecimalFormat areaFormat = new DecimalFormat("#0.00");
        PaymentEntity payment = businessInstance.getPayment();
        if (payment.getBankAccountDetails() != null){
            searchKey += " " + payment.getBankAccountDetails().getBankOperationOrder();
        }
        searchKey += " " + payment.getSectionCode() + " " + payment.getSectionName();
        if (!DataHelper.empty(payment.getPayPerson()))
            searchKey += " " + payment.getPayPerson();

        String summary = null;
        if (payment.getPaymentBusinesses().size() > 2){
            summary = "多户合并交款！";
        }
        for(PaymentBusinessEntity p : payment.getPaymentBusinesses()){
            HouseEntity house = p.getAccountDetails().getHouse();
            searchKey += " " + house.getMapNumber() + "-" + house.getBlockNumber() + "-" + house.getBuildNumber() + "-" + house.getHouseOrder();
            searchKey += " " + house.getHouseAddress() + " " + house.getHouseCode();
            for(OwnerPersonEntity o : house.getOwnerPersonList()){
                searchKey += " " + o.getName() + " " + o.getCredentialsNumber();
            }
            if (summary == null){

                result.add(BusinessSummary.factorySummary("房屋编号",house.getHouseCode(),4));
                result.add(BusinessSummary.factorySummary("测绘标识",house.getMapNumber() + "图" + house.getBlockNumber() + "丘" + house.getBuildNumber() + "幢" + house.getHouseOrder() + "房",5));
                result.add(BusinessSummary.factorySummary("建筑面积",areaFormat.format(house.getHouseArea()),3));
                result.add(BusinessSummary.factorySummary("房屋座落",house.getHouseAddress(),12));
                String owner = "";
                for(OwnerPersonEntity o : house.getOwnerPersonList()){
                    if ("".equals(owner)){
                        owner += o.getName() + "[" + o.getCredentialsNumber() + "]";
                    }
                }
                result.add(BusinessSummary.factorySummary("业主",owner,5));
                result.add(BusinessSummary.factorySummary(enumHelper.getLabel(p.getType()),1));
                result.add(BusinessSummary.factorySummary("应缴金额",currencyFormat.format(p.getMustMoney()),3));
                result.add(BusinessSummary.factorySummary("实缴金额",currencyFormat.format(p.getMoney()),3));
            }else if(summary.length() < 400){

                summary += house.getHouseOrder() + "[" + house.getOwnerPersonList().get(0).getName() + "]、";
            }else{
                summary += "等 ";
            }
        }
        if (summary != null){
            if (summary.endsWith("、")){
                summary = summary.substring(0,summary.length() - 1);
            }
            summary += "共" + payment.getPaymentBusinesses().size() + " 户。";
            result.add(BusinessSummary.factorySummary("物业区域",payment.getSectionName(),12));
            result.add(BusinessSummary.factorySummary("交款人/单位",payment.getPayPerson(),6));
            result.add(BusinessSummary.factorySummary("应缴金额",currencyFormat.format(payment.getMustMoney()),3));
            result.add(BusinessSummary.factorySummary("实缴金额",currencyFormat.format(payment.getMoney()),3));
            result.add(BusinessSummary.factorySummary(summary));
        }

        businessInstance.setSummary(BusinessSummary.parseSummary(result));
        businessInstance.setSearchKey(searchKey);

    }
}
