package cc.coopersoft.framework.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class BusinessSummary {

    public static String parseSummary(List<BusinessSummary> summary) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(summary);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e.getMessage(),e);
        }
    }

    public static BusinessSummary factorySummary(String label, String context, int widthBlock){
        return new BusinessSummary(label,context,widthBlock);
    }

    public static BusinessSummary factorySummary(String context,int widthBlock){
        return new BusinessSummary(context,widthBlock);
    }

    public static BusinessSummary factorySummary(String context){
        return new BusinessSummary(context,12);
    }

    public BusinessSummary() {
    }

    public BusinessSummary(String context, int widthBlock) {
        this.context = context;
        this.widthBlock = widthBlock;
    }

    public BusinessSummary(String label, String context, int widthBlock) {
        this.label = label;
        this.context = context;
        this.widthBlock = widthBlock;
    }

    private String label;

    private String context;

    private int widthBlock;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getWidthBlock() {
        return widthBlock;
    }

    public void setWidthBlock(int widthBlock) {
        this.widthBlock = widthBlock;
    }
}
