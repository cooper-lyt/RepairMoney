package cc.coopersoft.house.repair.data.repository;

import cc.coopersoft.framework.ConditionAdapter;
import cc.coopersoft.framework.ConditionQuery;
import cc.coopersoft.house.repair.data.UseTypeCount;
import cc.coopersoft.house.repair.data.model.HouseAccountEntity;
import cc.coopersoft.house.repair.data.model.HouseEntity;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository
public abstract class HouseAccountRepository extends AbstractEntityRepository<HouseAccountEntity,String> {

    private final static ConditionQuery.ConditionField[] fields = {
            ConditionQuery.instanceField("a.houseCode",ConditionAdapter.MatchType.FULL)
            ,ConditionQuery.instanceField("a.accountNumber",ConditionAdapter.MatchType.FULL)
            ,ConditionQuery.instanceField("h.houseAddress",ConditionAdapter.MatchType.CONTAIN)
            ,ConditionQuery.instanceField("h.buildName",ConditionAdapter.MatchType.CONTAIN)
            ,ConditionQuery.instanceField("h.sectionName",ConditionAdapter.MatchType.CONTAIN)
            ,ConditionQuery.instanceField("o.credentialsNumber",ConditionAdapter.MatchType.FULL)
            ,ConditionQuery.instanceField("o.name",ConditionAdapter.MatchType.CONTAIN)
            ,ConditionQuery.instanceField("o.tel",ConditionAdapter.MatchType.END_WITCH)
    };

    private ConditionQuery buildConditionQuery(List<ConditionAdapter> conditions, List<HouseEntity.UseType> useTypes){
        return ConditionQuery.instance(conditions,useTypes.isEmpty()?null:" h.useType in (:useTypes) ",fields);
    }

    private <T> TypedQuery<T> parameterization(ConditionQuery conditionQuery, TypedQuery<T> query, List<HouseEntity.UseType> useTypes){
        TypedQuery<T> result = conditionQuery.parameterization(query);
        if (!useTypes.isEmpty()){
            result = result.setParameter("useTypes", useTypes);
        }
        return result;
    }


    public List<HouseAccountEntity> queryByKey(List<ConditionAdapter> conditions, List<HouseEntity.UseType> useTypes, int offset, int count){
        ConditionQuery conditionQuery = buildConditionQuery(conditions,useTypes);
        TypedQuery<HouseAccountEntity> query = typedQuery("SELECT DISTINCT(a) FROM HouseAccountEntity a left join fetch a.house h left join h.ownerPersons o " +
                conditionQuery.where() + " order by h.dataTime desc , a.createTime desc, a.accountNumber").setMaxResults(count).setFirstResult(offset);
        return parameterization(conditionQuery,query,useTypes).getResultList();
    }

    public Long queryCountByKey(List<ConditionAdapter> conditions, List<HouseEntity.UseType> useTypes){
        ConditionQuery conditionQuery = buildConditionQuery(conditions,useTypes);
        TypedQuery<Long> query = entityManager().createQuery("SELECT COUNT(DISTINCT a.accountNumber) FROM HouseAccountEntity a left join a.house h left join h.ownerPersons o" +
                conditionQuery.where(), Long.class);
        return parameterization(conditionQuery,query,useTypes).getSingleResult();
    }

    public List<UseTypeCount> queryByKeyGroupUseType(List<ConditionAdapter> conditions, List<HouseEntity.UseType> useTypes){
        ConditionQuery conditionQuery = buildConditionQuery(conditions,useTypes);
        TypedQuery<UseTypeCount> query = entityManager().createQuery("SELECT new cc.coopersoft.house.repair.data.UseTypeCount(h.useType, DISTINCT(a.accountNumber)) FROM HouseAccountEntity a left join a.house h left join h.ownerPersons o" +
                conditionQuery.where() + " group by h.useType",UseTypeCount.class);

        return parameterization(conditionQuery,query,useTypes).getResultList();
    }


    public abstract HouseAccountEntity findOptionalByHouseCode(String code);

    @Query("SELECT max(ad.operationTime) FROM AccountDetailsEntity ad where ad.houseAccount.houseCode in (?1) and ad.status <> 'DELETED'" )
    public abstract Date queryLastChangeDate(List<String> houseCodes);

}
