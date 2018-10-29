package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "USE_BUSINESS", schema = "WXZJ", catalog = "")
public class UseBusinessEntity {
    private String id;
    private String name;
    private String sectionCode;
    private String sectionName;
    private String sectionAddress;
    private Date applyDate;
    private String applyTel;
    private String plan;
    private BigDecimal applyMoney;
    private BigDecimal payMoney;
    private BigDecimal checkMoney;
    private BigDecimal superviseMoney;
    private boolean urgent;
    private Integer version;
    private String splitType;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "SECTION_CODE")
    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    @Basic
    @Column(name = "SECTION_NAME")
    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    @Basic
    @Column(name = "SECTION_ADDRESS")
    public String getSectionAddress() {
        return sectionAddress;
    }

    public void setSectionAddress(String sectionAddress) {
        this.sectionAddress = sectionAddress;
    }

    @Basic
    @Column(name = "APPLY_DATE")
    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    @Basic
    @Column(name = "APPLY_TEL")
    public String getApplyTel() {
        return applyTel;
    }

    public void setApplyTel(String applyTel) {
        this.applyTel = applyTel;
    }

    @Basic
    @Column(name = "PLAN")
    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    @Basic
    @Column(name = "APPLY_MONEY")
    public BigDecimal getApplyMoney() {
        return applyMoney;
    }

    public void setApplyMoney(BigDecimal applyMoney) {
        this.applyMoney = applyMoney;
    }

    @Basic
    @Column(name = "PAY_MONEY")
    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    @Basic
    @Column(name = "CHECK_MONEY")
    public BigDecimal getCheckMoney() {
        return checkMoney;
    }

    public void setCheckMoney(BigDecimal checkMoney) {
        this.checkMoney = checkMoney;
    }

    @Basic
    @Column(name = "SUPERVISE_MONEY")
    public BigDecimal getSuperviseMoney() {
        return superviseMoney;
    }

    public void setSuperviseMoney(BigDecimal superviseMoney) {
        this.superviseMoney = superviseMoney;
    }

    @Basic
    @Column(name = "URGENT")
    public boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }


    @Basic
    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Basic
    @Column(name = "SPLIT_TYPE")
    public String getSplitType() {
        return splitType;
    }

    public void setSplitType(String splitType) {
        this.splitType = splitType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UseBusinessEntity that = (UseBusinessEntity) o;


        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (sectionCode != null ? !sectionCode.equals(that.sectionCode) : that.sectionCode != null) return false;
        if (sectionName != null ? !sectionName.equals(that.sectionName) : that.sectionName != null) return false;
        if (sectionAddress != null ? !sectionAddress.equals(that.sectionAddress) : that.sectionAddress != null)
            return false;
        if (applyDate != null ? !applyDate.equals(that.applyDate) : that.applyDate != null) return false;
        if (applyTel != null ? !applyTel.equals(that.applyTel) : that.applyTel != null) return false;
        if (plan != null ? !plan.equals(that.plan) : that.plan != null) return false;
        if (applyMoney != null ? !applyMoney.equals(that.applyMoney) : that.applyMoney != null) return false;
        if (payMoney != null ? !payMoney.equals(that.payMoney) : that.payMoney != null) return false;
        if (checkMoney != null ? !checkMoney.equals(that.checkMoney) : that.checkMoney != null) return false;
        if (superviseMoney != null ? !superviseMoney.equals(that.superviseMoney) : that.superviseMoney != null)
            return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (splitType != null ? !splitType.equals(that.splitType) : that.splitType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sectionCode != null ? sectionCode.hashCode() : 0);
        result = 31 * result + (sectionName != null ? sectionName.hashCode() : 0);
        result = 31 * result + (sectionAddress != null ? sectionAddress.hashCode() : 0);
        result = 31 * result + (applyDate != null ? applyDate.hashCode() : 0);
        result = 31 * result + (applyTel != null ? applyTel.hashCode() : 0);
        result = 31 * result + (plan != null ? plan.hashCode() : 0);
        result = 31 * result + (applyMoney != null ? applyMoney.hashCode() : 0);
        result = 31 * result + (payMoney != null ? payMoney.hashCode() : 0);
        result = 31 * result + (checkMoney != null ? checkMoney.hashCode() : 0);
        result = 31 * result + (superviseMoney != null ? superviseMoney.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (splitType != null ? splitType.hashCode() : 0);
        return result;
    }
}
