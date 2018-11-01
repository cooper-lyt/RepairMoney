package cc.coopersoft.house.repair.data.repository;

import cc.coopersoft.house.repair.data.model.BankAccountDetailsEntity;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface BankAccountDetailsRepository extends EntityRepository<BankAccountDetailsEntity,String> {
}
