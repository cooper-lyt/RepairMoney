package cc.coopersoft.framework.tools;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Created by cooper on 6/5/16.
 */
public class Resources {



    @Produces
    public Logger produceLog(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

    @Produces
    @DefaultMessageBundle
    public ResourceBundle getBundle() {

        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().getResourceBundle(context, "messages");
    }

    @Named
    @Produces
    @RequestScoped
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    private void fillStackTrace(Throwable t, PrintWriter w) {
        if (t == null) return;
        t.printStackTrace(w);
        if (t instanceof ServletException) {
            Throwable cause = ((ServletException) t).getRootCause();
            if (cause != null) {
                w.println("Root cause:");
                fillStackTrace(cause, w);
            }
        } else if (t instanceof SQLException) {
            Throwable cause = ((SQLException) t).getNextException();
            if (cause != null) {
                w.println("Next exception:");
                fillStackTrace(cause, w);
            }
        } else {
            Throwable cause = t.getCause();
            if (cause != null) {
                w.println("Cause:");
                fillStackTrace(cause, w);
            }
        }
    }

    @Named
    @Produces
    public String getErrorStackTrace(){
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> request
                = context.getExternalContext().getRequestMap();
        Throwable ex = (Throwable) request.get("javax.servlet.error.exception");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        fillStackTrace(ex, pw);
        return sw.toString();
    }

}
