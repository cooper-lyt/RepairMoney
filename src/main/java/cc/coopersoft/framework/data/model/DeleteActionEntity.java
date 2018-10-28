package cc.coopersoft.framework.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "DELETE_ACTION")
public class DeleteActionEntity implements Comparable<DeleteActionEntity>,java.io.Serializable{

    private long id;
    private String regName;
    private int priority;

    private BusinessDefineEntity businessDefine;


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
    @Column(name = "REG_NAME", length = 32, nullable = false)
    @NotNull
    @Size(max = 32)
    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName;
    }

    @Basic
    @Column(name = "PRIORITY", nullable = false)
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "DEFINE_ID", nullable = false)
    @NotNull
    public BusinessDefineEntity getBusinessDefine() {
        return businessDefine;
    }

    public void setBusinessDefine(BusinessDefineEntity businessDefine) {
        this.businessDefine = businessDefine;
    }

    @Override
    public int compareTo(DeleteActionEntity o) {
        return Integer.valueOf(priority).compareTo(o.getPriority());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeleteActionEntity that = (DeleteActionEntity) o;


        if (that.id != id) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = Long.valueOf(id).hashCode();
        return result;
    }
}
