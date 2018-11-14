package cc.coopersoft.framework.data;


import java.util.Date;

public interface BusinessInstance {

    public enum Source{
        BUSINESS //业务
        ,IMPORT //导入
        ,BEFORE_INPUT //补录
        //TODO 在业务定义中加入是否补录
        ,OUT_SIDE //外部建立
    }


    public enum BusinessStatus{
        RUNNING  //运行中
        ,ABORT   //中止
        ,SUSPEND  //挂起
        ,COMPLETE //完成
        ,DELETED //撤回
        ,CANCEL //注销
    }


    String getId();

    void setId(String id);


    Source getSource();

    void setSource(Source source);


    String getMemo();

    void setMemo(String memo);


    String getDefineName();

    void setDefineName(String defineName);


    String getDefineId();

    void setDefineId(String defineId);


    int getDefineVersion();

    void setDefineVersion(int defineVersion);


    Date getRegTime();

    void setRegTime(Date regTime);


    boolean isReg();

    void setReg(boolean reg);


    Date getDataTime();

    void setDataTime(Date dataTime);

    BusinessStatus getStatus();

    void setStatus(BusinessStatus status);

    String getSearchKey();

    void setSearchKey(String searchKey);

    String getSummary();

    void setSummary(String summary);

}
