package cc.coopersoft.framework.faces.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.Date;

@FacesConverter("coopersoft.dateParamConverter")
public class DateParamConverter implements Converter {


    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !"".equals(value.trim())){
            try {
                return new Date(Long.parseLong(value));
            }catch (NumberFormatException e){
                return null;
            }
        }
        return null;
    }


    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Date){
            return String.valueOf(((Date) value).getTime());
        }
        return "";
    }
}
