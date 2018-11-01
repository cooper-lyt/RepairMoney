package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "PUT_ACCOUNT_BUSINESS", schema = "WXZJ", catalog = "")
public class PutAccountBusinessEntity {
    private long id;
    private String business;
    private Date putDate;
    private int cerNumber;
    private String cerWord;

    private PutAccountBookEntity putAccountBook;

    @Id
    @Column(name = "ID", nullable = false, unique = true)
    @NotNull
    public long getId() {
        return id;
    }

    public void setId(long id) {
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
    public Date getPutDate() {
        return putDate;
    }

    public void setPutDate(Date putDate) {
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

    @OneToOne(fetch = FetchType.LAZY,optional = false,cascade ={CascadeType.DETACH,CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
    @PrimaryKeyJoinColumn
    public PutAccountBookEntity getPutAccountBook() {
        return putAccountBook;
    }

    public void setPutAccountBook(PutAccountBookEntity putAccountBookEntity) {
        this.putAccountBook = putAccountBookEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PutAccountBusinessEntity that = (PutAccountBusinessEntity) o;


        if (that.id != id) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = Long.valueOf(id).hashCode();

        return result;
    }
}
