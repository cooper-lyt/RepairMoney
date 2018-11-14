package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "SPLIT_HOUSE")
public class RepairJoinHouseEntity implements Comparable<RepairJoinHouseEntity>, java.io.Serializable{

    public enum Status{
        SUCCESS("success"), //成功

        OUT_MIN("warning"), //超出最小余额

        ACCOUNT_WARN("warning"), //账户预警

        ACCOUNT_LOCK("danger"),//账户冻结
        NOT_ENOUGH("danger"), //余额不足
        NO_ACCOUNT("default"); //没有开户或已销户

        private String style;

        public String getStyle() {
            return style;
        }

        Status(String label) {
            this.style = label;
        }
    }


    private String id;

    //private String house;
    private BigDecimal mustMoney;
    private BigDecimal balance;
    private BigDecimal applyMoney;
    private BigDecimal money;
    private Status status;
    private int priority;

    private RepairBusinessEntity repairBusiness;
    private HouseEntity houseEntity;


    public RepairJoinHouseEntity() {
    }

    public RepairJoinHouseEntity(String id, RepairBusinessEntity repairBusiness) {
        this.id = id;
        this.repairBusiness = repairBusiness;
    }

    public RepairJoinHouseEntity(String id, RepairBusinessEntity repairBusiness, BigDecimal mustMoney, BigDecimal balance, HouseEntity houseEntity) {
        this.id = id;
        this.mustMoney = mustMoney;
        this.balance = balance;
        this.houseEntity = houseEntity;
        this.repairBusiness = repairBusiness;
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
    @Column(name = "MUST_MONEY", nullable = false)
    @NotNull
    public BigDecimal getMustMoney() {
        return mustMoney;
    }

    public void setMustMoney(BigDecimal mustMoney) {
        this.mustMoney = mustMoney;
    }

    @Basic
    @Column(name = "BALANCE", nullable = false)
    @NotNull
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Basic
    @Column(name = "APPLY_MONEY", nullable = false)
    @NotNull
    public BigDecimal getApplyMoney() {
        return applyMoney;
    }

    public void setApplyMoney(BigDecimal splitMoney) {
        this.applyMoney = splitMoney;
    }

    @Basic
    @Column(name = "MONEY", nullable = false)
    @NotNull
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal saveMoney) {
        this.money = saveMoney;
    }

    @Column(name = "PRIORITY" , nullable = false)
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PROJECT", nullable = false)
    @NotNull
    public RepairBusinessEntity getRepairBusiness() {
        return repairBusiness;
    }

    public void setRepairBusiness(RepairBusinessEntity repairBusiness) {
        this.repairBusiness = repairBusiness;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "HOUSE" , nullable = false)
    @NotNull
    public HouseEntity getHouseEntity() {
        return houseEntity;
    }

    public void setHouseEntity(HouseEntity houseEntity) {
        this.houseEntity = houseEntity;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 12)
    @NotNull
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepairJoinHouseEntity that = (RepairJoinHouseEntity) o;

        if (houseEntity != null ? !houseEntity.equals(that.houseEntity) : that.houseEntity != null) return false;
        if (repairBusiness != null ? !repairBusiness.equals(that.repairBusiness) : that.repairBusiness != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = (houseEntity != null ? houseEntity.hashCode() : 0);
        result = 31 * result + (repairBusiness != null ? repairBusiness.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(RepairJoinHouseEntity o) {

        return o.priority - priority;
    }
}
