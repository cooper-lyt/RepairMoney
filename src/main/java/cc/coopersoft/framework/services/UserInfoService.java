package cc.coopersoft.framework.services;

import cc.coopersoft.framework.data.LoginUser;
import org.apache.deltaspike.core.api.common.DeltaSpike;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.security.Principal;
import java.util.Hashtable;

public class UserInfoService implements java.io.Serializable{

    @Inject
    @DeltaSpike
    private Principal principal;

    @Inject
    private SystemParamService systemParamService;

    @Named
    @Produces
    @SessionScoped
    public LoginUser getLoginUser(){
        if (principal == null){
            return null;
        }
        LoginUser result = new LoginUser(principal.getName());
        Hashtable<String,String> ht= new Hashtable<String,String>();
        ht.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        ht.put(Context.PROVIDER_URL,systemParamService.getStringParam("config.ldap.address"));
        ht.put(Context.SECURITY_AUTHENTICATION, "simple");
        ht.put(Context.SECURITY_PRINCIPAL, systemParamService.getStringParam("config.ldap.admin.id"));
        ht.put(Context.SECURITY_CREDENTIALS, systemParamService.getStringParam("config.ldap.admin.password"));
        try {
            DirContext dc = new InitialDirContext(ht);

            SearchControls ctrl = new SearchControls();
            ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);

            NamingEnumeration enu = dc.search(systemParamService.getStringParam("config.ldap.baseDN"),
                    "uid=" + principal.getName(), ctrl);
            if (enu.hasMore()){
                SearchResult sr = (SearchResult) enu.next();

                Attributes ab = sr.getAttributes();
                result.setName(getAttributeValue(ab,"sn"));
                result.setMail(getAttributeValue(ab,"mail"));
                result.setMobile(getAttributeValue(ab,"mobile"));
                result.setPhoto(getAttributeValue(ab,"photo"));
            }
            dc.close();
            return result;
        } catch (NamingException e) {
            throw new IllegalArgumentException("conn ldap fail;",e);
        }
    }

    private String getAttributeValue(Attributes ab, String name) throws NamingException {
        Attribute attribute = ab.get(name);
        if ((attribute != null) && (attribute.size() > 0)){
            return attribute.get(0).toString();
        }
        return null;
    }
}
