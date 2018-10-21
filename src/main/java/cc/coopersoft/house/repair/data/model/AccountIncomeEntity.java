package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "ACCOUNT_INCOME", schema = "WXZJ", catalog = "")
public class AccountIncomeEntity {

    private String id;
    private String business;
    private String operOrder;
    private BigDecimal money;
    private BigDecimal interestRate;
    private Date incomeDate;
    private BigDecimal totalProduct;


    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "BUSINESS")
    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    @Basic
    @Column(name = "OPER_ORDER")
    public String getOperOrder() {
        return operOrder;
    }

    public void setOperOrder(String operOrder) {
        this.operOrder = operOrder;
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
    @Column(name = "INTEREST_RATE")
    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    @Basic
    @Column(name = "INCOME_DATE")
    public Date getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(Date incomeDate) {
        this.incomeDate = incomeDate;
    }

    @Basic
    @Column(name = "TOTAL_PRODUCT")
    public BigDecimal getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(BigDecimal totalProduct) {
        this.totalProduct = totalProduct;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountIncomeEntity that = (AccountIncomeEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (business != null ? !business.equals(that.business) : that.business != null) return false;
        if (operOrder != null ? !operOrder.equals(that.operOrder) : that.operOrder != null) return false;
        if (money != null ? !money.equals(that.money) : that.money != null) return false;
        if (interestRate != null ? !interestRate.equals(that.interestRate) : that.interestRate != null) return false;
        if (incomeDate != null ? !incomeDate.equals(that.incomeDate) : that.incomeDate != null) return false;
        if (totalProduct != null ? !totalProduct.equals(that.totalProduct) : that.totalProduct != null) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (business != null ? business.hashCode() : 0);
        result = 31 * result + (operOrder != null ? operOrder.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        result = 31 * result + (interestRate != null ? interestRate.hashCode() : 0);
        result = 31 * result + (incomeDate != null ? incomeDate.hashCode() : 0);
        result = 31 * result + (totalProduct != null ? totalProduct.hashCode() : 0);

        return result;
    }
}
