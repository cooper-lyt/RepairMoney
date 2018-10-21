package cc.coopersoft.framework.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "SYSTEM_PARAM")
public class SystemParamEntity {

    private String key;
    private String value;
    private String description;


    @Id
    @NotNull
    @Column(name = "_KEY",nullable = false,length = 32)
    @Size(max = 32)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column(name = "_VALUE",nullable = false,length = 512)
    @NotNull
    @Size(max = 512)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "DESCRIPTION",length = 512)
    @Size(max = 512)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SystemParamEntity that = (SystemParamEntity) o;

        if (key != null ? !key.equals(that.key) : that.key != null) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        return result;
    }
}
