package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PUT_ACCOUNT_BOOK", schema = "WXZJ", catalog = "")
public class PutAccountBookEntity {

    private long id;
    private String type;
    private boolean put;
    private BusinessEntity business;
    private PutAccountBusinessEntity putAccountBusiness;

    @Id
    @Column(name = "PUT_ID", nullable = false, unique = true)
    @GeneratedValue
    @NotNull
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "BUSINESS", nullable = false)
    @NotNull
    public BusinessEntity getBusiness() {
        return business;
    }

    public void setBusiness(BusinessEntity business) {
        this.business = business;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public PutAccountBusinessEntity getPutAccountBusiness() {
        return putAccountBusiness;
    }

    public void setPutAccountBusiness(PutAccountBusinessEntity putAccountBusiness) {
        this.putAccountBusiness = putAccountBusiness;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PutAccountBookEntity that = (PutAccountBookEntity) o;


        if (that.id != id) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = Long.valueOf(id).hashCode();

        return result;
    }
}
