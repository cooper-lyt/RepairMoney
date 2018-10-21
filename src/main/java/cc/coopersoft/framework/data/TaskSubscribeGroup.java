package cc.coopersoft.framework.data;

import cc.coopersoft.framework.data.model.SubscribeGroupEntity;

import java.util.ArrayList;
import java.util.List;

public class TaskSubscribeGroup {

    private String name;
    private String icon;
    private int index;

    private List<TaskSubscribe> subscribes = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<TaskSubscribe> getSubscribes() {
        return subscribes;
    }

    public void setSubscribes(List<TaskSubscribe> subscribes) {
        this.subscribes = subscribes;
    }
}
