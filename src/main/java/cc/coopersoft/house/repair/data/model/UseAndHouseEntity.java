package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "USE_AND_HOUSE", schema = "WXZJ", catalog = "")
public class UseAndHouseEntity {
    private String id;
    //private String house;
    private String pay;
    private String operOrder;
    private BigDecimal money;
    private String memo;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "PAY")
    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
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
    @Column(name = "MEMO")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UseAndHouseEntity that = (UseAndHouseEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (pay != null ? !pay.equals(that.pay) : that.pay != null) return false;
        if (operOrder != null ? !operOrder.equals(that.operOrder) : that.operOrder != null) return false;
        if (money != null ? !money.equals(that.money) : that.money != null) return false;
        if (memo != null ? !memo.equals(that.memo) : that.memo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (pay != null ? pay.hashCode() : 0);
        result = 31 * result + (operOrder != null ? operOrder.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        result = 31 * result + (memo != null ? memo.hashCode() : 0);
        return result;
    }
}
