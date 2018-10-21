package cc.coopersoft.house.repair.data.repository;

import cc.coopersoft.house.repair.data.model.HouseAccountEntity;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface HouseAccountRepository extends EntityRepository<HouseAccountEntity, String> {

    HouseAccountEntity findOptionalByHouseCode(String code);
}
