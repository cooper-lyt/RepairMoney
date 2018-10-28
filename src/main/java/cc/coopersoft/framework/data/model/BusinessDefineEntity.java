package cc.coopersoft.framework.data.model;

import cc.coopersoft.framework.data.TaskAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "BUSINESS_DEFINE")
public class BusinessDefineEntity implements java.io.Serializable{

    private String id;
    private String name;
    private String wfName;
    private String startPage;
    private String memo;
    private Integer version;
    private String rolePrefix;
    private String description;
    private int priority;
    private boolean enable;
    private int defineVersion;

    private Set<SubscribeGroupEntity> subscribeGroups = new HashSet<>(0);
    private Set<TaskActionEntity> taskActions = new HashSet<>(0);
    private Set<DeleteActionEntity> deleteActions = new HashSet<>(0);


    @Id
    @Column(name = "ID",nullable = false,length = 32, unique = true)
    @Size(max = 32)
    @NotNull
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME", nullable = false, length = 64)
    @Size(max = 64)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "WF_NAME", length = 32)
    @Size(max = 32)
    public String getWfName() {
        return wfName;
    }

    public void setWfName(String wfName) {
        this.wfName = wfName;
    }

    @Basic
    @Column(name = "START_PAGE", length = 256)
    @Size(max = 256)
    public String getStartPage() {
        return startPage;
    }

    public void setStartPage(String startPage) {
        this.startPage = startPage;
    }

    @Basic
    @Column(name = "MEMO",length = 512)
    @Size(max = 512)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Version
    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(name="WF_VER",nullable = false)
    public int getDefineVersion() {
        return defineVersion;
    }

    public void setDefineVersion(int defineVersion) {
        this.defineVersion = defineVersion;
    }

    @Basic
    @Column(name = "ROLE_PREFIX", length = 16)
    @Size(max = 16)
    public String getRolePrefix() {
        return rolePrefix;
    }

    public void setRolePrefix(String rolePrefix) {
        this.rolePrefix = rolePrefix;
    }

    @Basic
    @Column(name = "DESCRIPTION", length = 512)
    @Size(max = 512)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    @Column(name = "ENABLE", nullable = false)
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "businessDefine", orphanRemoval = true)
    public Set<SubscribeGroupEntity> getSubscribeGroups() {
        return subscribeGroups;
    }

    public void setSubscribeGroups(Set<SubscribeGroupEntity> subscribeGroups) {
        this.subscribeGroups = subscribeGroups;
    }

    @Transient
    public List<SubscribeGroupEntity> getSubscribeGroupList(){
        List<SubscribeGroupEntity> result = new ArrayList<>(getSubscribeGroups());
        Collections.sort(result);
        return result;
    }

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "businessDefine", orphanRemoval = true)
    public Set<TaskActionEntity> getTaskActions() {
        return taskActions;
    }

    public void setTaskActions(Set<TaskActionEntity> taskActions) {
        this.taskActions = taskActions;
    }

    @Transient
    public List<TaskActionEntity> getTaskActionList(){
        List<TaskActionEntity> result = new ArrayList<>(getTaskActions());
        Collections.sort(result);
        return result;
    }

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "businessDefine", orphanRemoval = true)
    public Set<DeleteActionEntity> getDeleteActions() {
        return deleteActions;
    }

    public void setDeleteActions(Set<DeleteActionEntity> deleteActions) {
        this.deleteActions = deleteActions;
    }

    @Transient
    public List<DeleteActionEntity> getDeleteActionList(){
        List<DeleteActionEntity> result = new ArrayList<>(getDeleteActions());
        Collections.sort(result);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessDefineEntity that = (BusinessDefineEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }
}
