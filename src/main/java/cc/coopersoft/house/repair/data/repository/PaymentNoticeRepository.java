package cc.coopersoft.house.repair.data.repository;

import cc.coopersoft.house.repair.data.model.PaymentNoticeEntity;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface PaymentNoticeRepository extends EntityRepository<PaymentNoticeEntity,String> {
}
