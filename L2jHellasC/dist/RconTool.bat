@echo off
SET CLASSPATH=%CLASSPATH%;./lib/rcon.jar
@java -Dfile.encoding=UTF-8 -Xmx1024m com.pauler.Main
@pause
