package cc.coopersoft.framework.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TASK_ACTION")
public class TaskActionEntity implements Comparable<TaskActionEntity>,java.io.Serializable{

    public enum Type{
        VALID,
        ACTION
    }

    public enum Position{
        BEFORE,
        AFTER,
        ABORT,
        REVOKE,
        DELETE

    }
    private long id;
    private String taskName;
    private Type type;
    private String regName;
    private Position position;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "POSITION", length = 8, nullable = false)
    @NotNull
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskActionEntity that = (TaskActionEntity) o;


        if (that.id != id) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = Long.valueOf(id).hashCode();
        return result;
    }

    @Override
    public int compareTo(TaskActionEntity o) {
        return Integer.valueOf(priority).compareTo(o.getPriority());
    }
}
