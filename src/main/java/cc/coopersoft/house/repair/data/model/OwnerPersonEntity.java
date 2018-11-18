package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "OWNER_PERSON", schema = "WXZJ")
public class OwnerPersonEntity {

    public enum CredentialsType{
        MASTER_ID//身份证
        ,SOLDIER_CARD//士兵证
        ,PASSPORT//护照
        ,COMPANY_CODE//营业执照
        ,OTHER//其它证件
        ,CORP_CODE//机构代码证
        ,TW_ID//台湾通行证
        ,OFFICER_CARD//军官证
        ,GA_ID//港澳通行证
    }

    private long ownerId;
    private CredentialsType credentialsType;
    private String credentialsNumber;
    private String name;
    private String tel;
    private int pri;
    private HouseEntity house;

    @Id
    @Column(name = "OWNER_ID",nullable = false, unique = true)
    @GeneratedValue
    @NotNull
    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "CREDENTIALS_TYPE")
    public CredentialsType getCredentialsType() {
        return credentialsType;
    }

    public void setCredentialsType(CredentialsType credentialsType) {
        this.credentialsType = credentialsType;
    }

    @Basic
    @Column(name = "CREDENTIALS_NUMBER")
    public String getCredentialsNumber() {
        return credentialsNumber;
    }

    public void setCredentialsNumber(String credentialsNumber) {
        this.credentialsNumber = credentialsNumber;
    }

    @Basic
    @Column(name = "NAME", length = 128, nullable = false)
    @Size(max = 128)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "TEL",length = 15)
    @Size(max = 64)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Basic
    @Column(name = "PRI", nullable = false)
    public int getPri() {
        return pri;
    }

    public void setPri(int pri) {
        this.pri = pri;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "HOUSE", nullable = false)
    @NotNull
    public HouseEntity getHouse() {
        return house;
    }

    public void setHouse(HouseEntity house) {
        this.house = house;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OwnerPersonEntity that = (OwnerPersonEntity) o;

        if (that.ownerId != ownerId) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = Long.valueOf(ownerId).hashCode();

        return result;
    }
}
