package cc.coopersoft.house.repair.data.repository;

import cc.coopersoft.framework.ConditionAdapter;
import cc.coopersoft.framework.ConditionQuery;
import cc.coopersoft.framework.tools.DataHelper;
import cc.coopersoft.house.repair.data.UseTypeCount;
import cc.coopersoft.house.repair.data.model.HouseAccountEntity;
import cc.coopersoft.house.repair.data.model.HouseEntity;
import org.apache.deltaspike.data.api.*;

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
            ,ConditionQuery.instanceField("concat(h.mapNumber, '-', h.blockNumber, '-', h.buildNumber , '-', h.houseOrder) ",ConditionAdapter.MatchType.END_WITCH)
    };

    private ConditionQuery buildConditionQuery(List<ConditionAdapter> conditions, List<HouseEntity.UseType> useTypes){
        return ConditionQuery.instance(conditions,useTypes.isEmpty() ? null : " h.useType in (:useTypes) ",fields);
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
                conditionQuery.where() + " order by a.createTime desc, h.dataTime desc ,  a.accountNumber").setMaxResults(count).setFirstResult(offset);
        return parameterization(conditionQuery,query,useTypes).getResultList();
    }

    public Long queryCountByKey(List<ConditionAdapter> conditions, List<HouseEntity.UseType> useTypes){
        ConditionQuery conditionQuery = buildConditionQuery(conditions,useTypes);
        TypedQuery<Long> query = entityManager().createQuery("SELECT COUNT(DISTINCT a.accountNumber) FROM HouseAccountEntity a left join a.house h left join h.ownerPersons o" +
                conditionQuery.where(), Long.class);
        return parameterization(conditionQuery,query,useTypes).getSingleResult();
    }

    public List<UseTypeCount> queryByKeyGroupUseType(List<ConditionAdapter> conditions){
        ConditionQuery conditionQuery = ConditionQuery.instance(conditions,fields);
        String where = conditionQuery.where();
        if (DataHelper.empty(where)){
            where = " WHERE ";
        }else{
            where += " AND ";
        }
        where += " h.useType is not null ";
        TypedQuery<UseTypeCount> query = entityManager().createQuery("SELECT new cc.coopersoft.house.repair.data.UseTypeCount(h.useType, COUNT(DISTINCT a.accountNumber)) FROM HouseAccountEntity a left join a.house h left join h.ownerPersons o" +
                where + "  group by h.useType",UseTypeCount.class);

        return conditionQuery.parameterization(query).getResultList();
    }


    public abstract HouseAccountEntity findOptionalByHouseCode(String code);

    @Query("SELECT a FROM HouseAccountEntity a left join fetch a.house h  where h.mapNumber = ?1 and h.blockNumber = ?2 and h.buildNumber =?3 and a.status <> 'DESTROY'" )
    public abstract List<HouseAccountEntity> queryHouseAccountList(String mapNumber,String blockNumber,String buildNumber);

    @Query("SELECT max(ad.operationTime) FROM AccountDetailsEntity ad where ad.houseAccount.houseCode in (?1) and ad.status <> 'DELETED'" )
    public abstract Date queryLastChangeDate(List<String> houseCodes);

    @Query("SELECT max(ad.operationTime) FROM AccountDetailsEntity ad where ad.houseAccount.houseCode = ?1 and ad.status <> 'DELETED'" )
    public abstract Date queryLastChangeDate(String houseCode);

    @Query("SELECT DISTINCT(a) FROM HouseAccountEntity a left join fetch a.house h left join h.ownerPersons o  where o.credentialsType = 'MASTER_ID' and (o.credentialsNumber = ?1 or o.credentialsNumber = ?2) and (h.useType in (?3) or (true = ?4)) order by a.createTime desc, h.dataTime desc ,  a.accountNumber")
    public abstract List<HouseAccountEntity> queryByIdCard(String number, String oldNumber, List<HouseEntity.UseType> useTypes,boolean allUseType, @FirstResult int offset, @MaxResults int count);

    @Query("SELECT COUNT(DISTINCT a.accountNumber) FROM HouseAccountEntity a left join a.house h left join h.ownerPersons o  where o.credentialsType = 'MASTER_ID' and (o.credentialsNumber = ?1 or o.credentialsNumber = ?2) and (h.useType in (?3) or (true = ?4)) ")
    public abstract Long queryCountByIdCard(String number, String oldNumber, List<HouseEntity.UseType> useTypes,boolean allUseType);

    @Query("SELECT new cc.coopersoft.house.repair.data.UseTypeCount(h.useType, COUNT(DISTINCT a.accountNumber)) FROM  HouseAccountEntity a left join a.house h left join h.ownerPersons o  where o.credentialsType = 'MASTER_ID' and (o.credentialsNumber = ?1 or o.credentialsNumber = ?2) and h.useType is not null group by h.useType")
    public abstract List<UseTypeCount> queryByIdCardGroupUseType(String number, String oldNumber);


    @Query("SELECT DISTINCT(a) FROM HouseAccountEntity a left join fetch a.house h left join h.ownerPersons o where o.name like ?1 and  (h.useType in (?2) or (true = ?3)) order by a.createTime desc, h.dataTime desc ,  a.accountNumber")
    public abstract List<HouseAccountEntity> queryByOwnerName(String name, List<HouseEntity.UseType> useTypes,boolean allUseType, @FirstResult int offset, @MaxResults int count);

    @Query("SELECT COUNT(DISTINCT a.accountNumber) FROM HouseAccountEntity a left join a.house h left join h.ownerPersons o where o.name like ?1 and  (h.useType in (?2) or (true = ?3)) ")
    public abstract Long queryCountByOwnerName(String name, List<HouseEntity.UseType> useTypes,boolean allUseType);

    @Query("SELECT new cc.coopersoft.house.repair.data.UseTypeCount(h.useType, COUNT(DISTINCT a.accountNumber)) FROM HouseAccountEntity a left join a.house h left join h.ownerPersons o where o.name like ?1 and h.useType is not null group by h.useType")
    public abstract List<UseTypeCount> queryByOwnerNameGroupUseType(String name);

    @Query("SELECT a FROM HouseAccountEntity a left join fetch a.house h where (h.mapNumber = ?1 or false = ?2) and (h.blockNumber = ?3 or false = ?4) and (h.buildNumber = ?5 or false = ?6) and (h.houseOrder = ?7 or false = ?8) order by a.createTime desc, h.dataTime desc ,  a.accountNumber")
    public abstract List<HouseAccountEntity> queryByMapId(String mapNumber, boolean hasMapNumber, String blockNumber, boolean hasBlockNumber, String buildNumber, boolean hasBuildNumber, String houseOrder, boolean hasHouseOrder);

    @Query("SELECT a FROM HouseAccountEntity a left join fetch a.house h where (h.mapNumber = ?1 or false = ?2) and (h.blockNumber = ?3 or false = ?4) and (h.buildNumber = ?5 or false = ?6) and (h.houseOrder = ?7 or false = ?8) and (h.useType in (?9) or (true = ?10)) order by a.createTime desc, h.dataTime desc ,  a.accountNumber")
    public abstract List<HouseAccountEntity> queryByMapId(String mapNumber, boolean hasMapNumber, String blockNumber, boolean hasBlockNumber, String buildNumber, boolean hasBuildNumber, String houseOrder, boolean hasHouseOrder, List<HouseEntity.UseType> useTypes,boolean allUseType , @FirstResult int offset , @MaxResults int count);

    @Query("SELECT COUNT(a) FROM HouseAccountEntity a left join a.house h where (h.mapNumber = ?1 or false = ?2) and (h.blockNumber = ?3 or false = ?4) and (h.buildNumber = ?5 or false = ?6) and (h.houseOrder = ?7 or false = ?8) and (h.useType in (?9) or (true = ?10)) ")
    public abstract Long queryCountByMapId(String mapNumber, boolean hasMapNumber, String blockNumber, boolean hasBlockNumber, String buildNumber, boolean hasBuildNumber, String houseOrder, boolean hasHouseOrder, List<HouseEntity.UseType> useTypes,boolean allUseType);

    @Query("SELECT new cc.coopersoft.house.repair.data.UseTypeCount(h.useType, COUNT(a.accountNumber)) FROM HouseAccountEntity a left join a.house h where (h.mapNumber = ?1 or false = ?2) and (h.blockNumber = ?3 or false = ?4) and (h.buildNumber = ?5 or false = ?6) and (h.houseOrder = ?7 or false = ?8) and (h.useType is not null) group by h.useType")
    public abstract List<UseTypeCount> queryByMapIdGroupUseType(String mapNumber, boolean hasMapNumber, String blockNumber, boolean hasBlockNumber, String buildNumber, boolean hasBuildNumber, String houseOrder, boolean hasHouseOrder);


    @Query("SELECT a FROM HouseAccountEntity a left join fetch a.house h where a.houseCode = ?1 and (h.useType in (?2) or (true = ?3)) order by a.createTime desc, h.dataTime desc ,  a.accountNumber")
    public abstract List<HouseAccountEntity> queryByHouseCode(String code, List<HouseEntity.UseType> useTypes,boolean allUseType, @FirstResult int offset, @MaxResults int count);

    @Query("SELECT COUNT(a) FROM HouseAccountEntity a left join a.house h where a.houseCode = ?1 and (h.useType in (?2) or (true = ?3))")
    public abstract Long queryCountByHouseCode(String code, List<HouseEntity.UseType> useTypes,boolean allUseType);

    @Query("SELECT new cc.coopersoft.house.repair.data.UseTypeCount(h.useType, COUNT(a.accountNumber)) FROM HouseAccountEntity a left join a.house h where a.houseCode = ?1 and  (h.useType is not null) group by h.useType")
    public abstract List<UseTypeCount> queryByHouseCodeGroupUseType(String code);


    @Query("SELECT a FROM HouseAccountEntity a left join fetch a.house h where h.houseAddress like ?1 and (h.useType in (?2) or (true = ?3)) order by a.createTime desc, h.dataTime desc ,  a.accountNumber")
    public abstract List<HouseAccountEntity> queryByAddress(String address, List<HouseEntity.UseType> useTypes,boolean allUseType, @FirstResult int offset, @MaxResults int count);

    @Query("SELECT COUNT(a) FROM HouseAccountEntity a left join a.house h where h.houseAddress like ?1 and (h.useType in (?2) or (true = ?3))")
    public abstract Long queryCountByAddress(String address, List<HouseEntity.UseType> useTypes,boolean allUseType);

    @Query("SELECT new cc.coopersoft.house.repair.data.UseTypeCount(h.useType, COUNT(a.accountNumber)) FROM HouseAccountEntity a left join a.house h where h.houseAddress like ?1  and  not (h.useType is null) group by h.useType")
    public abstract List<UseTypeCount> queryByAddressGroupUseType(String address);


    @Query("SELECT DISTINCT(a) FROM HouseAccountEntity a left join fetch a.house h left join h.ownerPersons o  where o.tel like ?1 and (h.useType in (?2) or (true = ?3) ) order by a.createTime desc, h.dataTime desc ,  a.accountNumber")
    public abstract List<HouseAccountEntity> queryByTel(String tel, List<HouseEntity.UseType> useTypes,boolean allUseType, @FirstResult int offset, @MaxResults int count);

    @Query("SELECT COUNT(DISTINCT a.accountNumber) FROM HouseAccountEntity a left join a.house h left join h.ownerPersons o  where o.tel like ?1 and (h.useType in (?2) or (true = ?3))")
    public abstract Long queryCountByTel(String tel, List<HouseEntity.UseType> useTypes, boolean allUseType);

    @Query("SELECT new cc.coopersoft.house.repair.data.UseTypeCount(h.useType, COUNT(DISTINCT a.accountNumber)) FROM HouseAccountEntity a left join a.house h left join h.ownerPersons o  where o.tel like ?1 and  (h.useType is not null)  group by h.useType")
    public abstract List<UseTypeCount> queryByTelGroupUseType(String tel);


}
