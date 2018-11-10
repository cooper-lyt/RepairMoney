package cc.coopersoft.house.repair.data.model;

import cc.coopersoft.house.repair.data.AccountMoneyOperation;
import cc.coopersoft.house.repair.data.PaymentType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "REFUND_BUSINESS")
public class RefundBusinessEntity implements AccountMoneyOperation,java.io.Serializable{

    public enum Type{
        WRONG_FULL, //冲账
        WRONG_PART, //冲账
        INCOME_PART, //冲账
        INCOME_FULL, //冲账
        DESTROY,
        OTHER
    }

    private String id;
    //private String house;
    private BigDecimal money;
    private String reason;
    private Type type;
    private Integer version;
    private Date refundTime;
    private PaymentType paymentType;

    private BusinessEntity business;

    private BankAccountDetailsEntity bankAccountDetails;
    private AccountDetailsEntity accountDetails;

    private Set<PaymentBusinessEntity> paymentBusiness = new HashSet<>(0);
    private Set<AccountIncomeEntity> accountIncomes = new HashSet<>(0);



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
    @Column(name = "MONEY",nullable = false)
    @NotNull
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Basic
    @Column(name = "REASON", length = 128)
    @Size(max = 128)
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", length = 16, nullable = false)
    @NotNull
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    @Version
    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REFUND_TIME",nullable = false)
    @NotNull
    public Date getOperationTime() {
        return refundTime;
    }

    public void setOperationTime(Date refundTime) {
        this.refundTime = refundTime;
    }


    @Enumerated(EnumType.STRING)
    @Column(name = "PAY_TYPE",length = 4 , nullable = false)
    @NotNull
    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "OPER_ORDER", nullable = false)
    public AccountDetailsEntity getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(AccountDetailsEntity accountDetails) {
        this.accountDetails = accountDetails;
    }

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "BANK_OPER_ORDER")
    public BankAccountDetailsEntity getBankAccountDetails() {
        return bankAccountDetails;
    }

    public void setBankAccountDetails(BankAccountDetailsEntity bankAccountDetails) {
        this.bankAccountDetails = bankAccountDetails;
    }

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "PAYMENT_REFUND", joinColumns = @JoinColumn(name = "REFUND", nullable = false), inverseJoinColumns = @JoinColumn(name = "PAYMENT", nullable = false))
    public Set<PaymentBusinessEntity> getPaymentBusiness() {
        return paymentBusiness;
    }

    public void setPaymentBusiness(Set<PaymentBusinessEntity> paymentBusiness) {
        this.paymentBusiness = paymentBusiness;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "INCOME_REFUND", joinColumns = @JoinColumn(name = "REFUND", nullable = false), inverseJoinColumns = @JoinColumn(name = "INCOME", nullable = false))
    public Set<AccountIncomeEntity> getAccountIncomes() {
        return accountIncomes;
    }

    public void setAccountIncomes(Set<AccountIncomeEntity> accountIncomes) {
        this.accountIncomes = accountIncomes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RefundBusinessEntity that = (RefundBusinessEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }
}
