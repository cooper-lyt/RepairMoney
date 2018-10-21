package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;

@Entity
@Table(name = "PUT_ACCOUNT_BUSINESS", schema = "WXZJ", catalog = "")
public class PutAccountBusinessEntity {
    private String id;
    private String business;
    private String putDate;
    private int cerNumber;
    private String cerWord;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "BUSINESS")
    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    @Basic
    @Column(name = "PUT_DATE")
    public String getPutDate() {
        return putDate;
    }

    public void setPutDate(String putDate) {
        this.putDate = putDate;
    }

    @Basic
    @Column(name = "CER_NUMBER")
    public int getCerNumber() {
        return cerNumber;
    }

    public void setCerNumber(int cerNumber) {
        this.cerNumber = cerNumber;
    }

    @Basic
    @Column(name = "CER_WORD")
    public String getCerWord() {
        return cerWord;
    }

    public void setCerWord(String cerWord) {
        this.cerWord = cerWord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PutAccountBusinessEntity that = (PutAccountBusinessEntity) o;

        if (cerNumber != that.cerNumber) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (business != null ? !business.equals(that.business) : that.business != null) return false;
        if (putDate != null ? !putDate.equals(that.putDate) : that.putDate != null) return false;
        if (cerWord != null ? !cerWord.equals(that.cerWord) : that.cerWord != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (business != null ? business.hashCode() : 0);
        result = 31 * result + (putDate != null ? putDate.hashCode() : 0);
        result = 31 * result + cerNumber;
        result = 31 * result + (cerWord != null ? cerWord.hashCode() : 0);
        return result;
    }
}
