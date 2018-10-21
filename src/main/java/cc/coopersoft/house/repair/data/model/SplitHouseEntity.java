package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "SPLIT_HOUSE", schema = "WXZJ")
public class SplitHouseEntity {
    private String id;
    private String project;
    //private String house;
    private BigDecimal mustMoney;
    private BigDecimal balance;
    private BigDecimal splitMoney;
    private BigDecimal saveMoney;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "PROJECT")
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Basic
    @Column(name = "MUST_MONEY")
    public BigDecimal getMustMoney() {
        return mustMoney;
    }

    public void setMustMoney(BigDecimal mustMoney) {
        this.mustMoney = mustMoney;
    }

    @Basic
    @Column(name = "BALANCE")
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Basic
    @Column(name = "SPLIT_MONEY")
    public BigDecimal getSplitMoney() {
        return splitMoney;
    }

    public void setSplitMoney(BigDecimal splitMoney) {
        this.splitMoney = splitMoney;
    }

    @Basic
    @Column(name = "SAVE_MONEY")
    public BigDecimal getSaveMoney() {
        return saveMoney;
    }

    public void setSaveMoney(BigDecimal saveMoney) {
        this.saveMoney = saveMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SplitHouseEntity that = (SplitHouseEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (project != null ? !project.equals(that.project) : that.project != null) return false;
        if (mustMoney != null ? !mustMoney.equals(that.mustMoney) : that.mustMoney != null) return false;
        if (balance != null ? !balance.equals(that.balance) : that.balance != null) return false;
        if (splitMoney != null ? !splitMoney.equals(that.splitMoney) : that.splitMoney != null) return false;
        if (saveMoney != null ? !saveMoney.equals(that.saveMoney) : that.saveMoney != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (project != null ? project.hashCode() : 0);
        result = 31 * result + (mustMoney != null ? mustMoney.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (splitMoney != null ? splitMoney.hashCode() : 0);
        result = 31 * result + (saveMoney != null ? saveMoney.hashCode() : 0);
        return result;
    }
}
