package cc.coopersoft.framework.data.model;

import javax.persistence.*;

@Entity
@Table(name = "WORD_CATEGORY")
public class WordCategoryEntity {
    private String id;
    private String name;
    private String memo;
    private boolean system;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "MEMO")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Basic
    @Column(name = "SYSTEM")
    public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordCategoryEntity that = (WordCategoryEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;

        return result;
    }
}
