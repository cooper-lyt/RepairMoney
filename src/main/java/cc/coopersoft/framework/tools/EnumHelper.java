package cc.coopersoft.framework.tools;


import javax.inject.Inject;
import javax.inject.Named;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by cooper on 6/16/16.
 */
@Named
public class EnumHelper implements java.io.Serializable{


    @Inject @DefaultMessageBundle
    private ResourceBundle bundle;

    public String getLabel(Enum value){
        if (value == null){
            return "";
        }
        try {
            return bundle.getString(value.getClass().getName() + "." + value.name());
        }catch (MissingResourceException e){
            return value.getClass().getName() + "." + value.name();
        }
    }
}
