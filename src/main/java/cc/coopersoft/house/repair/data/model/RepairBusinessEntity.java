package cc.coopersoft.house.repair.data.model;

import cc.coopersoft.framework.tools.SetLinkList;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "USE_BUSINESS")
public class RepairBusinessEntity {

    public enum SplitType{
        MONEY, //按首缴应缴金额
        AREA // 按面积比例

        //TODO 手动分摊
    }


    private String id;
    private String name;
    private String sectionCode;
    private String sectionName;
    private String sectionAddress;
    private Date applyDate;
    private String applyTel;
    private String applyGroup;
    private String plan;
    private BigDecimal applyMoney;
    private BigDecimal budgetMoney;
    private BigDecimal checkMoney;
    private BigDecimal superviseMoney;
    private BigDecimal saveMoney;
    private boolean urgent;
    private Integer version;
    private SplitType splitType;
    private RoundingMode calcType;
    private boolean budget;

    private BusinessEntity business;
    private Set<RepairJoinHouseEntity> repairJoinHouses = new HashSet<>(0);
    private Set<FixingPayEntity> fixingPays = new HashSet<>(0);

    public RepairBusinessEntity() {
    }

    public RepairBusinessEntity(BusinessEntity business) {
        this.business = business;
    }

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

    @Column(name = "APPLY_GROUP", length = 128, nullable = false)
    @Size(max = 128)
    @NotNull
    public String getApplyGroup() {
        return applyGroup;
    }

    public void setApplyGroup(String applyGroup) {
        this.applyGroup = applyGroup;
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
    public BigDecimal getBudgetMoney() {
        return budgetMoney;
    }

    public void setBudgetMoney(BigDecimal payMoney) {
        this.budgetMoney = payMoney;
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

    @Column(name = "BUDGET", nullable = false)
    public boolean isBudget() {
        return budget;
    }

    public void setBudget(boolean budget) {
        this.budget = budget;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "CALC_TYPE", nullable = false, length = 16)
    @NotNull
    public RoundingMode getCalcType() {
        return calcType;
    }

    public void setCalcType(RoundingMode calcType) {
        this.calcType = calcType;
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "repairBusiness")
    public Set<RepairJoinHouseEntity> getRepairJoinHouses() {
        return repairJoinHouses;
    }

    public void setRepairJoinHouses(Set<RepairJoinHouseEntity> repairJoinHouses) {
        this.repairJoinHouses = repairJoinHouses;
    }

    @Transient
    public List<RepairJoinHouseEntity> getRepairJoinHouseList(){
        return SetLinkList.instance(getRepairJoinHouses());
    }


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "repairBusiness")
    public Set<FixingPayEntity> getFixingPays() {
        return fixingPays;
    }

    public void setFixingPays(Set<FixingPayEntity> fixingPays) {
        this.fixingPays = fixingPays;
    }

    @Transient
    public List<FixingPayEntity> getFixingPayList(){
        return SetLinkList.instance(getFixingPays());
    }

    /***
     * 根据状态得到总維修金额
     *
     * @return 总金额
     */
    @Transient
    public BigDecimal getTotalMoney(){
        if (isBudget()){
            return getBudgetMoney();
        }else{
            return getApplyMoney();
        }
    }

    /***
     *
     * @return 已支付金额
     */
    @Transient
    public BigDecimal getPaymentMoney(){
        BigDecimal result = BigDecimal.ZERO;
        for(FixingPayEntity pay: getFixingPays()){
            result = result.add(pay.getPayMoney());
        }
        return result;
    }

    /***
     *
     * @return 未支付金额 不包含保证金
     */
    @Transient
    public BigDecimal getDebtMoney(){
        return getTotalMoney().subtract(getSaveMoney()).subtract(getPaymentMoney());
    }

    /***
     *
     * @return 未支付金额 含保证金
     */
    @Transient
    public BigDecimal getAllDebitMoney(){
        return getTotalMoney().subtract(getPaymentMoney());
    }

    /***
     *
     * @return 正在进行支付的操作
     */
    @Transient
    public FixingPayEntity getPaying(){
        for(FixingPayEntity pay: getFixingPays()){
            if (FixingPayEntity.Status.CREATING.equals(pay)){
                return pay;
            }
        }
        return null;
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
