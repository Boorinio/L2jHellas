@echo off
cls
SET CLASSPATH=%CLASSPATH%;./rcon.jar
@java -Dfile.encoding=UTF-8 -Xmx2m com.pauler.Main
@pause