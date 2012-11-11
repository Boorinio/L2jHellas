#!/bin/sh
. ./setenv.sh
java -Djava.util.logging.config.file=console.cfg com.l2jhellas.accountmanager.SQLAccountManager
