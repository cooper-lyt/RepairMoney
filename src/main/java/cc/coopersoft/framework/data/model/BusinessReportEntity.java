package cc.coopersoft.framework.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "BUSINESS_REPORT")
public class BusinessReportEntity {

    private String taskName;
    private long id;
    private int priority;


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
    @Column(name = "TASK_NAME")
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }



    @Basic
    @Column(name = "PRIORITY")
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessReportEntity that = (BusinessReportEntity) o;


        if (that.id != id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Long.valueOf(id).hashCode();
        return result;
    }
}
