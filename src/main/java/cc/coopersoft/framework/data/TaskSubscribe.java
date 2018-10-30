package cc.coopersoft.framework.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TaskSubscribe extends TaskAction implements java.io.Serializable{


    private String page;
    private Map<String,String> params = new HashMap<>();
    private Set<String> dependencies = new HashSet<>();

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Set<String> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Set<String> dependencies) {
        this.dependencies = dependencies;
    }



}
