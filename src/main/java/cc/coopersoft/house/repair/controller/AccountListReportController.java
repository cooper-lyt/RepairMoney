package cc.coopersoft.house.repair.controller;


import cc.coopersoft.framework.tools.EnumHelper;
import cc.coopersoft.house.repair.Messages;
import cc.coopersoft.house.repair.data.model.HouseAccountEntity;
import cc.coopersoft.house.repair.data.model.OwnerPersonEntity;
import cc.coopersoft.house.repair.services.HouseAccountService;
import org.apache.deltaspike.jsf.api.message.JsfMessage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@RequestScoped
public class AccountListReportController {

    @Inject
    private HouseAccountService houseAccountService;

    @Inject
    private EnumHelper enumHelper;

    @Inject
    private FacesContext facesContext;


    @Inject
    private JsfMessage<Messages> messages;


    @Inject
    private Logger logger;

    private String mapNumber;
    private String blockNumber;
    private String buildNumber;

    public String getMapNumber() {
        return mapNumber;
    }

    public void setMapNumber(String mapNumber) {
        this.mapNumber = mapNumber;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }


    public void reportHouseAccount(){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle headCellStyle = workbook.createCellStyle();
        XSSFCellStyle cellStyle = workbook.createCellStyle();


        headCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
        headCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
        XSSFFont font = workbook.createFont();// 创建字体对象
        font.setFontHeightInPoints((short) 12);// 设置字体大小
        headCellStyle.setFont(font);

        int rowIndex = 0;//行
        XSSFSheet sheet = workbook.createSheet("账户列表");

        Row row = sheet.createRow(rowIndex++);


        Cell cell = row.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));
        cell.setCellValue( mapNumber + "图"  + blockNumber + "丘" +  buildNumber + "幢账户列表 " );
        cell.setCellStyle(headCellStyle);

        int cellIndex = 0;
        row = sheet.createRow(rowIndex++);

        cell = row.createCell(cellIndex++);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("房号");

        cell = row.createCell(cellIndex++);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("业主");


        cell = row.createCell(cellIndex++);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("设计用途");

        cell = row.createCell(cellIndex++);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("建筑面积");


        cell = row.createCell(cellIndex++);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("首缴应缴金额");

        cell = row.createCell(cellIndex++);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("账户余额");


        for(HouseAccountEntity account: houseAccountService.listHouseAccount(mapNumber,blockNumber,buildNumber)){
            row = sheet.createRow(rowIndex++);
            cellIndex = 0;

            cell = row.createCell(cellIndex++);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(account.getHouse().getHouseOrder());

            cell = row.createCell(cellIndex++);
            cell.setCellStyle(cellStyle);
            String ownerNameStr = "";
            for (OwnerPersonEntity owner: account.getHouse().getOwnerPersons()){
                if (!"".equals(ownerNameStr)){
                    ownerNameStr += "、";
                }
                ownerNameStr += owner.getName();
            }
            cell.setCellValue(ownerNameStr);

            cell = row.createCell(cellIndex++);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(enumHelper.getLabel(account.getHouse().getUseType()) + " - " + account.getHouse().getDesignType());

            cell = row.createCell(cellIndex++);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(account.getHouse().getHouseArea().doubleValue());


            cell = row.createCell(cellIndex++);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(account.getMustMoney().doubleValue());

            cell = row.createCell(cellIndex++);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(account.getBalance().doubleValue());


        }


        row = sheet.createRow(rowIndex);
        cell = row.createCell(0);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("合计");


        cell = row.createCell(3, Cell.CELL_TYPE_FORMULA);
        cell.setCellStyle(headCellStyle);
        cell.setCellFormula("SUM(" + CellReference.convertNumToColString(3) + "3:" + CellReference.convertNumToColString(3) + rowIndex + ")");


        cell = row.createCell(4, Cell.CELL_TYPE_FORMULA);
        cell.setCellStyle(headCellStyle);
        cell.setCellFormula("SUM(" + CellReference.convertNumToColString(4) + "3:" + CellReference.convertNumToColString(4) + rowIndex + ")");

        cell = row.createCell(5, Cell.CELL_TYPE_FORMULA);
        cell.setCellStyle(headCellStyle);
        cell.setCellFormula("SUM(" + CellReference.convertNumToColString(5) + "3:" + CellReference.convertNumToColString(5) + rowIndex + ")");


        sheet.setForceFormulaRecalculation(true);
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.responseReset();
        externalContext.setResponseContentType("application/vnd.ms-excel");
        externalContext.setResponseHeader("Content-Disposition", "attachment;filename=export" + mapNumber + "-" + blockNumber + "-" + buildNumber + ".xlsx");

        try {
            workbook.write(externalContext.getResponseOutputStream());
            facesContext.responseComplete();
        } catch (IOException e) {
            messages.addError().reportError();
            logger.log(Level.WARNING,"账户列表报表出错！", e);
        }

    }
}
