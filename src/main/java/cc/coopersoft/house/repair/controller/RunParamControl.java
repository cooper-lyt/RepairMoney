package cc.coopersoft.house.repair.controller;

import cc.coopersoft.framework.services.SystemParamService;
import cc.coopersoft.house.repair.services.RemoteDataService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class RunParamControl {

    @Inject
    private SystemParamService systemParamService;

    public String getDistrictAddress(){
        return systemParamService.getStringParam(RemoteDataService.API_SERVER_ADDRESS_PARAM_KEY) + "/api/public/list-district";
    }

    public String getSectionAddress(){
        return systemParamService.getStringParam(RemoteDataService.API_SERVER_ADDRESS_PARAM_KEY) + "/api/public/list-section";
    }

}
