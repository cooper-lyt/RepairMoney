package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;

@Entity
@Table(name = "PUT_ACCOUNT_BOOK", schema = "WXZJ", catalog = "")
public class PutAccountBookEntity {
    private String putId;
    private String type;
    private boolean put;

    @Id
    @Column(name = "PUT_ID")
    public String getPutId() {
        return putId;
    }

    public void setPutId(String putId) {
        this.putId = putId;
    }

    @Basic
    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Basic
    @Column(name = "PUT")
    public boolean isPut() {
        return put;
    }

    public void setPut(boolean put) {
        this.put = put;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PutAccountBookEntity that = (PutAccountBookEntity) o;


        if (putId != null ? !putId.equals(that.putId) : that.putId != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = putId != null ? putId.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);

        return result;
    }
}
