package cc.coopersoft.house.repair.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name = "HOUSE", schema = "WXZJ")
public class HouseEntity {

    public enum HouseProperty {
        SALE_HOUSE,
        BACK_HOUSE,
        PUBLIC_RENT,
        GOV_RENT,
        LIMIT_PRICE_HOUSE,
        GOV_SALE_HOUSE,
        TARGET_HOUSE,
        GROUP_HOUSE,
        WELFARE_HOUSE,
        COMM_USE_HOUSE,
        OTHER,
        SELF_CREATE,
        GOV_GROUP_HOUSE,
        STORE_HOUSE;

    }

    public enum UseType {
        DWELLING_KEY,
        SHOP_HOUSE_KEY,
        STORE_HOUSE,
        OFFICE,
        CAR_STORE,
        INDUSTRY,
        OTHER;
    }

    public enum BuildType{
        HIGH, //高层
        MANY, //多层
        VILLA, //别墅
        SINGLE //平房
    }

    private long id;
    private String districtCode;
    private String districtName;
    private String sectionCode;
    private String sectionName;
    private String buildCode;
    private String buildName;
    private String houseCode;
    private String unitName;
    private String floorName;
    private Integer inFloor;
    private String mapNumber;
    private String blockNumber;
    private String buildNumber;
    private String houseOrder;
    private String houseAddress;
    private BigDecimal houseArea;
    private BigDecimal useArea;
    private BigDecimal commArea;
    private HouseProperty houseType;
    private String structure;
    private UseType useType;
    private String designType;
    private BuildType buildType;
    private Integer floorCount;
    private BigDecimal saleMoney;
    private Date dataTime;

    private Set<OwnerPersonEntity> ownerPersons = new HashSet<OwnerPersonEntity>(0);

    @Id
    @Column(name = "ID", nullable = false, unique = true)
    @GeneratedValue
    @NotNull
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "DISTRICT_CODE", length = 12, nullable = false)
    @Size(max = 12)
    @NotNull
    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    @Basic
    @Column(name = "DISTRICT_NAME", length = 128, nullable = false)
    @Size(max = 128)
    @NotNull
    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    @Basic
    @Column(name = "SECTION_CODE", length = 32, nullable = false)
    @Size(max = 32)
    @NotNull
    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    @Basic
    @Column(name = "SECTION_NAME", length = 64, nullable = false)
    @Size(max = 64)
    @NotNull
    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    @Basic
    @Column(name = "BUILD_CODE", length = 32, nullable = false)
    @Size(max = 32)
    @NotNull
    public String getBuildCode() {
        return buildCode;
    }

    public void setBuildCode(String buildCode) {
        this.buildCode = buildCode;
    }

    @Basic
    @Column(name = "BUILD_NAME",length = 128, nullable = false)
    @Size(max = 128)
    @NotNull
    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    @Basic
    @Column(name = "HOUSE_CODE", length = 32, nullable = false)
    @Size(max = 32)
    @NotNull
    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    @Basic
    @Column(name = "UNIT_NAME",length = 32)
    @Size(max = 32)
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Basic
    @Column(name = "FLOOR_NAME",length = 64)
    @Size(max = 64)
    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    @Basic
    @Column(name = "IN_FLOOR")
    public Integer getInFloor() {
        return inFloor;
    }

    public void setInFloor(Integer inFloor) {
        this.inFloor = inFloor;
    }

    @Basic
    @Column(name = "MAP_NUMBER", length = 4)
    @Size(max = 4)
    public String getMapNumber() {
        return mapNumber;
    }

    public void setMapNumber(String mapNumber) {
        this.mapNumber = mapNumber;
    }

    @Basic
    @Column(name = "BLOCK_NUMBER", length = 10)
    @Size(max = 10)
    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    @Basic
    @Column(name = "BUILD_NUMBER", length = 24)
    @Size(max = 24)
    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    @Basic
    @Column(name = "HOUSE_ORDER",length = 20, nullable = false)
    @Size(max = 20)
    @NotNull
    public String getHouseOrder() {
        return houseOrder;
    }

    public void setHouseOrder(String houseOrder) {
        this.houseOrder = houseOrder;
    }

    @Basic
    @Column(name = "HOUSE_ADDRESS", length = 256, nullable = false)
    @NotNull
    @Size(max = 256)
    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    @Basic
    @Column(name = "HOUSE_AREA", nullable = false)
    @NotNull
    public BigDecimal getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(BigDecimal houseArea) {
        this.houseArea = houseArea;
    }

    @Basic
    @Column(name = "USE_AREA")
    public BigDecimal getUseArea() {
        return useArea;
    }

    public void setUseArea(BigDecimal useArea) {
        this.useArea = useArea;
    }

    @Basic
    @Column(name = "COMM_AREA")
    public BigDecimal getCommArea() {
        return commArea;
    }

    public void setCommArea(BigDecimal commArea) {
        this.commArea = commArea;
    }


    @Enumerated(EnumType.STRING)
    @Column(name = "HOUSE_TYPE",length = 32)
    public HouseProperty getHouseType() {
        return houseType;
    }

    public void setHouseType(HouseProperty houseType) {
        this.houseType = houseType;
    }

    @Basic
    @Column(name = "STRUCTURE",length = 32)
    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "USE_TYPE",length = 32)
    public UseType getUseType() {
        return useType;
    }

    public void setUseType(UseType useType) {
        this.useType = useType;
    }

    @Basic
    @Column(name = "DESIGN_TYPE",length = 512)
    @Size(max = 512)
    public String getDesignType() {
        return designType;
    }

    public void setDesignType(String designType) {
        this.designType = designType;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "BUILD_TYPE", length = 8)
    public BuildType getBuildType() {
        return buildType;
    }

    public void setBuildType(BuildType buildType) {
        this.buildType = buildType;
    }

    @Basic
    @Column(name = "FLOOR_COUNT")
    public Integer getFloorCount() {
        return floorCount;
    }

    public void setFloorCount(Integer floorCount) {
        this.floorCount = floorCount;
    }

    @Basic
    @Column(name = "SALE_MONEY")
    public BigDecimal getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(BigDecimal saleMoney) {
        this.saleMoney = saleMoney;
    }

    @Basic
    @Column(name = "DATA_TIME", nullable = false)
    @NotNull
    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "house" ,orphanRemoval = true,cascade = CascadeType.ALL)
    public Set<OwnerPersonEntity> getOwnerPersons() {
        return ownerPersons;
    }

    public void setOwnerPersons(Set<OwnerPersonEntity> ownerPersons) {
        this.ownerPersons = ownerPersons;
    }

    @Transient
    public List<OwnerPersonEntity> getOwnerPersonList(){
        List<OwnerPersonEntity> result = new ArrayList<OwnerPersonEntity>(getOwnerPersons());
        result.sort(new Comparator<OwnerPersonEntity>() {
            @Override
            public int compare(OwnerPersonEntity o1, OwnerPersonEntity o2) {
                return Integer.valueOf(o1.getPri()).compareTo(o2.getPri());
            }
        });
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HouseEntity that = (HouseEntity) o;

        if (that.id != id) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = Long.valueOf(id).hashCode();

        return result;
    }
}
