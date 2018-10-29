package cc.coopersoft.house.repair.data.repository;

import cc.coopersoft.house.repair.data.model.HouseAccountEntity;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HouseAccountRepository extends EntityRepository<HouseAccountEntity, String> {

    HouseAccountEntity findOptionalByHouseCode(String code);

    @Query("SELECT max(ad.operationTime) FROM AccountDetailsEntity ad where ad.houseAccount.houseCode in (?1) and ad.status <> 'DELETED'" )
    Date queryLastChangeDate(List<String> houseCodes);
}
