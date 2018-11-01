package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "FIXING_PAY", schema = "WXZJ", catalog = "")
public class FixingPayEntity {
    private String id;
    private BigDecimal payMoney;
    private String bankOperOrder;
    private String project;
    private String type;
    private int round;
    private String receivePerson;
    private String receiveNumber;
    private String receiveName;
    private String receiveBank;
    private String description;
    private String memo;
    private Timestamp payTime;


    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "PAY_MONEY")
    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    @Basic
    @Column(name = "BANK_OPER_ORDER")
    public String getBankOperOrder() {
        return bankOperOrder;
    }

    public void setBankOperOrder(String bankOperOrder) {
        this.bankOperOrder = bankOperOrder;
    }

    @Basic
    @Column(name = "PROJECT")
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
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
    @Column(name = "ROUND")
    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    @Basic
    @Column(name = "RECEIVE_PERSON")
    public String getReceivePerson() {
        return receivePerson;
    }

    public void setReceivePerson(String receivePerson) {
        this.receivePerson = receivePerson;
    }

    @Basic
    @Column(name = "RECEIVE_NUMBER")
    public String getReceiveNumber() {
        return receiveNumber;
    }

    public void setReceiveNumber(String receiveNumber) {
        this.receiveNumber = receiveNumber;
    }

    @Basic
    @Column(name = "RECEIVE_NAME")
    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    @Basic
    @Column(name = "RECEIVE_BANK")
    public String getReceiveBank() {
        return receiveBank;
    }

    public void setReceiveBank(String receiveBank) {
        this.receiveBank = receiveBank;
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
    @Column(name = "MEMO")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Basic
    @Column(name = "PAY_TIME")
    public Timestamp getPayTime() {
        return payTime;
    }

    public void setPayTime(Timestamp payTime) {
        this.payTime = payTime;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FixingPayEntity that = (FixingPayEntity) o;

        if (round != that.round) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (payMoney != null ? !payMoney.equals(that.payMoney) : that.payMoney != null) return false;
        if (bankOperOrder != null ? !bankOperOrder.equals(that.bankOperOrder) : that.bankOperOrder != null)
            return false;
        if (project != null ? !project.equals(that.project) : that.project != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (receivePerson != null ? !receivePerson.equals(that.receivePerson) : that.receivePerson != null)
            return false;
        if (receiveNumber != null ? !receiveNumber.equals(that.receiveNumber) : that.receiveNumber != null)
            return false;
        if (receiveName != null ? !receiveName.equals(that.receiveName) : that.receiveName != null) return false;
        if (receiveBank != null ? !receiveBank.equals(that.receiveBank) : that.receiveBank != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (memo != null ? !memo.equals(that.memo) : that.memo != null) return false;
        if (payTime != null ? !payTime.equals(that.payTime) : that.payTime != null) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (payMoney != null ? payMoney.hashCode() : 0);
        result = 31 * result + (bankOperOrder != null ? bankOperOrder.hashCode() : 0);
        result = 31 * result + (project != null ? project.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + round;
        result = 31 * result + (receivePerson != null ? receivePerson.hashCode() : 0);
        result = 31 * result + (receiveNumber != null ? receiveNumber.hashCode() : 0);
        result = 31 * result + (receiveName != null ? receiveName.hashCode() : 0);
        result = 31 * result + (receiveBank != null ? receiveBank.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (memo != null ? memo.hashCode() : 0);
        result = 31 * result + (payTime != null ? payTime.hashCode() : 0);

        return result;
    }
}
