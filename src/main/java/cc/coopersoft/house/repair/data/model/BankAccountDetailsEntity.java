package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "BANK_ACCOUNT_DETAILS")
public class BankAccountDetailsEntity implements java.io.Serializable{

    public enum Type{
        PAYMENT(AccountOperationDirection.IN),
        USE(AccountOperationDirection.OUT),
        REFUND(AccountOperationDirection.OUT),
        INCREMENT(AccountOperationDirection.IN),
        SAVE(AccountOperationDirection.IN),
        OUT(AccountOperationDirection.OUT);

        public AccountOperationDirection direction;

        Type(AccountOperationDirection direction) {
            this.direction = direction;
        }
    }

    private AccountOperationDirection direction;
    private Date operationTime;
    private BigDecimal money;
    private Type type;
    private String description;
    private String bankOperationOrder;

    private BankAccountEntity bankAccount;

    public BankAccountDetailsEntity() {
    }

    public BankAccountDetailsEntity(String bankOperationOrder,Type type) {
        this.type = type;
        this.direction = type.direction;
        this.bankOperationOrder = bankOperationOrder;
    }

    @Id
    @Column(name = "BANK_OPER_ORDER",unique = true, nullable = false, length = 32)
    public String getBankOperationOrder() {
        return bankOperationOrder;
    }

    public void setBankOperationOrder(String bankOperOrder) {
        this.bankOperationOrder = bankOperOrder;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "DIRECTION",length = 4, nullable = false)
    @NotNull
    public AccountOperationDirection getDirection() {
        return direction;
    }

    public void setDirection(AccountOperationDirection direction) {
        this.direction = direction;
    }

    @Basic
    @Column(name = "OPER_TIME", nullable = false)
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
    @Column(name = "TYPE",length = 16, nullable = false)
    @NotNull
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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
    @JoinColumn(name = "BANK_ACCOUNT_ID", nullable = false)
    @NotNull
    public BankAccountEntity getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountEntity bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankAccountDetailsEntity that = (BankAccountDetailsEntity) o;


        if (bankOperationOrder != null ? !bankOperationOrder.equals(that.bankOperationOrder) : that.bankOperationOrder != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bankOperationOrder != null ? bankOperationOrder.hashCode() : 0;
        return result;
    }
}
