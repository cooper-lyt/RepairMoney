package cc.coopersoft.framework.data;


import java.util.Date;

public interface BusinessInstance {

    public enum Source{
        BUSINESS //业务
        ,IMPORT //导入
        ,BEFOR_INPUT //补录
        ,OUT_SIDE //外部建立
    }

    //仅对应工作流状态， 其它状态见各业务的状态
    public enum BusinessStatus{
        RUNNING  //运行中
        ,ABORT   //中止
        ,SUSPEND  //挂起
        ,COMPLETE //完成
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
}
