package cc.coopersoft.framework.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "SUBSCRIBE_GROUP")
public class SubscribeGroupEntity implements Comparable<SubscribeGroupEntity>, java.io.Serializable{

    @Override
    public int compareTo(SubscribeGroupEntity o) {
        return Integer.valueOf(priority).compareTo(o.getPriority());
    }

    public enum Type{
        VIEW,EDITOR
    }


    private long id;
    private String name;
    private int priority;
    private String taskName;
    private Type type;
    private String iconCss;

    private BusinessDefineEntity businessDefine;

    private Set<SubscribeEntity> subscribes = new HashSet<>(0);


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
    @Column(name = "NAME",length = 32, nullable = false)
    @NotNull
    @Size(max = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "PRIORITY", nullable = false)
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Basic
    @Column(name = "TASK_NAME",length = 128, nullable = false)
    @NotNull
    @Size(max = 128)
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE",length = 8, nullable = false)
    @NotNull
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Basic
    @Column(name = "ICON_CSS", length = 64)
    @Size(max = 64)
    public String getIconCss() {
        return iconCss;
    }

    public void setIconCss(String iconCss) {
        this.iconCss = iconCss;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "DEFINE_ID",nullable = false)
    @NotNull
    public BusinessDefineEntity getBusinessDefine() {
        return businessDefine;
    }

    public void setBusinessDefine(BusinessDefineEntity businessDefine) {
        this.businessDefine = businessDefine;
    }

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "subscribeGroup", orphanRemoval = true)
    public Set<SubscribeEntity> getSubscribes() {
        return subscribes;
    }

    public void setSubscribes(Set<SubscribeEntity> subscribeEntities) {
        this.subscribes = subscribeEntities;
    }

    @Transient
    public List<SubscribeEntity> getSubscribeList(){
        List<SubscribeEntity> result = new ArrayList<>(getSubscribes());
        Collections.sort(result);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubscribeGroupEntity that = (SubscribeGroupEntity) o;


        if (that.id != id) return false;



        return true;
    }

    @Override
    public int hashCode() {
        int result = Long.valueOf(id).hashCode() ;


        return result;
    }
}
