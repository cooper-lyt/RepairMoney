package cc.coopersoft.framework.data.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class RoleBizEntityPK implements Serializable {
    private String role;
    private String bizId;

    @Column(name = "ROLE")
    @Id
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Column(name = "BIZ_ID")
    @Id
    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleBizEntityPK that = (RoleBizEntityPK) o;

        if (role != null ? !role.equals(that.role) : that.role != null) return false;
        if (bizId != null ? !bizId.equals(that.bizId) : that.bizId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = role != null ? role.hashCode() : 0;
        result = 31 * result + (bizId != null ? bizId.hashCode() : 0);
        return result;
    }
}
