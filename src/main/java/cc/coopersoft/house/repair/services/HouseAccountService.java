package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.services.SimpleEntityService;
import cc.coopersoft.house.repair.data.model.HouseAccountEntity;
import cc.coopersoft.house.repair.data.repository.HouseAccountRepository;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.inject.Inject;
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
}
