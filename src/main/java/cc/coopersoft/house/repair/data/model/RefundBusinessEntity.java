package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "REFUND_BUSINESS", schema = "WXZJ")
public class RefundBusinessEntity {

    public enum Type{
        PAYMENT_WRONG_FULL,
        PAYMENT_WRONG_PART,
        DESTROY,
        OTHER
    }

    private String id;
    //private String house;
    private String operationOrder;
    private BigDecimal money;
    private String reason;
    private String type;
    private String memo;
    private Integer version;
    private Date refundTime;
    private String bankOperationOrder;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "OPER_ORDER")
    public String getOperationOrder() {
        return operationOrder;
    }

    public void setOperationOrder(String operOrder) {
        this.operationOrder = operOrder;
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
    @Column(name = "REASON")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
    @Column(name = "MEMO")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    @Basic
    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Basic
    @Column(name = "REFUND_TIME")
    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    @Basic
    @Column(name = "BANK_OPER_ORDER")
    public String getBankOperationOrder() {
        return bankOperationOrder;
    }

    public void setBankOperationOrder(String bankOperOrder) {
        this.bankOperationOrder = bankOperOrder;
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
