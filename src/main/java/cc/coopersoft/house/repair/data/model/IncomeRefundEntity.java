package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "INCOME_REFUND")
public class IncomeRefundEntity implements java.io.Serializable {

    private long id;
    private BigDecimal money;
    private Date productDate;

    private AccountIncomeEntity accountIncome;
    private RefundBusinessEntity refundBusiness;
    private AccountDetailsEntity accountDetails;

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

    @Column(name = "MONEY", nullable = false)
    @NotNull
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "PRODUCT_DATE", nullable = false)
    @NotNull
    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "INCOME", nullable = false)
    @NotNull
    public AccountIncomeEntity getAccountIncome() {
        return accountIncome;
    }

    public void setAccountIncome(AccountIncomeEntity accountIncome) {
        this.accountIncome = accountIncome;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "REFUND",nullable = false)
    @NotNull
    public RefundBusinessEntity getRefundBusiness() {
        return refundBusiness;
    }

    public void setRefundBusiness(RefundBusinessEntity refundBusiness) {
        this.refundBusiness = refundBusiness;
    }


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "OPER_ORDER",nullable = false)
    @NotNull
    public AccountDetailsEntity getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(AccountDetailsEntity accountDetails) {
        this.accountDetails = accountDetails;
    }
}
