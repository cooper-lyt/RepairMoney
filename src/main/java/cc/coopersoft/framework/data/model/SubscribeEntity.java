package cc.coopersoft.framework.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "SUBSCRIBE")
public class SubscribeEntity implements java.io.Serializable, Comparable<SubscribeEntity> {

    private long id;
    private String regName;
    private int priority;
    private SubscribeGroupEntity subscribeGroup;

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
    @JoinColumn(name = "GROUP_ID",nullable = false)
    public SubscribeGroupEntity getSubscribeGroup() {
        return subscribeGroup;
    }

    public void setSubscribeGroup(SubscribeGroupEntity subscribeGroupEntity) {
        this.subscribeGroup = subscribeGroupEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubscribeEntity that = (SubscribeEntity) o;

        if (that.id != id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Long.valueOf(id).hashCode();
        return result;
    }

    @Override
    public int compareTo(SubscribeEntity o) {
        SubscribeEntity that = (SubscribeEntity) o;
        return Integer.valueOf(priority).compareTo(o.getPriority());
    }
}
