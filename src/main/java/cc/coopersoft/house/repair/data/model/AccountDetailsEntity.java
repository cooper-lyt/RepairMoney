package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "ACCOUNT_DETAILS", schema = "WXZJ", catalog = "")
public class AccountDetailsEntity {
    private String operOrder;
    private String direction;
    private Timestamp operTime;
    private BigDecimal money;
    private String type;
    private BigDecimal balance;
    private String description;
    private String accountNumber;
    private String status;

    @Id
    @Column(name = "OPER_ORDER")
    public String getOperOrder() {
        return operOrder;
    }

    public void setOperOrder(String operOrder) {
        this.operOrder = operOrder;
    }

    @Basic
    @Column(name = "DIRECTION")
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Basic
    @Column(name = "OPER_TIME")
    public Timestamp getOperTime() {
        return operTime;
    }

    public void setOperTime(Timestamp operTime) {
        this.operTime = operTime;
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
    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "ACCOUNT_NUMBER")
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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

        AccountDetailsEntity that = (AccountDetailsEntity) o;

        if (operOrder != null ? !operOrder.equals(that.operOrder) : that.operOrder != null) return false;
        if (direction != null ? !direction.equals(that.direction) : that.direction != null) return false;
        if (operTime != null ? !operTime.equals(that.operTime) : that.operTime != null) return false;
        if (money != null ? !money.equals(that.money) : that.money != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (balance != null ? !balance.equals(that.balance) : that.balance != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (accountNumber != null ? !accountNumber.equals(that.accountNumber) : that.accountNumber != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = operOrder != null ? operOrder.hashCode() : 0;
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + (operTime != null ? operTime.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (accountNumber != null ? accountNumber.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
