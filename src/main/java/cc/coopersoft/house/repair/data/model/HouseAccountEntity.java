package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "HOUSE_ACCOUNT")
public class HouseAccountEntity {

    public enum Status{
        NORMAL,
        DESTROY,
        LOCKED

    }

    private String accountNumber;
    private BigDecimal balance;
    private BigDecimal frozen;
    private BigDecimal product;
    private BigDecimal totalProduct;
    private Date productDate;
    private Date createTime;
    private Status status;
    private String memo;
    private Integer version;
    private BigDecimal mustMoney;
    private String houseCode;

    private HouseEntity house;


    private Set<AccountDetailsEntity> accountDetails = new HashSet<>(0);

    @Id
    @Column(name = "ACCOUNT_NUMBER", length = 128, nullable = false, unique = true)
    @Size(max = 128)
    @NotNull
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Basic
    @Column(name = "BALANCE", nullable = false)
    @NotNull
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Basic
    @Column(name = "FROZEN", nullable = false)
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

    @Temporal(TemporalType.DATE)
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
    @Column(name = "STATUS",length = 8, nullable = false)
    @NotNull
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Transient
    public BigDecimal getValidBalance(){
        return getBalance().subtract(getFrozen());
    }


    @Basic
    @Column(name = "MUST_MONEY",nullable = false)
    @NotNull
    public BigDecimal getMustMoney() {
        return mustMoney;
    }

    public void setMustMoney(BigDecimal mustMoney) {
        this.mustMoney = mustMoney;
    }

    @Basic
    @Column(name = "HOUSE_CODE", unique = true,  nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "houseAccount")
    public Set<AccountDetailsEntity> getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(Set<AccountDetailsEntity> accountDetails) {
        this.accountDetails = accountDetails;
    }

    @Transient
    private List<AccountDetailsEntity> getAccountDetailList(List<AccountDetailsEntity> accountDetails){
        Collections.sort(accountDetails, (o1, o2) -> o2.getOperationTime().compareTo(o1.getOperationTime()));
        return accountDetails;
    }

    @Transient
    public List<AccountDetailsEntity> getAllDetailList(){
        return getAccountDetailList(new ArrayList<>(getAccountDetails()));
    }

    @Transient
    public List<AccountDetailsEntity> getValidDetailsList(){
        List<AccountDetailsEntity> result = new ArrayList<>();
        for(AccountDetailsEntity detail: getAccountDetails()){
            if (!AccountDetailsEntity.Status.DELETED.equals(detail.getStatus())){
                result.add(detail);
            }
        }
        return getAccountDetailList(result);
    }


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "HOUSE", nullable = false)
    public HouseEntity getHouse() {
        return house;
    }

    public void setHouse(HouseEntity houseEntity) {
        this.house = houseEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HouseAccountEntity that = (HouseAccountEntity) o;

        if (accountNumber != null ? !accountNumber.equals(that.accountNumber) : that.accountNumber != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accountNumber != null ? accountNumber.hashCode() : 0;
        return result;
    }
}
