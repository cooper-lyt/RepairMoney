package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.I18n;
import cc.coopersoft.framework.data.BusinessSummary;
import cc.coopersoft.framework.tools.EnumHelper;
import cc.coopersoft.house.repair.data.model.AccountDetailsEntity;
import cc.coopersoft.house.repair.data.model.HouseEntity;
import cc.coopersoft.house.repair.data.model.OwnerPersonEntity;

import javax.inject.Inject;
import java.text.DecimalFormat;
import java.util.List;

public class HouseBaseSummaryGenerator {

    private final static int MAX_SUMMARY_LENGTH = 400;

    @Inject
    protected EnumHelper enumHelper;

    @Inject
    protected I18n i18n;

    protected List<BusinessSummary> summaryList;

    protected String searchKey;

    protected String padding(AccountDetailsEntity accountDetails, String value){
        return value;
    }

    private void putSearchKey(HouseEntity house){
        searchKey += " " + house.getMapNumber() + "-" + house.getBlockNumber() + "-" + house.getBuildNumber() + "-" + house.getHouseOrder();
        searchKey += " " + house.getHouseAddress() + " " + house.getHouseCode();
    }

    protected void singleHouse(AccountDetailsEntity accountDetails){
        HouseEntity house = accountDetails.getHouse();
        putSearchKey(house);
        DecimalFormat areaFormat = new DecimalFormat("#0.00");
        summaryList.add(BusinessSummary.factorySummary("房屋编号",house.getHouseCode(),4));
        summaryList.add(BusinessSummary.factorySummary("测绘标识",house.getMapNumber() + "图" + house.getBlockNumber() + "丘" + house.getBuildNumber() + "幢" + house.getHouseOrder() + "房",5));
        summaryList.add(BusinessSummary.factorySummary("建筑面积",areaFormat.format(house.getHouseArea()),3));
        summaryList.add(BusinessSummary.factorySummary("房屋座落",house.getHouseAddress(),12));
        String owner = "";
        for(OwnerPersonEntity o : house.getOwnerPersonList()){
            if ("".equals(owner)){
                owner += o.getName() + "[" + o.getCredentialsNumber() + "]";
            }
            searchKey += " " + o.getName() + " " + o.getCredentialsNumber();
        }
        summaryList.add(BusinessSummary.factorySummary("业主",owner,5));

    }

    protected String multiHouse(List<AccountDetailsEntity> accountDetails) {

        String summary = "";

        for (AccountDetailsEntity details : accountDetails) {
            HouseEntity house = details.getHouse();

            putSearchKey(house);
            for (OwnerPersonEntity o : house.getOwnerPersonList()) {
                searchKey += " " + o.getName() + " " + o.getCredentialsNumber();
                if (summary.length() < MAX_SUMMARY_LENGTH) {
                    summary += padding(details , house.getHouseOrder() + "[" + house.getOwnerPersonList().get(0).getName() + "]") + "、";
                } else {
                    summary += "等 ";
                }

            }
        }
        if (summary.endsWith("、")){
            summary = summary.substring(0,summary.length() - 1);
        }

        summary += "共" + accountDetails.size() + " 户。";
        return summary;
    }


}
