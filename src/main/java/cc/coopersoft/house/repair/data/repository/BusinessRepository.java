package cc.coopersoft.house.repair.data.repository;

import cc.coopersoft.framework.ConditionAdapter;
import cc.coopersoft.framework.data.KeyAndCount;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public abstract class BusinessRepository extends AbstractEntityRepository<BusinessEntity,String> {

    private String queryWhere(List<ConditionAdapter> conditions, List<String> defineIds){


        String result = "";
        if (!conditions.isEmpty() || !defineIds.isEmpty()){
            result += " WHERE ";


            if (!defineIds.isEmpty()){
                result += " b.defineId in (:defines) ";
            }

            if (!conditions.isEmpty()){
                if (!defineIds.isEmpty()){
                    result += " AND ";
                }
                result += " ( ";
                boolean first = true;
                for(ConditionAdapter c: conditions){
                    if (!first){
                        result += " OR ";
                    }
                    result += "b.memo like '%" + c.getCondition() +  "%' OR b.id = '" + c.getCondition() + "' OR b.searchKey like '" + c.getContains() + "' ";
                    first = false;
                }

                result += " ) ";
            }
        }

        return result;
    }

    public List<BusinessEntity> queryByKey(List<ConditionAdapter> conditions,List<String> defineIds,int offset, int count){
        TypedQuery<BusinessEntity> query = typedQuery("SELECT b FROM BusinessEntity b left join fetch b.payment " + queryWhere(conditions, defineIds) + "order by b.regTime desc, b.dataTime desc").setFirstResult(offset).setMaxResults(count);
        if (!defineIds.isEmpty()){
            query.setParameter("defines", defineIds);
        }
        return query.getResultList();
    }

    public Long countByKey(List<ConditionAdapter> conditions,List<String> defineIds){
        TypedQuery<Long> query = entityManager().createQuery("SELECT COUNT(b) FROM BusinessEntity b " + queryWhere(conditions, defineIds),Long.class);
        if (!defineIds.isEmpty()){
            query.setParameter("defines", defineIds);
        }
        return query.getSingleResult();
    }

    public List<KeyAndCount> queryByKeyDefineGroup(List<ConditionAdapter> conditions,List<String> defineIds){
        TypedQuery<KeyAndCount> query = entityManager().createQuery(" SELECT new cc.coopersoft.framework.data.KeyAndCount(b.defineId,b.defineName,count(b)) FROM BusinessEntity b " + queryWhere(conditions, defineIds) + " group by b.defineId", KeyAndCount.class);
        if (!defineIds.isEmpty()){
            query.setParameter("defines", defineIds);
        }
        return query.getResultList();
    }

}
