package cc.coopersoft.house.repair.data;

import cc.coopersoft.framework.tools.DataHelper;

public class HouseMapId {

    private String map;
    private String block;
    private String build;
    private String house;

    public HouseMapId() {
    }

    public HouseMapId(String map, String block, String build, String house) {
        setMap(map);
        setBlock(block);
        setBuild(build);
        setHouse(house);
    }

    public String getMap() {
        return map;
    }

    public void setMap(String mapNumber) {
        if (mapNumber != null){
            this.map = mapNumber.trim();
        }else
            this.map = null;
    }

    public boolean isEmptyMap(){
        return DataHelper.empty(map);
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String blockNumber) {
        if (blockNumber != null) {
            this.block = blockNumber.trim();
        }else
            this.block = null;
    }

    public boolean isEmptyBlock(){
        return DataHelper.empty(block);
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String buildNumber) {
        if (buildNumber != null){
            this.build = buildNumber.trim();
        }else
            this.build = null;
    }

    public boolean isEmptyBuild(){
        return DataHelper.empty(build);
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String houseOrder) {
        if (houseOrder != null){
            this.house = houseOrder.trim();
        }else{
            this.house = null;
        }
    }

    public boolean isEmptyHouse(){
        return DataHelper.empty(house);
    }


    public boolean isEmpty(){
        return isEmptyMap() && isEmptyBlock() && isEmptyBuild() && isEmptyHouse();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HouseMapId that = (HouseMapId) o;

        if (map != null ? !map.equals(that.map) : that.map != null) return false;
        if (block != null ? !block.equals(that.block) : that.block != null) return false;
        if (build != null ? !build.equals(that.build) : that.build != null) return false;
        return house != null ? house.equals(that.house) : that.house == null;
    }

    @Override
    public int hashCode() {
        int result = map != null ? map.hashCode() : 0;
        result = 31 * result + (block != null ? block.hashCode() : 0);
        result = 31 * result + (build != null ? build.hashCode() : 0);
        result = 31 * result + (house != null ? house.hashCode() : 0);
        return result;
    }
}
