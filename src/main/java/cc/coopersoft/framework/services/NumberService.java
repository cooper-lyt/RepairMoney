package cc.coopersoft.framework.services;

import cc.coopersoft.framework.data.model.NumberPoolEntity;
import cc.coopersoft.framework.data.repository.NumberPoolRepository;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.*;

@ApplicationScoped
@Singleton
public class NumberService implements java.io.Serializable {

    private static final int MONTH_NUMBER_BOOT_PART_MIN_LENGTH = 2;

    private static final int YEAR_NUMBER_BOOT_PART_MIN_LENGTH = 3;

    private static class Number{
        private Date numberDate;
        private int number;

        public Number() {
            this.numberDate = new Date();
            this.number = 0;
        }

        public Date getNumberDate() {
            return numberDate;
        }

        public void setNumberDate(Date numberDate) {
            this.numberDate = numberDate;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }

    @Inject
    private NumberPoolRepository numberPoolRepository;

    private Map<String,NumberPoolEntity> numberDefines;

    private Map<String,Number> numbers;

    private boolean isSameDay(Date date1, Date date2){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
                if (cal1.get(Calendar.DAY_OF_MONTH) == cal2
                        .get(Calendar.DAY_OF_MONTH)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isSameMonth(Date date1, Date date2){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSameYear(Date date1, Date date2){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }

    @PostConstruct
    @Transactional
    public void create(){
        List<NumberPoolEntity> numberPoolEntities = numberPoolRepository.findAll();
        numberDefines = new HashMap<>(numberPoolEntities.size());
        numbers = new HashMap<>(numberPoolEntities.size());
        Date nowDate = new Date();
        for (NumberPoolEntity num: numberPoolEntities){

            long number = num.getNumber() + 1;
            if ( (NumberPoolEntity.Type.DATE.equals(num.getType()) && !isSameDay(num.getNumberDate(),nowDate)) ||
                    (NumberPoolEntity.Type.MONTH.equals(num.getType()) && !isSameMonth(num.getNumberDate(), nowDate)) ||
                    (NumberPoolEntity.Type.YEAR.equals(num.getType()) && !isSameYear(num.getNumberDate(),nowDate)) ){
                num.setNumberDate(new Date());
                number = 0;
            }

            num.setNumber(number);
            numberDefines.put(num.getId(),num);
            numbers.put(num.getId(),new Number());
        }
        numberPoolRepository.flush();
    }

    public String getNumber(String id, String prefix){
        return prefix + getNumber(id);
    }


    public String getNumber(String id){
        NumberPoolEntity numberDefine = numberDefines.get(id);
        Number number = numbers.get(id);
        String bootNumPart = String.valueOf(numberDefine.getNumber());
        String datePart;

        Date nowDate = new Date();
        switch (numberDefine.getType()){
            case DATE:
                if (!isSameDay(nowDate,numberDefine.getNumberDate())){
                    bootNumPart = "0";
                }
                if (!isSameDay(nowDate,number.getNumberDate())){
                    number.setNumberDate(nowDate);
                    number.setNumber(1);
                }else{
                    number.setNumber(number.getNumber() + 1);
                }
                datePart = (new SimpleDateFormat("yyyyMMdd")).format(nowDate);
                break;
            case MONTH:
                if (!isSameMonth(nowDate,numberDefine.getNumberDate())){
                    bootNumPart = "0";
                }
                if (!isSameMonth(nowDate,number.getNumberDate())){
                    number.setNumberDate(nowDate);
                    number.setNumber(1);
                }else{
                    number.setNumber(number.getNumber() + 1);
                }
                if (bootNumPart.length() < MONTH_NUMBER_BOOT_PART_MIN_LENGTH){
                    bootNumPart = "0" + bootNumPart;
                }
                datePart = (new SimpleDateFormat("yyyyMM")).format(nowDate);
                break;
            case YEAR:
                if (!isSameYear(nowDate,numberDefine.getNumberDate())){
                    bootNumPart = "0";
                }
                if (!isSameYear(nowDate,number.getNumberDate())){
                    number.setNumberDate(nowDate);
                    number.setNumber(1);
                }else{
                    number.setNumber(number.getNumber() + 1);
                }
                while (bootNumPart.length() < YEAR_NUMBER_BOOT_PART_MIN_LENGTH){
                    bootNumPart = "0" + bootNumPart;
                }
                datePart = (new SimpleDateFormat("yyyy")).format(nowDate);
                break;

                default:
                    throw new IllegalArgumentException("unknow type:" + numberDefine.getType());
        }



        StringBuffer result =  new StringBuffer(String.valueOf(number.getNumber()));
        while (result.length()  < numberDefine.getLength()) {
            result.insert(0, 0);
        }

        result.insert(0,bootNumPart);
        result.insert(0,datePart);
        if (numberDefine.getPrefix() != null){
            result.insert(0,numberDefine.getPrefix().trim());
        }
        return result.toString();

    }
}
