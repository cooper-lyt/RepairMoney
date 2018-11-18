package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "PAYMENT_NOTICE", schema = "WXZJ")
public class PaymentNoticeEntity implements java.io.Serializable {

    public enum Source{
        CREATE,
        OWNER
    }

    private String id;
    private BigDecimal mustMoney;
    private BigDecimal money;
    private String description;
    private String empCode;
    private String empName;
    private Date noticeTime;
    private Source source;
    private int version;


    private Set<PaymentEntity> payments = new HashSet<PaymentEntity>(0);
    private Set<PaymentNoticeItemEntity> noticeItems = new HashSet<PaymentNoticeItemEntity>(0);

    @Id
    @NotNull
    @Size(max = 32)
    @Column(name = "ID" ,length = 32  ,nullable = false, unique = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "MUST_MONEY",nullable = false)
    @NotNull
    public BigDecimal getMustMoney() {
        return mustMoney;
    }

    public void setMustMoney(BigDecimal mustMoney) {
        this.mustMoney = mustMoney;
    }

    @Column(name = "MONEY",nullable = false)
    @NotNull
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Column(name = "DESCRIPTION",nullable = false, length = 512)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "EMP_CODE", nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    @Column(name = "EMP_NAME", nullable = false, length = 64)
    @NotNull
    @Size(max = 64)
    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    @Column(name = "NOTICE_TIME", nullable = false)
    @NotNull
    public Date getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(Date noticeTime) {
        this.noticeTime = noticeTime;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "SOURCE", nullable = false, length = 8)
    @NotNull
    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @Version
    @Column(name = "VERSION", nullable = false)
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "paymentNotice")
    public Set<PaymentEntity> getPayments() {
        return payments;
    }

    public void setPayments(Set<PaymentEntity> paymentEntities) {
        this.payments = paymentEntities;
    }

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "paymentNotice", orphanRemoval = true, cascade = CascadeType.ALL)
    public Set<PaymentNoticeItemEntity> getNoticeItems() {
        return noticeItems;
    }

    public void setNoticeItems(Set<PaymentNoticeItemEntity> paymentNoticeItemEntities) {
        this.noticeItems = paymentNoticeItemEntities;
    }

    @Transient
    public List<PaymentNoticeItemEntity> getNoticeItemList(){
        List<PaymentNoticeItemEntity> result = new ArrayList<>(getNoticeItems());
        result.sort(new Comparator<PaymentNoticeItemEntity>() {
            @Override
            public int compare(PaymentNoticeItemEntity o1, PaymentNoticeItemEntity o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentNoticeEntity that = (PaymentNoticeEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;

        return result;
    }
}
