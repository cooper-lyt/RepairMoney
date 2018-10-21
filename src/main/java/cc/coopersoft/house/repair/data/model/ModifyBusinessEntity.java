package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "MODIFY_BUSINESS", schema = "WXZJ", catalog = "")
public class ModifyBusinessEntity {
    private String id;
    //private String oldHouse;
    //private String newHouse;
    private String type;
    private String status;
    private Integer version;
    private String reason;
    private String memo;
    private String operOrder;
    private Timestamp modifyTime;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    @Column(name = "REASON")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
    @Column(name = "OPER_ORDER")
    public String getOperOrder() {
        return operOrder;
    }

    public void setOperOrder(String operOrder) {
        this.operOrder = operOrder;
    }

    @Basic
    @Column(name = "MODIFY_TIME")
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModifyBusinessEntity that = (ModifyBusinessEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (reason != null ? !reason.equals(that.reason) : that.reason != null) return false;
        if (memo != null ? !memo.equals(that.memo) : that.memo != null) return false;
        if (operOrder != null ? !operOrder.equals(that.operOrder) : that.operOrder != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        result = 31 * result + (memo != null ? memo.hashCode() : 0);
        result = 31 * result + (operOrder != null ? operOrder.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        return result;
    }
}
