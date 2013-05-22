@echo off
color 9f
title L2J Hellas Game Server Console http://l2jhellas.eu/
:start
REM ----------- Set Class Paths and Calls setenv.bat -----------------
SET OLDCLASSPATH=%CLASSPATH%
call classpath.bat
REM ------------------------------------------------------------------

REM -------------------------------------
REM Default parameters for a basic server.
java -Dfile.encoding=UTF-8 -Xmx768m -Xmn256m -Xmn256m -XX:PermSize=32m -XX:SurvivorRatio=8 -Xnoclassgc -XX:+AggressiveOpts com.l2jhellas.gameserver.GameServer

REM If you have a big server and lots of memory, you could experiment for example with:

REM FOR LOW COMPUTERS
REM java -Dfile.encoding=UTF-8 -Xmx768m -Xmn256m -Xmn256m -XX:PermSize=32m -XX:SurvivorRatio=8 -Xnoclassgc -XX:+AggressiveOpts com.l2jhellas.gameserver.GameServer
REM FOR MIDIUM COMPUTERS
REM java -Dfile.encoding=UTF-8 -Xmx1536m -Xmn512m -Xmn512m -XX:PermSize=64m -XX:SurvivorRatio=8 -Xnoclassgc -XX:+AggressiveOpts com.l2jhellas.gameserver.GameServer
REM FOR GOOD COMPUTERS
REM java -Dfile.encoding=UTF-8 -Xmx2g -Xmn512m -Xmn512m -XX:PermSize=128m -XX:SurvivorRatio=8 -Xnoclassgc -XX:+AggressiveOpts com.l2jhellas.gameserver.GameServer
REM FOR SUPER COMPUTERS
REM 1: java -server -Dfile.encoding=UTF-8 -Xmx2g -Xmn512m -Xmn512m -XX:PermSize=128m -XX:SurvivorRatio=8 -Xnoclassgc -XX:+AggressiveOpts com.l2jhellas.gameserver.GameServer
REM 2: java -server -Dfile.encoding=UTF-8 -Xmx4g -Xmn2g -Xmn1g -XX:PermSize=512m -XX:SurvivorRatio=8 -Xnoclassgc -XX:+AggressiveOpts com.l2jhellas.gameserver.GameServer
REM Some of those isn't tested (we got less ram than 3Gb) ;'(
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
