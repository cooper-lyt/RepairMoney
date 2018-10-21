package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "INCOME_BUSINESS", schema = "WXZJ", catalog = "")
public class IncomeBusinessEntity {
    private String id;
    private String bankOperOrder;
    private String putId;
    private BigDecimal capital;
    private Timestamp incomeTime;
    private int accountCount;
    private Date beginDate;
    private Date endDate;
    private BigDecimal expectMoney;
    private BigDecimal money;
    private String status;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "BANK_OPER_ORDER")
    public String getBankOperOrder() {
        return bankOperOrder;
    }

    public void setBankOperOrder(String bankOperOrder) {
        this.bankOperOrder = bankOperOrder;
    }

    @Basic
    @Column(name = "PUT_ID")
    public String getPutId() {
        return putId;
    }

    public void setPutId(String putId) {
        this.putId = putId;
    }

    @Basic
    @Column(name = "CAPITAL")
    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    @Basic
    @Column(name = "INCOME_TIME")
    public Timestamp getIncomeTime() {
        return incomeTime;
    }

    public void setIncomeTime(Timestamp incomeTime) {
        this.incomeTime = incomeTime;
    }

    @Basic
    @Column(name = "ACCOUNT_COUNT")
    public int getAccountCount() {
        return accountCount;
    }

    public void setAccountCount(int accountCount) {
        this.accountCount = accountCount;
    }

    @Basic
    @Column(name = "BEGIN_DATE")
    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @Basic
    @Column(name = "END_DATE")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "EXPECT_MONEY")
    public BigDecimal getExpectMoney() {
        return expectMoney;
    }

    public void setExpectMoney(BigDecimal expectMoney) {
        this.expectMoney = expectMoney;
    }

    @Basic
    @Column(name = "MONEY")
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Basic
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IncomeBusinessEntity that = (IncomeBusinessEntity) o;

        if (accountCount != that.accountCount) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (bankOperOrder != null ? !bankOperOrder.equals(that.bankOperOrder) : that.bankOperOrder != null)
            return false;
        if (putId != null ? !putId.equals(that.putId) : that.putId != null) return false;
        if (capital != null ? !capital.equals(that.capital) : that.capital != null) return false;
        if (incomeTime != null ? !incomeTime.equals(that.incomeTime) : that.incomeTime != null) return false;
        if (beginDate != null ? !beginDate.equals(that.beginDate) : that.beginDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (expectMoney != null ? !expectMoney.equals(that.expectMoney) : that.expectMoney != null) return false;
        if (money != null ? !money.equals(that.money) : that.money != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (bankOperOrder != null ? bankOperOrder.hashCode() : 0);
        result = 31 * result + (putId != null ? putId.hashCode() : 0);
        result = 31 * result + (capital != null ? capital.hashCode() : 0);
        result = 31 * result + (incomeTime != null ? incomeTime.hashCode() : 0);
        result = 31 * result + accountCount;
        result = 31 * result + (beginDate != null ? beginDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (expectMoney != null ? expectMoney.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
