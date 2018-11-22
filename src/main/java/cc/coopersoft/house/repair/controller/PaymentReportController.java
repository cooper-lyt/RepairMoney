package cc.coopersoft.house.repair.controller;

import cc.coopersoft.framework.I18n;
import cc.coopersoft.house.repair.Messages;
import cc.coopersoft.house.repair.data.model.PaymentBusinessEntity;
import cc.coopersoft.house.repair.services.PaymentTotalService;
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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@RequestScoped
public class PaymentReportController {

    @Inject
    private PaymentTotalService paymentTotalService;

    @Inject
    private FacesContext facesContext;

    @Inject
    private I18n i18n;

    @Inject
    private Logger logger;

    @Inject
    private JsfMessage<Messages> messages;

    private Date searchDate = new Date();

    public Date getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }

    public void paymentDayReport(){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle headCellStyle = workbook.createCellStyle();
        XSSFCellStyle cellStyle = workbook.createCellStyle();


        headCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
        headCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
        XSSFFont font = workbook.createFont();// 创建字体对象
        font.setFontHeightInPoints((short) 12);// 设置字体大小
        headCellStyle.setFont(font);

        int rowIndex = 0;//行
        XSSFSheet sheet = workbook.createSheet("维修资金收缴日报表");

        Row row = sheet.createRow(rowIndex++);


        Cell cell = row.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));
        cell.setCellValue("维修资金收缴日报表 " + i18n.dateDisplay(searchDate));
        cell.setCellStyle(headCellStyle);

        int cellIndex = 0;
        row = sheet.createRow(rowIndex++);

        cell = row.createCell(cellIndex++);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("缴费时间");

        cell = row.createCell(cellIndex++);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("业务编号");

        cell = row.createCell(cellIndex++);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("票号");

        cell = row.createCell(cellIndex++);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("业主");

        cell = row.createCell(cellIndex++);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("应缴金额");


        cell = row.createCell(cellIndex++);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("实缴金额");

        List<PaymentBusinessEntity> data = paymentTotalService.paymentDayReport(searchDate);

        if (!data.isEmpty()) {
            for (PaymentBusinessEntity pay : data) {
                row = sheet.createRow(rowIndex++);
                cellIndex = 0;

                cell = row.createCell(cellIndex++);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(pay.getAccountDetails().getOperationTime());

                cell = row.createCell(cellIndex++);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(pay.getId());


                cell = row.createCell(cellIndex++);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(pay.getReceiptNumber());


                cell = row.createCell(cellIndex++);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(pay.getAccountDetails().getHouse().getFirstOwnerPerson().getName());

                cell = row.createCell(cellIndex++);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(pay.getMustMoney().doubleValue());

                cell = row.createCell(cellIndex);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(pay.getMoney().doubleValue());
            }


            row = sheet.createRow(rowIndex);
            cell = row.createCell(0);
            cell.setCellStyle(headCellStyle);
            cell.setCellValue("合计");


            cell = row.createCell(4, Cell.CELL_TYPE_FORMULA);
            cell.setCellStyle(headCellStyle);
            cell.setCellFormula("SUM(" + CellReference.convertNumToColString(4) + "3:" + CellReference.convertNumToColString(4) + rowIndex + ")");


            cell = row.createCell(5, Cell.CELL_TYPE_FORMULA);
            cell.setCellStyle(headCellStyle);
            cell.setCellFormula("SUM(" + CellReference.convertNumToColString(5) + "3:" + CellReference.convertNumToColString(5) + rowIndex + ")");

        }

        sheet.setForceFormulaRecalculation(true);
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.responseReset();
        externalContext.setResponseContentType("application/vnd.ms-excel");
        externalContext.setResponseHeader("Content-Disposition", "attachment;filename=exportContract.xlsx");

        try {
            workbook.write(externalContext.getResponseOutputStream());
            facesContext.responseComplete();
        } catch (IOException e) {
            messages.addError().reportError();
            logger.log(Level.WARNING,"维修资金日报表出错！", e);
        }



    }
}
