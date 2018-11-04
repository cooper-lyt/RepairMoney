package cc.coopersoft.framework;

import cc.coopersoft.framework.tools.DataHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cooper on 6/22/16.
 */
public class ConditionAdapter implements java.io.Serializable{

    public enum MatchType{
        FULL("="),
        END_WITCH("like"),
        START_WITCH("like"),
        CONTAIN("like");

        public String symbol;

        MatchType(String symbol) {
            this.symbol = symbol;
        }
    }

    public static ConditionAdapter instance(String condition){
        return new ConditionAdapter(condition);
    }

    public static List<ConditionAdapter> instance(String condition, String regex){
        if (DataHelper.empty(condition)){
            return new ArrayList<>(0);
        }
        List<ConditionAdapter> result = new ArrayList<>();
        for(String s: condition.split(regex)){
            result.add(new ConditionAdapter(s));
        }
        return result;
    }


    private String condition;

    private boolean empty;
    private String endWith;
    private String startWith;
    private String contains;

    private ConditionAdapter(String condition) {
        empty = condition == null || "".equals(condition.trim());
        if (empty){
            endWith = "%";
            startWith = "%";
            contains = "%";
            this.condition = "";
        }else{
            endWith = "%" + condition.trim();
            startWith = condition.trim() + "%";
            contains = "%" + condition.trim() + "%";
            this.condition = condition.trim();
        }
    }

    public String getCondition(MatchType type){
        switch (type){

            case FULL:
                return getCondition();
            case END_WITCH:
                return getEndWith();
            case START_WITCH:
                return getStartWith();
            case CONTAIN:
                return getContains();
        }

        return getCondition();
    }

    public String getCondition() {
        return condition;
    }

    public boolean isEmpty() {
        return empty;
    }

    public String getEndWith() {
        return endWith;
    }

    public String getStartWith() {
        return startWith;
    }

    public String getContains() {
        return contains;
    }

    public boolean isDirty(String other){
        String otherValue = other == null ? "" : other.trim();
        return DataHelper.isDirty(condition,otherValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConditionAdapter that = (ConditionAdapter) o;

        return condition.equals(that.condition);

    }

    @Override
    public int hashCode() {
        return condition.hashCode();
    }
}
