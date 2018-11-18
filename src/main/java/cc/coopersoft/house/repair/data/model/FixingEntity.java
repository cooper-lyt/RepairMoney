package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "FIXING", schema = "WXZJ", catalog = "")
public class FixingEntity {
    private String id;
    private String name;
    private String factory;
    private String brand;
    private String spec;
    private String useType;
    private Date repairDate;
    private int checkMonth;
    private String type;
    private String inType;
    private String inCode;
    private String memo;
    private int version;
    private String status;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "FACTORY")
    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    @Basic
    @Column(name = "BRAND")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Basic
    @Column(name = "SPEC")
    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    @Basic
    @Column(name = "USE_TYPE")
    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }

    @Basic
    @Column(name = "REPAIR_DATE")
    public Date getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(Date repairDate) {
        this.repairDate = repairDate;
    }

    @Basic
    @Column(name = "CHECK_MONTH")
    public int getCheckMonth() {
        return checkMonth;
    }

    public void setCheckMonth(int checkMonth) {
        this.checkMonth = checkMonth;
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
    @Column(name = "IN_TYPE")
    public String getInType() {
        return inType;
    }

    public void setInType(String inType) {
        this.inType = inType;
    }

    @Basic
    @Column(name = "IN_CODE")
    public String getInCode() {
        return inCode;
    }

    public void setInCode(String inCode) {
        this.inCode = inCode;
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
    @Column(name = "VERSION", nullable = false)
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Basic
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FixingEntity that = (FixingEntity) o;

        if (checkMonth != that.checkMonth) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (factory != null ? !factory.equals(that.factory) : that.factory != null) return false;
        if (brand != null ? !brand.equals(that.brand) : that.brand != null) return false;
        if (spec != null ? !spec.equals(that.spec) : that.spec != null) return false;
        if (useType != null ? !useType.equals(that.useType) : that.useType != null) return false;
        if (repairDate != null ? !repairDate.equals(that.repairDate) : that.repairDate != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (inType != null ? !inType.equals(that.inType) : that.inType != null) return false;
        if (inCode != null ? !inCode.equals(that.inCode) : that.inCode != null) return false;
        if (memo != null ? !memo.equals(that.memo) : that.memo != null) return false;

        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (factory != null ? factory.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (spec != null ? spec.hashCode() : 0);
        result = 31 * result + (useType != null ? useType.hashCode() : 0);
        result = 31 * result + (repairDate != null ? repairDate.hashCode() : 0);
        result = 31 * result + checkMonth;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (inType != null ? inType.hashCode() : 0);
        result = 31 * result + (inCode != null ? inCode.hashCode() : 0);
        result = 31 * result + (memo != null ? memo.hashCode() : 0);

        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
