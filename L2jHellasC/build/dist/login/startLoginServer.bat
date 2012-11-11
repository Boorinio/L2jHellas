@echo off
color 9f
title LoginServer Console
:start
REM ----------- Set Class Paths and Calls setenv.bat -----------------
SET OLDCLASSPATH=%CLASSPATH%
call classpath.bat
REM ------------------------------------------------------------------

REM -------------------------------------
REM Default parameters for a basic server.
java -Dfile.encoding=UTF-8 -Xmx128m com.l2jhellas.loginserver.L2LoginServer
REM -------------------------------------

SET CLASSPATH=%OLDCLASSPATH%

if ERRORLEVEL 2 goto restart
if ERRORLEVEL 1 goto error
goto end
:restart
echo.
echo Admin Restart ...
echo.
goto start
:error
echo.
echo Server Terminated
echo.
:end
echo.
echo Server Terminated
echo.
pause
