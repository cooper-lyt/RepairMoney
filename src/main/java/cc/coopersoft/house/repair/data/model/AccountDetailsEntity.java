package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ACCOUNT_DETAILS")
public class AccountDetailsEntity implements java.io.Serializable{


    private String order;
    private AccountOperationDirection direction;
    private Date operationTime;
    private BigDecimal money;
    private BigDecimal balance;
    private String description;

    private HouseAccountEntity houseAccount;
    private BusinessEntity businessEntity;

    public AccountDetailsEntity() {
    }

    public AccountDetailsEntity(BusinessEntity businessEntity,AccountOperationDirection direction, String order) {
        this.order = order;
        this.direction = direction;
        this.businessEntity = businessEntity;
    }

    @Id
    @Column(name = "OPER_ORDER",unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getOrder() {
        return order;
    }

    public void setOrder(String operOrder) {
        this.order = operOrder;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "DIRECTION", length = 4, nullable = false)
    @NotNull
    public AccountOperationDirection getDirection() {
        return direction;
    }

    public void setDirection(AccountOperationDirection direction) {
        this.direction = direction;
    }

    @Basic
    @Column(name = "OPER_TIME",nullable = false)
    @NotNull
    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operTime) {
        this.operationTime = operTime;
    }

    @Basic
    @Column(name = "MONEY", nullable = false)
    @NotNull
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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
    @Column(name = "DESCRIPTION", length = 64)
    @Size(max = 64)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @ManyToOne(fetch = FetchType.LAZY,cascade =  {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH},optional = false)
    @JoinColumn(name = "ACCOUNT_NUMBER", nullable = false)
    @NotNull
    public HouseAccountEntity getHouseAccount() {
        return houseAccount;
    }

    public void setHouseAccount(HouseAccountEntity houseAccount) {
        this.houseAccount = houseAccount;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "BUSINESS",nullable = false)
    @NotNull
    public BusinessEntity getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(BusinessEntity businessEntity) {
        this.businessEntity = businessEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountDetailsEntity that = (AccountDetailsEntity) o;

        if (order != null ? !order.equals(that.order) : that.order != null) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = order != null ? order.hashCode() : 0;
        return result;
    }
}
