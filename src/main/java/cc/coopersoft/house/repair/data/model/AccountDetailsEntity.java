package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ACCOUNT_DETAILS")
public class AccountDetailsEntity implements java.io.Serializable{

    public enum Type{
        PAYMENT(AccountOperationDirection.IN),
        USE(AccountOperationDirection.OUT),
        REFUND(AccountOperationDirection.OUT),
        INCREMENT(AccountOperationDirection.IN);

        public AccountOperationDirection direction;

        Type(AccountOperationDirection direction) {
            this.direction = direction;
        }
    }

    private String order;
    private AccountOperationDirection direction;
    private Date operationTime;
    private BigDecimal money;
    private Type type;
    private BigDecimal balance;
    private String description;

    private HouseAccountEntity houseAccount;

    public AccountDetailsEntity() {
    }

    public AccountDetailsEntity(String order, Type type) {
        this.order = order;
        this.type = type;
        this.direction = type.direction;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE",nullable = false, length = 16)
    @NotNull
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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


    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "ACCOUNT_NUMBER", nullable = false)
    @NotNull
    public HouseAccountEntity getHouseAccount() {
        return houseAccount;
    }

    public void setHouseAccount(HouseAccountEntity houseAccount) {
        this.houseAccount = houseAccount;
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
