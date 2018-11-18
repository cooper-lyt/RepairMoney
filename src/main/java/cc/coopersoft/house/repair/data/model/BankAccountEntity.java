package cc.coopersoft.house.repair.data.model;

import cc.coopersoft.house.repair.data.AccountStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "BANK_ACCOUNT")
public class BankAccountEntity {


    private String bankAccountId;
    private String bankName;
    private String bankCode;
    private String accountNumber;
    private String accountName;
    private String contacts;
    private String tel;
    private String address;
    private String description;
    private String memo;
    private int version;
    private BigDecimal frozen;
    private BigDecimal product;
    private BigDecimal totalProduct;
    private Date productDate;
    private Date createTime;
    private AccountStatus status;

    @Id
    @Column(name = "BANK_ACCOUNT_ID",unique = true, nullable = false,length = 32)
    @NotNull
    @Size(max = 32)
    public String getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    @Basic
    @Column(name = "BANK_NAME",length = 64,nullable = false)
    @NotNull
    @Size(max = 64)
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Basic
    @Column(name = "BANK_CODE",length = 16,nullable = false)
    @NotNull
    @Size(max = 16)
    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    @Basic
    @Column(name = "ACCOUNT_NUMBER",length = 128, nullable = false)
    @NotNull
    @Size(max = 128)
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Basic
    @Column(name = "ACCOUNT_NAME", length = 64, nullable = false)
    @NotNull
    @Size(max = 64)
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Basic
    @Column(name = "CONTACTS",length = 32)
    @Size(max = 32)
    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    @Basic
    @Column(name = "TEL", length = 16)
    @Size(max = 16)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Basic
    @Column(name = "ADDRESS",length = 256)
    @Size(max = 256)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "DESCRIPTION",length = 64)
    @Size(max = 64)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "MEMO", length = 256)
    @Size(max = 256)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Version
    @Column(name = "VERSION", nullable = false)
    public int  getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


    @Basic
    @Column(name = "FROZEN",nullable = false)
    @NotNull
    public BigDecimal getFrozen() {
        return frozen;
    }

    public void setFrozen(BigDecimal frozen) {
        this.frozen = frozen;
    }

    @Basic
    @Column(name = "PRODUCT")
    public BigDecimal getProduct() {
        return product;
    }

    public void setProduct(BigDecimal product) {
        this.product = product;
    }

    @Basic
    @Column(name = "TOTAL_PRODUCT")
    public BigDecimal getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(BigDecimal totalProduct) {
        this.totalProduct = totalProduct;
    }

    @Basic
    @Column(name = "PRODUCT_DATE", nullable = false)
    @NotNull
    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    @Basic
    @Column(name = "CREATE_TIME",nullable = false)
    @NotNull
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS",length = 8,nullable = false)
    @NotNull
    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankAccountEntity that = (BankAccountEntity) o;

        if (bankAccountId != null ? !bankAccountId.equals(that.bankAccountId) : that.bankAccountId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bankAccountId != null ? bankAccountId.hashCode() : 0;

        return result;
    }
}
