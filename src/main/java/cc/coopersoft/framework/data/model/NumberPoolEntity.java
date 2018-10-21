package cc.coopersoft.framework.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "NUMBER_POOL")
public class NumberPoolEntity implements java.io.Serializable{

    public enum Type{
        DATE,
        MONTH,
        YEAR
    }

    private String id;
    private long number;
    private Type type;
    private int version;
    private String description;
    private Date numberDate;
    private int length;
    private String prefix;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NUMBER")
    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE",length = 16, nullable = false)
    @NotNull
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Version
    @Column(name = "VERSION",nullable = false)
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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

    @Basic
    @Column(name = "NUMBER_DATE")
    @NotNull
    public Date getNumberDate() {
        return numberDate;
    }

    public void setNumberDate(Date numberDate) {
        this.numberDate = numberDate;
    }

    @Basic
    @Column(name = "LENGTH",nullable = false)
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Basic
    @Column(name = "PREFIX",length = 16)
    @Size(max = 16)
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumberPoolEntity that = (NumberPoolEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }
}
