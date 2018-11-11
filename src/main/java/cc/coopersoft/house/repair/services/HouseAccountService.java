package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.ConditionAdapter;
import cc.coopersoft.framework.EntityDataPage;
import cc.coopersoft.framework.services.SimpleEntityService;
import cc.coopersoft.framework.tools.DataHelper;
import cc.coopersoft.framework.tools.IdCardUtils;
import cc.coopersoft.house.repair.data.UseTypeCount;
import cc.coopersoft.house.repair.data.model.HouseAccountEntity;
import cc.coopersoft.house.repair.data.model.HouseEntity;
import cc.coopersoft.house.repair.data.HouseMapId;
import cc.coopersoft.house.repair.data.repository.HouseAccountRepository;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.inject.Inject;
import java.util.*;

public class HouseAccountService extends SimpleEntityService<HouseAccountEntity,String>  {



    @Inject
    private HouseAccountRepository houseAccountRepository;


    @Override
    public EntityRepository<HouseAccountEntity, String> getEntityRepository() {
        return houseAccountRepository;
    }

    @Override
    public HouseAccountEntity createNew() {
        return new HouseAccountEntity();
    }

    public Date lastChangeTime(List<String> houseCodes){
        return houseAccountRepository.queryLastChangeDate(houseCodes);
    }

    public Date lastChangeTime(String houseCode){
        return houseAccountRepository.queryLastChangeDate(houseCode);
    }


    public List<HouseAccountEntity> search(HouseMapId houseMapId){
        return houseAccountRepository.queryByMapId(
                houseMapId.getMap(),!houseMapId.isEmptyMap(),
                houseMapId.getBlock(),!houseMapId.isEmptyBlock(),
                houseMapId.getBuild(),!houseMapId.isEmptyBuild(),
                houseMapId.getHouse(),!houseMapId.isEmptyHouse());
    }

    public boolean validTime(Date date, List<String> houseCodes){
        Date lastDate = lastChangeTime(houseCodes);
        return ((lastDate == null) || date.after(lastDate));
    }

    public SearchResult search(Type type, String condition ,HouseMapId mapId, List<HouseEntity.UseType> useTypes, int offset, int count){
        SearchResult result = new SearchResult();

        if (type == null || (!Type.MAP_ID.equals(type) && DataHelper.empty(condition))){
            List<ConditionAdapter> conditions = ConditionAdapter.instance(condition,"\\s+");
            result.entityDataPage =  new EntityDataPage<>(
                    houseAccountRepository.queryByKey(conditions,useTypes,offset,count),
                    offset,
                    houseAccountRepository.queryCountByKey(conditions,useTypes)
                    ,count
            );
            result.useTypeCounts = houseAccountRepository.queryByKeyGroupUseType(ConditionAdapter.instance(condition,"\\s+"));
        }else{
            List<HouseEntity.UseType> useTypeList = useTypes;
            if (useTypeList == null || useTypeList.isEmpty()){
                useTypeList = new ArrayList<>(EnumSet.allOf(HouseEntity.UseType.class));
            }
            switch (type){

                case IDENTITY:
                    String number1 = condition.trim();
                    String number2 = condition.trim();
                    if (number1.length() == 15){
                        number2 = IdCardUtils.conver15CardTo18(number1);
                    }else if (number1.length() == 18){
                        number2 = IdCardUtils.conver18CardTo15(number1);
                    }
                    result.entityDataPage = new EntityDataPage<>(
                            houseAccountRepository.queryByIdCard(number1,number2,useTypeList,offset,count),
                            offset,
                            houseAccountRepository.queryCountByIdCard(number1,number2,useTypeList),
                            count
                    );
                    result.useTypeCounts = houseAccountRepository.queryByIdCardGroupUseType(number1,number2);
                    break;
                case PERSON_NAME:
                    String personName = ConditionAdapter.instance(condition).getContains();
                    result.entityDataPage = new EntityDataPage<>(
                            houseAccountRepository.queryByOwnerName(personName,useTypeList,offset,count),
                            offset,
                            houseAccountRepository.queryCountByOwnerName(personName,useTypeList),
                            count
                    );
                    result.useTypeCounts = houseAccountRepository.queryByOwnerNameGroupUseType(personName);
                    break;
                case MAP_ID:

                    result.entityDataPage = new EntityDataPage<>(
                            houseAccountRepository.queryByMapId(
                                    mapId.getMap(),!mapId.isEmptyMap(),
                                    mapId.getBlock(),!mapId.isEmptyBlock(),
                                    mapId.getBuild(),!mapId.isEmptyBuild(),
                                    mapId.getHouse(),!mapId.isEmptyHouse()
                                    ,useTypeList,offset,count),
                            offset,
                            houseAccountRepository.queryCountByMapId(mapId.getMap(),!mapId.isEmptyMap(),
                                    mapId.getBlock(),!mapId.isEmptyBlock(),
                                    mapId.getBuild(),!mapId.isEmptyBuild(),
                                    mapId.getHouse(),!mapId.isEmptyHouse(),useTypeList),
                            count
                    );
                    result.useTypeCounts = houseAccountRepository.queryByMapIdGroupUseType(mapId.getMap(),!mapId.isEmptyMap(),
                            mapId.getBlock(),!mapId.isEmptyBlock(),
                            mapId.getBuild(),!mapId.isEmptyBuild(),
                            mapId.getHouse(),!mapId.isEmptyHouse());
                    break;
                case HOUSE_CODE:
                    result.entityDataPage = new EntityDataPage<>(
                            houseAccountRepository.queryByHouseCode(condition.trim(),useTypeList,offset,count),
                            offset,
                            houseAccountRepository.queryCountByHouseCode(condition.trim(),useTypeList),
                            count
                    );
                    result.useTypeCounts = houseAccountRepository.queryByHouseCodeGroupUseType(condition.trim());
                    break;
                case HOUSE_ADDRESS:
                    String address = ConditionAdapter.instance(condition).getContains();
                    result.entityDataPage = new EntityDataPage<>(
                            houseAccountRepository.queryByAddress(address,useTypeList,offset,count),
                            offset,
                            houseAccountRepository.queryCountByAddress(address,useTypeList),
                            count
                    );
                    result.useTypeCounts = houseAccountRepository.queryByAddressGroupUseType(address);
                    break;
                case TEL:
                    String tel = ConditionAdapter.instance(condition).getEndWith();
                    result.entityDataPage = new EntityDataPage<>(
                            houseAccountRepository.queryByTel(tel,useTypeList,offset,count),
                            offset,
                            houseAccountRepository.queryCountByTel(tel,useTypeList),
                            count
                    );
                    result.useTypeCounts = houseAccountRepository.queryByTelGroupUseType(tel);
                    break;
            }
        }

        Collections.sort(result.useTypeCounts);
        return result;
    }


    public enum Type{
        IDENTITY,
        PERSON_NAME,
        MAP_ID,
        HOUSE_CODE,
        HOUSE_ADDRESS,
        TEL
    }

    public static class SearchResult{
        private EntityDataPage<HouseAccountEntity> entityDataPage;
        private List<UseTypeCount> useTypeCounts;

        SearchResult() {
        }

        public EntityDataPage<HouseAccountEntity> getEntityDataPage() {
            return entityDataPage;
        }

        public List<UseTypeCount> getUseTypeCounts() {
            return useTypeCounts;
        }
    }
}
