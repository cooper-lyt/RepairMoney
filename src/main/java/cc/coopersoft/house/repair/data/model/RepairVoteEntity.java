package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "REPAIR_VOTE", schema = "WXZJ", catalog = "")
public class RepairVoteEntity {
    private long id;
    private String project;
    private BigDecimal area;
    private BigDecimal acceptArea;
    private int ownerCount;
    private int acceptOwner;
    private Integer version;
    private int round;

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

    @Basic
    @Column(name = "PROJECT")
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Basic
    @Column(name = "AREA")
    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    @Basic
    @Column(name = "ACCEPT_AREA")
    public BigDecimal getAcceptArea() {
        return acceptArea;
    }

    public void setAcceptArea(BigDecimal acceptArea) {
        this.acceptArea = acceptArea;
    }

    @Basic
    @Column(name = "OWNER_COUNT")
    public int getOwnerCount() {
        return ownerCount;
    }

    public void setOwnerCount(int ownerCount) {
        this.ownerCount = ownerCount;
    }

    @Basic
    @Column(name = "ACCEPT_OWNER")
    public int getAcceptOwner() {
        return acceptOwner;
    }

    public void setAcceptOwner(int acceptOwner) {
        this.acceptOwner = acceptOwner;
    }

    @Basic
    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Basic
    @Column(name = "ROUND")
    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepairVoteEntity that = (RepairVoteEntity) o;


        if (that.id != id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Long.valueOf(id).hashCode();
        return result;
    }
}
