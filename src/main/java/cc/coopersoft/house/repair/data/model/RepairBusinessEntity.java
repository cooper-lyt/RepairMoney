package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "USE_BUSINESS")
public class RepairBusinessEntity {

    public enum SplitType{
        AREA, // 按面积比例
        MONEY //按首缴应缴金额
        //TODO 手动分摊
    }


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
    private BigDecimal saveMoney;
    private boolean urgent;
    private Integer version;
    private SplitType splitType;

    private BusinessEntity business;

    @Id
    @Column(name = "ID", length = 32, nullable = false, unique = true)
    @NotNull
    @Size(max = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME", length = 128, nullable = false)
    @Size(max = 128)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "SECTION_CODE", length = 32, nullable = false)
    @Size(max = 32)
    @NotNull
    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    @Basic
    @Column(name = "SECTION_NAME", length = 64 , nullable = false)
    @Size(max = 64)
    @NotNull
    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    @Basic
    @Column(name = "SECTION_ADDRESS", length = 256)
    @Size(max = 256)
    public String getSectionAddress() {
        return sectionAddress;
    }

    public void setSectionAddress(String sectionAddress) {
        this.sectionAddress = sectionAddress;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "APPLY_DATE", nullable = false)
    @NotNull
    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    @Basic
    @Column(name = "APPLY_TEL", length = 16)
    @Size(max = 16)
    public String getApplyTel() {
        return applyTel;
    }

    public void setApplyTel(String applyTel) {
        this.applyTel = applyTel;
    }

    @Basic
    @Column(name = "PLAN", length = 512)
    @Size(max = 512)
    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    @Basic
    @Column(name = "APPLY_MONEY", nullable = false)
    @NotNull
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

    @Column(name = "SAVE_MONEY", nullable = false)
    @NotNull
    public BigDecimal getSaveMoney() {
        return saveMoney;
    }

    public void setSaveMoney(BigDecimal saveMoney) {
        this.saveMoney = saveMoney;
    }

    @Basic
    @Column(name = "URGENT", nullable = false)
    public boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }


    @Version
    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "SPLIT_TYPE", nullable = false, length = 6)
    @NotNull
    public SplitType getSplitType() {
        return splitType;
    }

    public void setSplitType(SplitType splitType) {
        this.splitType = splitType;
    }


    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "ID")
    @MapsId
    public BusinessEntity getBusiness() {
        return business;
    }

    public void setBusiness(BusinessEntity businessEntity) {
        this.business = businessEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepairBusinessEntity that = (RepairBusinessEntity) o;


        if (id != null ? !id.equals(that.id) : that.id != null) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;

        return result;
    }
}
