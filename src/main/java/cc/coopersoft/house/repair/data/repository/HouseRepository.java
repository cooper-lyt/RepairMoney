package cc.coopersoft.house.repair.data.repository;

import cc.coopersoft.house.repair.data.model.HouseEntity;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface HouseRepository extends EntityRepository<HouseEntity,Long> {
}
