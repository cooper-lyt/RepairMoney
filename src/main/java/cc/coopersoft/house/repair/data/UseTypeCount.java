package cc.coopersoft.house.repair.data;

import cc.coopersoft.house.repair.data.model.HouseEntity;

public class UseTypeCount implements java.io.Serializable{

    private HouseEntity.UseType useType;

    private long count;

    public UseTypeCount(HouseEntity.UseType useType, long count) {
        this.useType = useType;
        this.count = count;
    }

    public HouseEntity.UseType getUseType() {
        return useType;
    }

    public long getCount() {
        return count;
    }
}
