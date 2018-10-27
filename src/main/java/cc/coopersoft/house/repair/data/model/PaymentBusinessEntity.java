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

    public enum Status {
        NORMAL, //正常
        MODIFYING, //变更中
        CANCEL, //取消
        DELETED //删除
    }

    private String id;

    private Status status;
    private Integer version;
    private String memo;
    private BigDecimal money;
    private BigDecimal mustMoney;
    private String calcDetails;
    private Type type;

    private PaymentEntity paymentEntity;
    private AccountDetailsEntity accountDetails;



    private HouseEntity house;

    public PaymentBusinessEntity() {
    }

    public PaymentBusinessEntity(String id, Status status, BigDecimal money, BigDecimal mustMoney, String calcDetails, Type type, PaymentEntity paymentEntity, HouseEntity house) {
        this.id = id;
        this.status = status;
        this.money = money;
        this.mustMoney = mustMoney;
        this.calcDetails = calcDetails;
        this.type = type;
        this.paymentEntity = paymentEntity;
        this.house = house;
    }

    @Id
    @Column(name = "ID",nullable = false,length = 38,unique = true)
    @NotNull
    @Size(max = 38)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS",nullable = false,length = 16)
    @NotNull
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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


    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "OPER_ORDER")
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "HOUSE",nullable = false)
    public HouseEntity getHouse() {
        return house;
    }

    public void setHouse(HouseEntity house) {
        this.house = house;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentBusinessEntity that = (PaymentBusinessEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;

        return result;
    }
}
