package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "FIXING_REPAIR", schema = "WXZJ", catalog = "")
public class FixingRepairEntity {
    private String id;
    private String fixing;
    private String repairCompany;
    private String manager;
    private String tel;
    private BigDecimal money;
    private Date repairToDate;
    private Date repairDate;
    private String memo;
    private String description;
    private BigDecimal applyMoney;
    private String project;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "FIXING")
    public String getFixing() {
        return fixing;
    }

    public void setFixing(String fixing) {
        this.fixing = fixing;
    }

    @Basic
    @Column(name = "REPAIR_COMPANY")
    public String getRepairCompany() {
        return repairCompany;
    }

    public void setRepairCompany(String repairCompany) {
        this.repairCompany = repairCompany;
    }

    @Basic
    @Column(name = "MANAGER")
    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    @Basic
    @Column(name = "TEL")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Basic
    @Column(name = "MONEY")
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Basic
    @Column(name = "REPAIR_TO_DATE")
    public Date getRepairToDate() {
        return repairToDate;
    }

    public void setRepairToDate(Date repairToDate) {
        this.repairToDate = repairToDate;
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
    @Column(name = "MEMO")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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
    @Column(name = "APPLY_MONEY")
    public BigDecimal getApplyMoney() {
        return applyMoney;
    }

    public void setApplyMoney(BigDecimal applyMoney) {
        this.applyMoney = applyMoney;
    }

    @Basic
    @Column(name = "PROJECT")
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FixingRepairEntity that = (FixingRepairEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (fixing != null ? !fixing.equals(that.fixing) : that.fixing != null) return false;
        if (repairCompany != null ? !repairCompany.equals(that.repairCompany) : that.repairCompany != null)
            return false;
        if (manager != null ? !manager.equals(that.manager) : that.manager != null) return false;
        if (tel != null ? !tel.equals(that.tel) : that.tel != null) return false;
        if (money != null ? !money.equals(that.money) : that.money != null) return false;
        if (repairToDate != null ? !repairToDate.equals(that.repairToDate) : that.repairToDate != null) return false;
        if (repairDate != null ? !repairDate.equals(that.repairDate) : that.repairDate != null) return false;
        if (memo != null ? !memo.equals(that.memo) : that.memo != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (applyMoney != null ? !applyMoney.equals(that.applyMoney) : that.applyMoney != null) return false;
        if (project != null ? !project.equals(that.project) : that.project != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fixing != null ? fixing.hashCode() : 0);
        result = 31 * result + (repairCompany != null ? repairCompany.hashCode() : 0);
        result = 31 * result + (manager != null ? manager.hashCode() : 0);
        result = 31 * result + (tel != null ? tel.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        result = 31 * result + (repairToDate != null ? repairToDate.hashCode() : 0);
        result = 31 * result + (repairDate != null ? repairDate.hashCode() : 0);
        result = 31 * result + (memo != null ? memo.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (applyMoney != null ? applyMoney.hashCode() : 0);
        result = 31 * result + (project != null ? project.hashCode() : 0);
        return result;
    }
}
