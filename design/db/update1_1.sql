
use WXZJ;
rename table BUSINESS_EMP to BUSINESS_LOG;
ALTER TABLE BUSINESS_LOG MODIFY TASK_NAME varchar(128);