package cc.coopersoft.house.repair.data.repository;

import cc.coopersoft.framework.ConditionAdapter;
import cc.coopersoft.framework.ConditionQuery;
import cc.coopersoft.framework.data.KeyAndCount;
import cc.coopersoft.house.repair.data.model.BusinessEntity;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public abstract class BusinessRepository extends AbstractEntityRepository<BusinessEntity,String> {

    private final static ConditionQuery.ConditionField[] conditionFields = {
            ConditionQuery.instanceField("b.memo", ConditionAdapter.MatchType.CONTAIN),
            ConditionQuery.instanceField("b.id", ConditionAdapter.MatchType.FULL),
            ConditionQuery.instanceField("b.searchKey", ConditionAdapter.MatchType.CONTAIN)
    };

    private ConditionQuery buildConditionQuery(List<ConditionAdapter> conditions,List<String> defineIds){
        return ConditionQuery.instance(conditions,defineIds.isEmpty() ? null : " b.defineId in (:defines) " ,conditionFields);
    }

    private <T> TypedQuery<T> parameterization(ConditionQuery conditionQuery, TypedQuery<T> query, List<String> defineIds){
        TypedQuery<T> result = conditionQuery.parameterization(query);
        if (!defineIds.isEmpty()){
            result = result.setParameter("defines", defineIds);
        }
        return result;
    }

    public List<BusinessEntity> queryByKey(List<ConditionAdapter> conditions,List<String> defineIds,int offset, int count){
        ConditionQuery conditionQuery =  buildConditionQuery(conditions,defineIds);
        TypedQuery<BusinessEntity> query = typedQuery("SELECT b FROM BusinessEntity b left join fetch b.payment " +
                conditionQuery.where() + "order by b.regTime desc, b.dataTime desc").setFirstResult(offset).setMaxResults(count);
        return parameterization(conditionQuery,query,defineIds).getResultList();
    }

    public Long queryCountByKey(List<ConditionAdapter> conditions, List<String> defineIds){
        ConditionQuery conditionQuery =  buildConditionQuery(conditions,defineIds);
        TypedQuery<Long> query = entityManager().createQuery("SELECT COUNT(b) FROM BusinessEntity b " + conditionQuery.where(),Long.class);
        return parameterization(conditionQuery,query,defineIds).getSingleResult();
    }

    public List<KeyAndCount> queryByKeyDefineGroup(List<ConditionAdapter> conditions,List<String> defineIds){
        ConditionQuery conditionQuery =  buildConditionQuery(conditions,defineIds);
        TypedQuery<KeyAndCount> query = entityManager().createQuery(" SELECT new cc.coopersoft.framework.data.KeyAndCount(b.defineId,b.defineName,count(b)) FROM BusinessEntity b " +
                conditionQuery.where() + " group by b.defineId", KeyAndCount.class);
        return parameterization(conditionQuery,query,defineIds).getResultList();
    }

}
