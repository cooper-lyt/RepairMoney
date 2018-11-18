package cc.coopersoft.house.repair.data.model;

import cc.coopersoft.framework.data.BusinessOperationLog;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "BUSINESS_LOG")
public class BusinessLogEntity implements java.io.Serializable, BusinessOperationLog {


    private long id;
    private Type type;
    private String empCode;
    private String empName;
    private Date operationTime;
    private String comments;
    private String taskName;
    private String taskDescription;
    private Long taskId;

    private BusinessEntity business;

    public BusinessLogEntity() {
    }

    public BusinessLogEntity(BusinessEntity business) {
        this.business = business;
    }

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

    @Override
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE",nullable = false,length = 16)
    @NotNull
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    @Basic
    @Column(name = "EMP_CODE",length = 32,nullable = false)
    @Size(max = 32)
    @NotNull
    public String getEmpCode() {
        return empCode;
    }

    @Override
    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    @Override
    @Basic
    @Column(name = "EMP_NAME",length = 64,nullable = false)
    @Size(max = 64)
    @NotNull
    public String getEmpName() {
        return empName;
    }

    @Override
    public void setEmpName(String empName) {
        this.empName = empName;
    }

    @Basic
    @Column(name = "OPER_TIME",nullable = false)
    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operTime) {
        this.operationTime = operTime;
    }

    @Override
    @Basic
    @Column(name = "COMMENTS",length = 512)
    @Size(max = 512)
    public String getComments() {
        return comments;
    }

    @Override
    public void setComments(String comments) {
        this.comments = comments;
    }


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "BUSINESS",nullable = false)
    @NotNull
    public BusinessEntity getBusiness() {
        return business;
    }

    public void setBusiness(BusinessEntity business) {
        this.business = business;
    }

    @Override
    @Column(name = "TASK_NAME",length = 128,nullable = false)
    @NotNull
    @Size(max = 128)
    public String getTaskName() {
        return taskName;
    }

    @Override
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    @Column(name = "TASK_DESCRIPTION",length = 512)
    @Size(max = 512)
    public String getTaskDescription() {
        return taskDescription;
    }

    @Override
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    @Override
    @Column(name = "TASK_ID")
    public Long getTaskId() {
        return taskId;
    }

    @Override
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessLogEntity that = (BusinessLogEntity) o;

        if (that.id != id) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = Long.valueOf(id).hashCode();

        return result;
    }
}
