package cc.coopersoft.framework;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by cooper on 7/22/16.
 */

@Named
@SessionScoped
public class I18n implements java.io.Serializable{

    private Locale locale;

    @PostConstruct
    public void initParam(){
        locale = Locale.CHINA;
    }


    public Locale getLocale() {
        return locale;
    }

    public TimeZone getTimeZone(){
        return Calendar.getInstance(getLocale()).getTimeZone();
    }

    public Date getDayBeginTime(Date value){
        GregorianCalendar gc = new GregorianCalendar(getLocale());
        gc.setTime(value);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        gc.set(Calendar.MILLISECOND, 0);


        return gc.getTime();
    }

    public Date getDayEndTime(Date value){
        return new Date(getDayBeginTime(value).getTime() + 24 * 60 * 60 * 1000 - 1000);
    }

    public String currencyDisplay(BigDecimal value){
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(getLocale());
        return currencyFormat.format(value);
    }

    public String datetimeDisplay(Date value){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return dateFormat.format(value);
    }

    public String dateDisplay(Date value){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return dateFormat.format(value);
    }
}
