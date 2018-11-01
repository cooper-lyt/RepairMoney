package cc.coopersoft.house.repair.data.model;

import cc.coopersoft.framework.data.BusinessInstance;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "BUSINESS", schema = "WXZJ")
public class BusinessEntity implements java.io.Serializable, BusinessInstance {

    private String id;
    private Integer version;
    private Source source;
    private String memo;
    private String defineName;
    private String defineId;
    private int defineVersion;
    private Date regTime;
    private Date dataTime;
    private BusinessStatus status;
    private boolean isReg;
    private String searchKey;
    private String summary;

    private PaymentEntity payment;
    private Set<AccountDetailsEntity> accountDetails = new HashSet<>(0);
    private Set<BankAccountDetailsEntity> bankAccountDetails = new HashSet<>(0);
    private Set<PutAccountBookEntity> putAccountBooks = new HashSet<>(0);

    public BusinessEntity() {
    }

    public BusinessEntity(String id, Source source) {
        this.id = id;
        this.source = source;
        this.isReg = false;
        this.status = BusinessStatus.RUNNING;
        this.dataTime = new Date();
    }

    @Override
    @Id
    @Column(name = "ID", length = 32, nullable = false, unique = true)
    @NotNull
    @Size(max = 32)
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Version
    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    @Enumerated(EnumType.STRING)
    @Column(name = "SOURCE",nullable = false, length = 20)
    @NotNull
    public Source getSource() {
        return source;
    }

    @Override
    public void setSource(Source source) {
        this.source = source;
    }

    @Override
    @Basic
    @Column(name = "MEMO",length = 256)
    @Size(max = 256)
    public String getMemo() {
        return memo;
    }

    @Override
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    @Basic
    @Column(name = "DEFINE_NAME", nullable = false,length = 50)
    @Size(max = 50)
    @NotNull
    public String getDefineName() {
        return defineName;
    }

    @Override
    public void setDefineName(String defineName) {
        this.defineName = defineName;
    }

    @Override
    @Basic
    @Column(name = "DEFINE_ID",nullable = false, length = 32)
    @Size(max = 32)
    @NotNull
    public String getDefineId() {
        return defineId;
    }

    @Override
    public void setDefineId(String defineId) {
        this.defineId = defineId;
    }

    @Override
    @Basic
    @Column(name = "DEFINE_VERSION", nullable = false)
    public int getDefineVersion() {
        return defineVersion;
    }

    @Override
    public void setDefineVersion(int defineVersion) {
        this.defineVersion = defineVersion;
    }

    @Override
    @Basic
    @Column(name = "REG_TIME",nullable = false)
    @NotNull
    public Date getRegTime() {
        return regTime;
    }

    @Override
    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    @Override
    @Column(name = "REG",nullable = false)
    public boolean isReg() {
        return isReg;
    }

    @Override
    public void setReg(boolean reg) {
        isReg = reg;
    }

    @Override
    @Basic
    @Column(name = "DATA_TIME",nullable = false)
    @NotNull
    public Date getDataTime() {
        return dataTime;
    }

    @Override
    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    @Override
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS",length = 8)
    @NotNull
    public BusinessStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(BusinessStatus status) {
        this.status = status;
    }


    @Column(name = "SEARCH_KEY",length = 1024)
    @Size(max = 1024)
    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    @Column(name = "SUMMARY",length = 1024)
    @Size(max = 1024)
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "business")
    public Set<AccountDetailsEntity> getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(Set<AccountDetailsEntity> accountDetailsEntities) {
        this.accountDetails = accountDetailsEntities;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "business" )
    public Set<BankAccountDetailsEntity> getBankAccountDetails() {
        return bankAccountDetails;
    }

    public void setBankAccountDetails(Set<BankAccountDetailsEntity> bankAccountDetails) {
        this.bankAccountDetails = bankAccountDetails;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "business")
    public Set<PutAccountBookEntity> getPutAccountBooks() {
        return putAccountBooks;
    }

    public void setPutAccountBooks(Set<PutAccountBookEntity> putAccountBooks) {
        this.putAccountBooks = putAccountBooks;
    }

    @OneToOne(fetch = FetchType.LAZY,orphanRemoval = true,cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity paymentEntity) {
        this.payment = paymentEntity;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessEntity that = (BusinessEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;

        return result;
    }
}
