package cc.coopersoft.house.repair.controller;

import cc.coopersoft.framework.EntityController;
import cc.coopersoft.framework.services.EntityService;
import cc.coopersoft.house.repair.data.model.HouseAccountEntity;
import cc.coopersoft.house.repair.services.HouseAccountService;
import org.omnifaces.cdi.Param;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class HouseAccountController extends EntityController<HouseAccountEntity,String> {

    private static final String ACCOUNT_ID_PARAM_NAME = "accountId";

    @Override
    @Inject @Param(name = ACCOUNT_ID_PARAM_NAME)
    public String getId(){
        return super.getId();
    }

    @Inject
    private FacesContext facesContext;

    @Inject
    private HouseAccountService houseAccountService;

    @Override
    protected EntityService<HouseAccountEntity, String> getEntityService() {
        return houseAccountService;
    }


    @PostConstruct
    public void init(){
        setId(facesContext.getExternalContext().getRequestParameterMap().get(ACCOUNT_ID_PARAM_NAME));
    }

}
