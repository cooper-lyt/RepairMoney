package cc.coopersoft.framework.services;

import cc.coopersoft.framework.data.model.SystemParamEntity;
import cc.coopersoft.framework.data.repository.SystemParamRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class SystemParamService implements java.io.Serializable{

    @Inject
    private SystemParamRepository systemParamRepository;

    private Map<String,String> params;

    private void initParams(){
        if (params == null) {
            List<SystemParamEntity> paramEntities = systemParamRepository.findAll();
            params = new HashMap<String, String>(paramEntities.size());
            for (SystemParamEntity entity : paramEntities) {
                params.put(entity.getKey(), entity.getValue());
            }
        }
    }

    public String getStringParam(String key){
        initParams();
        return params.get(key);
    }

    public Integer getIntParam(String key){
        initParams();
        String val = params.get(key);
        if (val == null){
            return null;
        }else{
            return Integer.valueOf(val);
        }
    }

    public BigDecimal getNumberParam(String key){
        initParams();
        String val = params.get(key);
        if (val == null){
            return null;
        }
        return new BigDecimal(val);
    }

    public <T extends Enum<T>> T getEnumParam(Class<T> enumType, String key){
        initParams();
        String val = params.get(key);
        if (val == null){
            return null;
        }
        return Enum.valueOf(enumType,val);
    }
}
