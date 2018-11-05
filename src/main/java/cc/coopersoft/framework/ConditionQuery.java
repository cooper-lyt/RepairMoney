package cc.coopersoft.framework;

import cc.coopersoft.framework.tools.DataHelper;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConditionQuery {

    public static ConditionQuery instance(List<ConditionAdapter> conditionAdapters, ConditionField... fields){
        return new ConditionQuery(conditionAdapters,fields, null);
    }

    public static ConditionQuery instance(List<ConditionAdapter> conditionAdapters, String other, ConditionField... fields){
        return new ConditionQuery(conditionAdapters,fields, other);
    }

    public static ConditionField instanceField(String fieldName, ConditionAdapter.MatchType matchType){
        return new ConditionField(fieldName,matchType);
    }


    private final static String CONDITION_PARAM_NAME = "c";

    public static class ConditionField {

        private String fieldName;
        private ConditionAdapter.MatchType matchType;

        String condition(String paramName){
            return fieldName + " " + matchType.symbol + " :" + paramName + " ";
        }

        ConditionField(String fieldName, ConditionAdapter.MatchType matchType) {
            this.fieldName = fieldName;
            this.matchType = matchType;
        }

    }

    private String where = "";

    private Map<String,String> parameter;


    ConditionQuery(List<ConditionAdapter> conditionAdapters, ConditionField[] fields, String other) {
        init(conditionAdapters,fields,other);
    }

    private void init(List<ConditionAdapter> conditionAdapters, ConditionField[] fields, String other){
        boolean notEmpty = !conditionAdapters.isEmpty() && (fields != null) && (fields.length > 0);

        if (notEmpty || !DataHelper.empty(other)) {
            where = " WHERE ";
            if (!DataHelper.empty(other)){
                where += other;
            }
            if (notEmpty) {
                if (!DataHelper.empty(other)){
                    where += " AND ";
                }
                parameter = new HashMap<>();
                where += " ( ";
                boolean first = true;
                for (int i = 0; i < conditionAdapters.size(); i++) {
                    ConditionAdapter c = conditionAdapters.get(i);
                    for (ConditionField field : fields) {
                        if (!first) {
                            where += " OR ";
                        }
                        String paramName = CONDITION_PARAM_NAME + i + field.matchType.name();
                        if (parameter.get(paramName) == null) {
                            parameter.put(paramName, c.getCondition(field.matchType));
                        }
                        where += field.condition(paramName);

                        first = false;
                    }
                }
                where += " ) ";
            }
        }
    }

    public String where() {
        return where;
    }

    public <T> TypedQuery<T> parameterization(TypedQuery<T> query){
        if (parameter == null || parameter.isEmpty()){
            return query;
        }
        TypedQuery<T> result = query;
        for(Map.Entry<String,String> param: parameter.entrySet()){
            result = result.setParameter(param.getKey(),param.getValue());
        }
        return result;
    }
}
