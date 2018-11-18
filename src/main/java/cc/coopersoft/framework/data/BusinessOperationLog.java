package cc.coopersoft.framework.data;

import java.util.Date;
import java.util.EnumSet;

public interface BusinessOperationLog {

    public enum Type{
        CREATE,CHECK_ACCEPT,CHECK_BACK,NEXT,BACK,ABORT,DELETE,SUSPEND,CONTINUE,ASSIGN;

        public boolean isManager(){
            return EnumSet.of(ABORT,DELETE,SUSPEND,CONTINUE,ASSIGN).contains(this);
        }

    }

    Type getType();

    void setType(Type type);


    String getEmpCode();

    void setEmpCode(String empCode);

    String getEmpName();

    void setEmpName(String empName);


    Date getOperationTime();

    void setOperationTime(Date time);


    String getComments();

    void setComments(String comments);


    String getTaskName();

    void setTaskName(String taskName);


    String getTaskDescription();

    void setTaskDescription(String taskDescription);

    Long getTaskId();

    void setTaskId(Long taskId);
}
