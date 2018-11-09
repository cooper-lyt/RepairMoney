package cc.coopersoft.house.repair.data.repository;

import cc.coopersoft.house.repair.data.AccountStatus;
import cc.coopersoft.house.repair.data.model.BankAccountEntity;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends EntityRepository<BankAccountEntity,String> {

    List<BankAccountEntity> findByStatusOrderByCreateTimeDescBankAccountId(AccountStatus status);

}
