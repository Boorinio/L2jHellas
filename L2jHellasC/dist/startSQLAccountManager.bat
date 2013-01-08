@color 7f
echo OFF
CLS
REM ----------- Set Class Paths and Calls setenv.bat -----------------
SET OLDCLASSPATH=%CLASSPATH%
call classpath.bat
REM ------------------------------------------------------------------
@java -Djava.util.logging.config.file=console.cfg com.l2jhellas.accountmanager.SQLAccountManager
@pause
SET CLASSPATH=%OLDCLASSPATH%
