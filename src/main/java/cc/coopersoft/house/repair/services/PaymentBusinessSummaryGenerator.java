package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.I18n;
import cc.coopersoft.framework.SubscribeComponent;
import cc.coopersoft.framework.data.BusinessInstance;
import cc.coopersoft.framework.data.BusinessSummary;
import cc.coopersoft.framework.services.TaskActionComponent;
import cc.coopersoft.framework.services.ValidMessage;
import cc.coopersoft.framework.tools.DataHelper;
import cc.coopersoft.framework.tools.EnumHelper;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import cc.coopersoft.house.repair.data.model.OwnerPersonEntity;
import cc.coopersoft.house.repair.data.model.PaymentBusinessEntity;
import cc.coopersoft.house.repair.data.model.PaymentEntity;

import javax.inject.Inject;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@SubscribeComponent
public class PaymentBusinessSummaryGenerator implements TaskActionComponent {

    @Inject
    public EnumHelper enumHelper;

    @Inject
    public I18n i18n;

    @Override
    public List<ValidMessage> valid(BusinessInstance businessInstance) {
        return new ArrayList<>(0);
    }

    @Override
    public void doAction(BusinessInstance businessInstance) {
        List<BusinessSummary> result = new ArrayList<>();
        BusinessEntity businessEntity = (BusinessEntity) businessInstance;
        String searchKey = "";
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(i18n.getLocale());
        DecimalFormat areaFormat = new DecimalFormat("#0.00");
        PaymentEntity payment = businessEntity.getPayment();
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
            searchKey += " " + p.getHouse().getMapNumber() + "-" + p.getHouse().getBlockNumber() + "-" + p.getHouse().getBuildNumber() + "-" + p.getHouse().getHouseOrder();
            searchKey += " " + p.getHouse().getHouseAddress() + " " + p.getHouse().getHouseCode();
            for(OwnerPersonEntity o : p.getHouse().getOwnerPersonList()){
                searchKey += " " + o.getName() + " " + o.getCredentialsNumber();
            }
            if (summary == null){

                result.add(BusinessSummary.factorySummary("房屋编号",p.getHouse().getHouseCode(),4));
                result.add(BusinessSummary.factorySummary("测绘标识",p.getHouse().getMapNumber() + "图" + p.getHouse().getBlockNumber() + "丘" + p.getHouse().getBuildNumber() + "幢" + p.getHouse().getHouseOrder() + "房",5));
                result.add(BusinessSummary.factorySummary("建筑面积",areaFormat.format(p.getHouse().getHouseArea()),3));
                result.add(BusinessSummary.factorySummary("房屋座落",p.getHouse().getHouseAddress(),12));
                String owner = "";
                for(OwnerPersonEntity o : p.getHouse().getOwnerPersonList()){
                    if ("".equals(owner)){
                        owner += o.getName() + "[" + o.getCredentialsNumber() + "]";
                    }
                }
                result.add(BusinessSummary.factorySummary("业主",owner,5));
                result.add(BusinessSummary.factorySummary(enumHelper.getLabel(p.getType()),1));
                result.add(BusinessSummary.factorySummary("应缴金额",currencyFormat.format(p.getMustMoney()),3));
                result.add(BusinessSummary.factorySummary("实缴金额",currencyFormat.format(p.getMoney()),3));
            }else if(summary.length() < 400){

                summary += p.getHouse().getHouseOrder() + "[" + p.getHouse().getOwnerPersonList().get(0).getName() + "]、";
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
