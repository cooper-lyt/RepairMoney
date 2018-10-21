package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "PAYMENT_NOTICE_ITEM", schema = "WXZJ")
public class PaymentNoticeItemEntity implements java.io.Serializable {

    private String id;
    private String calcDetails;
    private BigDecimal mustMoney;
    private BigDecimal money;
    private String description;


    private PaymentNoticeEntity paymentNotice;
    private HouseEntity house;

    @Id
    @NotNull
    @Size(max = 32)
    @Column(name = "ID",nullable = false, length = 32,unique = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "CALC_DETAILS", length = 512)
    @Size(max = 512)
    public String getCalcDetails() {
        return calcDetails;
    }

    public void setCalcDetails(String calcDetails) {
        this.calcDetails = calcDetails;
    }

    @Column(name = "MUST_MONEY",nullable = false)
    public BigDecimal getMustMoney() {
        return mustMoney;
    }

    public void setMustMoney(BigDecimal mustMoney) {
        this.mustMoney = mustMoney;
    }

    @Column(name = "MONEY", nullable = false)
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Column(name = "DESCRIPTION", length = 512)
    @Size(max = 512)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "HOUSE", nullable = false)
    @NotNull
    public HouseEntity getHouse() {
        return house;
    }

    public void setHouse(HouseEntity houseEntity) {
        this.house = houseEntity;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "NOTICE", nullable = false)
    public PaymentNoticeEntity getPaymentNotice() {
        return paymentNotice;
    }

    public void setPaymentNotice(PaymentNoticeEntity paymentNoticeEntity) {
        this.paymentNotice = paymentNoticeEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentNoticeItemEntity that = (PaymentNoticeItemEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;

        return result;
    }
}
