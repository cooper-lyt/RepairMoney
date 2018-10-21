package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "REFUND_BUSINESS", schema = "WXZJ")
public class RefundBusinessEntity {

    private String id;
    //private String house;
    private String operOrder;
    private BigDecimal money;
    private String reason;
    private String type;
    private String memo;
    private String status;
    private Integer version;
    private Timestamp refundTime;
    private String putId;
    private String bankOperOrder;

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
    public String getOperOrder() {
        return operOrder;
    }

    public void setOperOrder(String operOrder) {
        this.operOrder = operOrder;
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
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
    public Timestamp getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Timestamp refundTime) {
        this.refundTime = refundTime;
    }

    @Basic
    @Column(name = "PUT_ID")
    public String getPutId() {
        return putId;
    }

    public void setPutId(String putId) {
        this.putId = putId;
    }

    @Basic
    @Column(name = "BANK_OPER_ORDER")
    public String getBankOperOrder() {
        return bankOperOrder;
    }

    public void setBankOperOrder(String bankOperOrder) {
        this.bankOperOrder = bankOperOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RefundBusinessEntity that = (RefundBusinessEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (operOrder != null ? !operOrder.equals(that.operOrder) : that.operOrder != null) return false;
        if (money != null ? !money.equals(that.money) : that.money != null) return false;
        if (reason != null ? !reason.equals(that.reason) : that.reason != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (memo != null ? !memo.equals(that.memo) : that.memo != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (refundTime != null ? !refundTime.equals(that.refundTime) : that.refundTime != null) return false;
        if (putId != null ? !putId.equals(that.putId) : that.putId != null) return false;
        if (bankOperOrder != null ? !bankOperOrder.equals(that.bankOperOrder) : that.bankOperOrder != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (operOrder != null ? operOrder.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (memo != null ? memo.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (refundTime != null ? refundTime.hashCode() : 0);
        result = 31 * result + (putId != null ? putId.hashCode() : 0);
        result = 31 * result + (bankOperOrder != null ? bankOperOrder.hashCode() : 0);
        return result;
    }
}
