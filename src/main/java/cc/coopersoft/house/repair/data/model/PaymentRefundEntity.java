package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "PAYMENT_REFUND")
public class PaymentRefundEntity implements java.io.Serializable {

    private long id;
    private BigDecimal money;

    private RefundBusinessEntity refundBusiness;
    private PaymentBusinessEntity paymentBusiness;
    private AccountDetailsEntity accountDetailsEntity;

    @Id
    @Column(name = "ID", nullable = false, unique = true)
    @GeneratedValue
    @NotNull
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "MONEY", nullable = false)
    @NotNull
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "REFUND",nullable = false)
    @NotNull
    public RefundBusinessEntity getRefundBusiness() {
        return refundBusiness;
    }

    public void setRefundBusiness(RefundBusinessEntity refundBusiness) {
        this.refundBusiness = refundBusiness;
    }

    //TODO test Delete refund and modify payment
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "PAYMENT",nullable = false)
    @NotNull
    public PaymentBusinessEntity getPaymentBusiness() {
        return paymentBusiness;
    }

    public void setPaymentBusiness(PaymentBusinessEntity paymentBusiness) {
        this.paymentBusiness = paymentBusiness;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "OPER_ORDER",nullable = false)
    @NotNull
    public AccountDetailsEntity getAccountDetailsEntity() {
        return accountDetailsEntity;
    }

    public void setAccountDetailsEntity(AccountDetailsEntity accountDetailsEntity) {
        this.accountDetailsEntity = accountDetailsEntity;
    }


}
