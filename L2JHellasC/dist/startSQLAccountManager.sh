#!/bin/sh
# if any problems about bad intercepter a solution is
# sudo apt-get install dos2unix
# and in terminal convert all sh with: dos2unix *.sh
java -Djava.util.logging.config.file=console.cfg -cp ./../libs/*;l2jhellas.jar com.l2jhellas.tools.accountmanager.SQLAccountManager
