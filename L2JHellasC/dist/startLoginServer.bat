@echo off
color 9f
title L2J Hellas Login Server Console http://l2jhellas.eu/
:start
REM -------------------------------------
REM Default parameters for a basic server.
java -Dfile.encoding=UTF-8 -Xmx128m -cp ./../libs/*; com.l2jhellas.loginserver.LoginServer
REM -------------------------------------

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
echo LoginServer terminated abnormaly
echo.
:end
echo.
echo LoginServer terminated
echo.
pause