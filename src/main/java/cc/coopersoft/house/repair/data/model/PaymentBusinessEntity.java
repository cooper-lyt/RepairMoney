package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "PAYMENT_BUSINESS", schema = "WXZJ")
public class PaymentBusinessEntity implements java.io.Serializable{

    public enum Type {
        FIRST //首缴
        ,ADD  //续缴
    }

    private long id;

    private Integer version;
    private String memo;
    private BigDecimal money;
    private BigDecimal mustMoney;
    private String calcDetails;
    private Type type;

    private PaymentEntity paymentEntity;
    private AccountDetailsEntity accountDetails;


    public PaymentBusinessEntity() {
    }

    public PaymentBusinessEntity( Type type, PaymentEntity paymentEntity) {
        this.type = type;
        this.paymentEntity = paymentEntity;
    }

    public PaymentBusinessEntity(BigDecimal money, BigDecimal mustMoney, String calcDetails, Type type, PaymentEntity paymentEntity) {
        this.money = money;
        this.mustMoney = mustMoney;
        this.calcDetails = calcDetails;
        this.type = type;
        this.paymentEntity = paymentEntity;
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


    @Version
    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Basic
    @Column(name = "MEMO",length = 256)
    @Size(max = 256)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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
    @Column(name = "MUST_MONEY",nullable = false)
    @NotNull
    public BigDecimal getMustMoney() {
        return mustMoney;
    }

    public void setMustMoney(BigDecimal mustMoney) {
        this.mustMoney = mustMoney;
    }


    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "OPER_ORDER",nullable = false)
    public AccountDetailsEntity getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(AccountDetailsEntity accountDetails) {
        this.accountDetails = accountDetails;
    }

    @Basic
    @Column(name = "CALC_DETAILS",length = 512)
    @Size(max = 512)
    public String getCalcDetails() {
        return calcDetails;
    }

    public void setCalcDetails(String calcDetails) {
        this.calcDetails = calcDetails;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    @NotNull
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "BUSINESS",nullable = false)
    @NotNull
    public PaymentEntity getPaymentEntity() {
        return paymentEntity;
    }

    public void setPaymentEntity(PaymentEntity paymentEntity) {
        this.paymentEntity = paymentEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentBusinessEntity that = (PaymentBusinessEntity) o;

        if (that.id != id) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = Long.valueOf(id).hashCode();

        return result;
    }
}
