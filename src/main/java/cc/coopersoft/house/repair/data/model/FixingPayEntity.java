package cc.coopersoft.house.repair.data.model;

import cc.coopersoft.house.repair.data.PaymentType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "FIXING_PAY")
public class FixingPayEntity implements Comparable<FixingPayEntity>, java.io.Serializable{


    public enum Type{
        PAY, //支付
        BACK //退回
    }

    public enum Status{
        CREATING, // 创建中
        COMPLETE,

        // 有银行接口使用以下状态
        WAITING, // 等待支付
        FAIL  //支付失败
    }

    private String id;
    private BigDecimal payMoney;

    private Type type;
    private PaymentType paymentType;
    private String receivePerson;
    private String receiveNumber;
    private String receiveName;
    private String receiveBank;
    private String description;
    private Date payTime;
    private Status status;

    private RepairBusinessEntity repairBusiness;
    private BankAccountDetailsEntity bankAccountDetails;
    private Set<AccountDetailsEntity> accountDetails = new HashSet<>(0);

    public FixingPayEntity() {
    }

    public FixingPayEntity(String id, Status status, RepairBusinessEntity repairBusiness) {
        this.id = id;
        this.status = status;
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
    @Column(name = "PAY_MONEY", nullable = false)
    @NotNull
    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }


    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", length = 6 , nullable = false)
    @NotNull
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "PAY_TYPE", nullable = false, length = 4)
    @NotNull
    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 8)
    @NotNull
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Basic
    @Column(name = "RECEIVE_PERSON",length = 64, nullable = false)
    @NotNull
    @Size(max = 64)
    public String getReceivePerson() {
        return receivePerson;
    }

    public void setReceivePerson(String receivePerson) {
        this.receivePerson = receivePerson;
    }

    @Basic
    @Column(name = "RECEIVE_NUMBER", length = 128)
    @Size(max = 128)
    public String getReceiveNumber() {
        return receiveNumber;
    }

    public void setReceiveNumber(String receiveNumber) {
        this.receiveNumber = receiveNumber;
    }

    @Basic
    @Column(name = "RECEIVE_NAME", length = 64)
    @Size(max = 64)
    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    @Basic
    @Column(name = "RECEIVE_BANK", length = 64)
    @Size(max = 64)
    public String getReceiveBank() {
        return receiveBank;
    }

    public void setReceiveBank(String receiveBank) {
        this.receiveBank = receiveBank;
    }

    @Basic
    @Column(name = "DESCRIPTION", length = 128)
    @Size(max = 128)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PAY_TIME", nullable = false)
    @NotNull
    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "USE_AND_HOUSE",joinColumns = @JoinColumn(name = "PAY", nullable = false), inverseJoinColumns = @JoinColumn(name = "OPER_ORDER", nullable = false))
    public Set<AccountDetailsEntity> getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(Set<AccountDetailsEntity> accountDetails) {
        this.accountDetails = accountDetails;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "BANK_OPER_ORDER")
    public BankAccountDetailsEntity getBankAccountDetails() {
        return bankAccountDetails;
    }

    public void setBankAccountDetails(BankAccountDetailsEntity bankAccountDetails) {
        this.bankAccountDetails = bankAccountDetails;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PROJECT", nullable = false)
    @NotNull
    public RepairBusinessEntity getRepairBusiness() {
        return repairBusiness;
    }

    public void setRepairBusiness(RepairBusinessEntity repairBusiness) {
        this.repairBusiness = repairBusiness;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixingPayEntity that = (FixingPayEntity) o;


        if (id != null ? !id.equals(that.id) : that.id != null) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;

        return result;
    }

    @Override
    public int compareTo(FixingPayEntity o) {
        return payTime.compareTo(o.payTime);
    }
}
