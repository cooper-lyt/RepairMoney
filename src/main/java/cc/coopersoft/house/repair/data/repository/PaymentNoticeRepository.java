package cc.coopersoft.house.repair.data.repository;

import cc.coopersoft.house.repair.data.model.PaymentNoticeEntity;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface PaymentNoticeRepository extends EntityRepository<PaymentNoticeEntity,String> {

    @Query("SELECT count(p.id) from PaymentNoticeEntity p left join p.payments pp left join pp.business b where b.status <> 'ABORT' AND  b.status <> 'DELETED' and p.id = ?1" )
    long queryNoticeUseCount(String number);
}
