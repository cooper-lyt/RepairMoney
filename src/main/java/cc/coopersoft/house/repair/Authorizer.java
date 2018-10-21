package cc.coopersoft.house.repair;

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
}
