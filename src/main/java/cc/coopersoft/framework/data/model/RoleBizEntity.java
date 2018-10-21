package cc.coopersoft.framework.data.model;

import javax.persistence.*;

@Entity
@Table(name = "ROLE_BIZ", schema = "BUSSINESS_SYSTEM", catalog = "")
@IdClass(RoleBizEntityPK.class)
public class RoleBizEntity {
    private String role;
    private String bizId;

    @Id
    @Column(name = "ROLE")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Id
    @Column(name = "BIZ_ID")
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

        RoleBizEntity that = (RoleBizEntity) o;

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
