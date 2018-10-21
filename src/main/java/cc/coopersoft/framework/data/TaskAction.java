package cc.coopersoft.framework.data;

public class TaskAction implements java.io.Serializable {

    private String id;
    private String name;
    private String className;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString(){
        return "[" + id + "]" + name;
    }
}
