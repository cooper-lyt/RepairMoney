package cc.coopersoft.house.repair.data.repository;

import cc.coopersoft.house.repair.data.model.PaymentBusinessEntity;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PaymentRepository extends EntityRepository<PaymentBusinessEntity,Long> {

    @Query("SELECT p FROM PaymentBusinessEntity p left join fetch p.accountDetails a where a.status in ('REG','USING','CANCEL') and a.operationTime >= ?1 and a.operationTime <= ?2 order by a.operationTime, p.id")
    List<PaymentBusinessEntity> queryDatePayment(Date begin, Date end);


}
