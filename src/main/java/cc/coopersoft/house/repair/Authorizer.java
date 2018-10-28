package cc.coopersoft.house.repair;

import cc.coopersoft.framework.BusinessManagerRole;
import cc.coopersoft.framework.BusinessRunManagerRole;
import cc.coopersoft.framework.SystemManagerRole;
import cc.coopersoft.house.repair.annotations.CollectRole;
import org.apache.deltaspike.security.api.authorization.Secures;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.interceptor.InvocationContext;

@ApplicationScoped
public class Authorizer {

    @Inject
    private FacesContext facesContext;

    @Secures
    @CollectRole
    public boolean doCollectCheck(InvocationContext invocationContext, BeanManager manager) throws Exception
    {
        return facesContext.getExternalContext().isUserInRole("Collect");
    }


    @Secures
    @SystemManagerRole
    public boolean doSystemManagerRoldCheck(InvocationContext invocationContext, BeanManager manager) throws Exception{
        return facesContext.getExternalContext().isUserInRole("SystemManager");
    }

    @Secures
    @BusinessRunManagerRole
    public boolean doBusinessRunManagerRole(InvocationContext invocationContext, BeanManager manager) throws Exception{
        return facesContext.getExternalContext().isUserInRole("BusinessRunManager");
    }

    @Secures
    @BusinessManagerRole
    public boolean doBusinessManagerRole(InvocationContext invocationContext, BeanManager manager) throws Exception{
        return facesContext.getExternalContext().isUserInRole("BusinessManager");
    }
}
