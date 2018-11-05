package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.ConditionAdapter;
import cc.coopersoft.framework.EntityDataPage;
import cc.coopersoft.framework.services.SimpleEntityService;
import cc.coopersoft.house.repair.data.UseTypeCount;
import cc.coopersoft.house.repair.data.model.HouseAccountEntity;
import cc.coopersoft.house.repair.data.model.HouseEntity;
import cc.coopersoft.house.repair.data.repository.HouseAccountRepository;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

    public boolean validTime(Date date, List<String> houseCodes){
        Date lastDate = lastChangeTime(houseCodes);
        return ((lastDate == null) || date.after(lastDate));
    }

    public EntityDataPage<HouseAccountEntity> search(String condition, List<HouseEntity.UseType> useTypes, int offset, int count){
        List<ConditionAdapter> conditions = ConditionAdapter.instance(condition," ");
        return new EntityDataPage<>(
                new ArrayList<>(houseAccountRepository.queryByKey(conditions,useTypes,offset,count)),
                offset,
                houseAccountRepository.queryCountByKey(conditions,useTypes)
                ,count
        );
    }

    public List<UseTypeCount> searchUseTypeCount(String condition, List<HouseEntity.UseType> useTypes){

        List<UseTypeCount> result = houseAccountRepository.queryByKeyGroupUseType(ConditionAdapter.instance(condition," "),useTypes);
        Collections.sort(result);
        return result;

    }

}
