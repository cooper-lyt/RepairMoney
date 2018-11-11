package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SPLIT_HOUSE")
public class SplitHouseEntity implements java.io.Serializable{
    private long id;

    //private String house;
    private BigDecimal mustMoney;
    private BigDecimal balance;
    private BigDecimal splitMoney;
    private BigDecimal saveMoney;

    private RepairBusinessEntity repairBusiness;
    private HouseEntity houseEntity;


    public SplitHouseEntity() {
    }

    public SplitHouseEntity(RepairBusinessEntity repairBusiness) {
        this.repairBusiness = repairBusiness;
    }

    @Id
    @Column(name = "ID", nullable = false, unique = true)
    @GeneratedValue
    @NotNull
    public long getId() {
        return id;
    }

    public void setId(long id) {
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
    @Column(name = "SPLIT_MONEY", nullable = false)
    @NotNull
    public BigDecimal getSplitMoney() {
        return splitMoney;
    }

    public void setSplitMoney(BigDecimal splitMoney) {
        this.splitMoney = splitMoney;
    }

    @Basic
    @Column(name = "SAVE_MONEY", nullable = false)
    @NotNull
    public BigDecimal getSaveMoney() {
        return saveMoney;
    }

    public void setSaveMoney(BigDecimal saveMoney) {
        this.saveMoney = saveMoney;
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SplitHouseEntity that = (SplitHouseEntity) o;

        if (that.id != id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Long.valueOf(id).hashCode();

        return result;
    }
}
