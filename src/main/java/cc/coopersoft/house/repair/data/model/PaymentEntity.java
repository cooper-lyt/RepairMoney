package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "PAYMENT")
public class PaymentEntity implements java.io.Serializable{

    private String id;
    private String sectionCode;
    private String sectionName;
    private Integer version;
    private Date operationDate;
    private BigDecimal rulePrice;
    private String calcRule;
    private String payPerson;
    private BigDecimal mustMoney;
    private BigDecimal money;
    private PaymentType paymentType;


    private BankAccountDetailsEntity bankAccountDetails;
    private BusinessEntity business;
    private PaymentNoticeEntity paymentNotice;
    private Set<PaymentBusinessEntity> paymentBusinesses = new HashSet<PaymentBusinessEntity>(0);

    public PaymentEntity() {
    }

    public PaymentEntity(BusinessEntity businessEntity) {
        this.business = businessEntity;
    }

    @Id
    @Column(name = "ID",length = 32, nullable = false)
    @NotNull
    @Size(max = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "SECTION_CODE",nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    @Basic
    @Column(name = "SECTION_NAME",length = 50, nullable = false)
    @NotNull
    @Size(max = 50)
    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    @Version
    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }


    @Basic
    @Column(name = "OPER_DATE",nullable = false)
    @NotNull
    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operDate) {
        this.operationDate = operDate;
    }

    @Basic
    @Column(name = "RULE_PRICE")
    public BigDecimal getRulePrice() {
        return rulePrice;
    }

    public void setRulePrice(BigDecimal rulePrice) {
        this.rulePrice = rulePrice;
    }

    @Basic
    @Column(name = "CALC_RULE", length = 50)
    @Size(max = 50)
    public String getCalcRule() {
        return calcRule;
    }

    public void setCalcRule(String calcRule) {
        this.calcRule = calcRule;
    }

    @Basic
    @Column(name = "PAY_PERSON", length = 64)
    @Size(max = 64)
    public String getPayPerson() {
        return payPerson;
    }

    public void setPayPerson(String payPerson) {
        this.payPerson = payPerson;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "PAY_TYPE",length = 4 , nullable = false)
    @NotNull
    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }


    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "BANK_OPER_ORDER")
    public BankAccountDetailsEntity getBankAccountDetails() {
        return bankAccountDetails;
    }

    public void setBankAccountDetails(BankAccountDetailsEntity bankAccountDetails) {
        this.bankAccountDetails = bankAccountDetails;
    }

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "NOTICE_ID")
    public PaymentNoticeEntity getPaymentNotice() {
        return paymentNotice;
    }

    public void setPaymentNotice(PaymentNoticeEntity paymentNoticeEntity) {
        this.paymentNotice = paymentNoticeEntity;
    }

    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "ID")
    @MapsId
    public BusinessEntity getBusiness() {
        return business;
    }

    public void setBusiness(BusinessEntity businessEntity) {
        this.business = businessEntity;
    }

    @Column(name = "MUST_MONEY", nullable = false)
    @NotNull
    public BigDecimal getMustMoney() {
        return mustMoney;
    }

    public void setMustMoney(BigDecimal mustMoney) {
        this.mustMoney = mustMoney;
    }

    @Column(name = "MONEY", nullable = false)
    @NotNull
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "paymentEntity",cascade = CascadeType.ALL,orphanRemoval = true)
    public Set<PaymentBusinessEntity> getPaymentBusinesses() {
        return paymentBusinesses;
    }

    public void setPaymentBusinesses(Set<PaymentBusinessEntity> paymentBusinessEntities) {
        this.paymentBusinesses = paymentBusinessEntities;
    }

    @Transient
    public List<PaymentBusinessEntity> getPaymentBusinessList(){
        List<PaymentBusinessEntity> result = new ArrayList<>(getPaymentBusinesses());
        Collections.sort(result, new Comparator<PaymentBusinessEntity>() {
            @Override
            public int compare(PaymentBusinessEntity o1, PaymentBusinessEntity o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentEntity that = (PaymentEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }
}
