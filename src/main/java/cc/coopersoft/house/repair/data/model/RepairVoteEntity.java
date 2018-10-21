package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "REPAIR_VOTE", schema = "WXZJ", catalog = "")
public class RepairVoteEntity {
    private String id;
    private String project;
    private BigDecimal area;
    private BigDecimal acceptArea;
    private int ownerCount;
    private int acceptOwner;
    private Integer version;
    private int round;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

        if (ownerCount != that.ownerCount) return false;
        if (acceptOwner != that.acceptOwner) return false;
        if (round != that.round) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (project != null ? !project.equals(that.project) : that.project != null) return false;
        if (area != null ? !area.equals(that.area) : that.area != null) return false;
        if (acceptArea != null ? !acceptArea.equals(that.acceptArea) : that.acceptArea != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (project != null ? project.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (acceptArea != null ? acceptArea.hashCode() : 0);
        result = 31 * result + ownerCount;
        result = 31 * result + acceptOwner;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + round;
        return result;
    }
}
