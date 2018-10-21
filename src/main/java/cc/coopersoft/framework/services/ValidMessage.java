package cc.coopersoft.framework.services;

public class ValidMessage {

    public enum Level{
        INFO, //信息级
        WARN, //警告级，
        SERVER,//服务级, 可从系统参数设置是否可以通过
        OFF //不接受级， 不可通过
    }

    private Level level;
    private String summary;
    private String detail;
    private Object[] params;

    public ValidMessage(Level level, String summary, String detail) {
        this.level = level;
        this.summary = summary;
        this.detail = detail;
    }

    public ValidMessage(Level level, String summary, String detail, Object... params) {
        this.level = level;
        this.summary = summary;
        this.detail = detail;
        this.params = params;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getSummary() {
        return summary;
    }


    public String getDetail() {
        return detail;
    }


    public Object[] getParams() {
        return params;
    }

}
