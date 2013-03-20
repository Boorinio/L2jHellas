#!/bin/bash
##########################################################################
## Copyright (C) 2004-2013 L2J DataPack                                 ##
##                                                                      ##
## This file is part of L2J DataPack.                                   ##
##                                                                      ##
## L2J DataPack is free software: you can redistribute it and/or modify ##
## it under the terms of the GNU General Public License as published by ##
## the Free Software Foundation, either version 3 of the License, or    ##
## (at your option) any later version.                                  ##
##                                                                      ##
## L2J DataPack is distributed in the hope that it will be useful,      ##
## but WITHOUT ANY WARRANTY; without even the implied warranty of       ##
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU     ##
## General Public License for more details.                             ##
##                                                                      ##
## You should have received a copy of the GNU General Public License    ##
## along with this program. If not, see <http://www.gnu.org/licenses/>. ##
##########################################################################
## WARNING!  WARNING!  WARNING!  WARNING! ##
##                                        ##
## DON'T USE NOTEPAD TO CHANGE THIS FILE  ##
## INSTEAD USE SOME DECENT TEXT EDITOR.   ##
## NEWLINE CHARACTERS DIFFER BETWEEN DOS/ ##
## WINDOWS AND UNIX.                      ##
##                                        ##
## USING NOTEPAD TO SAVE THIS FILE WILL   ##
## LEAVE IT IN A BROKEN STATE!!!          ##
############################################
trap finish 2

configure() {
clear
echo "#############################################"
echo "# Welcome To l2jhellas DB Installer script. #"
echo "# you will be prompted in the               #"
echo "# configuration area.                       #"
echo "# NOTE:                                     #"
echo "# No change will be performed in your DB    #"
echo "# I will just ask you some questions about  #"
echo "# your hosts and DB.                        #"
echo "#############################################"
MYSQLDUMPPATH=`which -a mysqldump 2>/dev/null`
MYSQLPATH=`which -a mysql 2>/dev/null`
if [ $? -ne 0 ]; then
echo "We were unable to find MySQL binaries on your path"
while :
 do
  echo -ne "\nPlease enter MySQL binaries directory (no trailing slash): "
  read MYSQLBINPATH
    if [ -e "$MYSQLBINPATH" ] && [ -d "$MYSQLBINPATH" ] && \
       [ -e "$MYSQLBINPATH/mysqldump" ] && [ -e "$MYSQLBINPATH/mysql" ]; then
       MYSQLDUMPPATH="$MYSQLBINPATH/mysqldump"
       MYSQLPATH="$MYSQLBINPATH/mysql"
       break
    else
       echo "The data you entered is invalid. Please verify and try again."
       exit 1
    fi
 done
fi

#GS
echo -ne "\nPlease enter MySQL Game Server hostname (default localhost): "
read GSDBHOST
if [ -z "$GSDBHOST" ]; then
  GSDBHOST="localhost"
fi
echo -ne "\nPlease enter MySQL Game Server database name (default l2jhellas): "
read GSDB
if [ -z "$GSDB" ]; then
  GSDB="l2jhellas"
fi
echo -ne "\nPlease enter MySQL Game Server user (default root): "
read GSUSER
if [ -z "$GSUSER" ]; then
  GSUSER="root"
fi
echo -ne "\nPlease enter MySQL Game Server $GSUSER's password (won't be displayed): "
stty -echo
read GSPASS
stty echo
echo ""
if [ -z "$GSPASS" ]; then
  echo "Hum.. I'll let it be but don't be stupid and avoid empty passwords"
elif [ "$GSUSER" == "$GSPASS" ]; then
  echo "You're not too brilliant choosing passwords huh?"
fi
save_config $1
}

save_config() {
if [ -n "$1" ]; then
CONF="$1"
else 
CONF="database_installer.rc"
fi
echo ""
echo "With these data I can generate a configuration file which can be read"
echo "on future updates. WARNING: this file will contain clear text passwords!"
echo -ne "Shall I generate config file $CONF? (Y/n):"
read SAVE
if [ "$SAVE" == "y" -o "$SAVE" == "Y" -o "$SAVE" == "" ];then 
cat <<EOF>$CONF
#Configuration settings for L2J-Datapack database installer script
MYSQLDUMPPATH=$MYSQLDUMPPATH
MYSQLPATH=$MYSQLPATH
GSDBHOST=$GSDBHOST
GSDB=$GSDB
GSUSER=$GSUSER
GSPASS=$GSPASS
EOF
chmod 600 $CONF
echo "Configuration saved as $CONF"
echo "Permissions changed to 600 (rw- --- ---)"
elif [ "$SAVE" != "n" -a "$SAVE" != "N" ]; then
  save_config
fi
}

load_config() {
if [ -n "$1" ]; then
CONF="$1"
else 
CONF="database_installer.rc"
fi
if [ -e "$CONF" ] && [ -f "$CONF" ]; then
. $CONF
else
echo "Settings file not found: $CONF"
echo "You can specify an alternate settings filename:"
echo $0 config_filename
echo ""
echo "If file doesn't exist it can be created"
echo "If nothing is specified script will try to work with ./database_installer.rc"
echo ""
configure $CONF
fi
}

gs_backup(){
while :
  do
   clear
   echo ""
   echo -ne "Do you want to make a backup copy of your GSDB? (y/n): "
   read GSB
   if [ "$GSB" == "Y" -o "$GSB" == "y" ]; then
     echo "Trying to create a Game Server DataBase."
     $MYSQLDUMPPATH --add-drop-table -h $GSDBHOST -u $GSUSER --password=$GSPASS $GSDB > gs_backup.sql
     if [ $? -ne 0 ];then
	 clear
     echo ""
     echo "There was a problem accesing your GS database, either it wasnt created or authentication data is incorrect."
     exit 1
     fi
     break
   elif [ "$GSB" == "n" -o "$GSB" == "N" ]; then 
     break
   fi
  done 
gs_ask
}

gs_ask(){
clear
echo ""
echo "GAME SERVER DATABASE install:"
echo ""
echo "(f) Full: WARNING! I'll destroy ALL of your existing character"
echo "    data (i really mean it: items, pets.. ALL)"
echo ""
echo "(u) Upgrade: I'll do my best to preserve all of your character"
echo "    data."
echo ""
echo "(q) Quit"
echo ""
echo -ne "GAMESERVER DB install type: "
read GSASK
case "$GSASK" in
	"f"|"F") gs_cleanup I;;
	"u"|"U") gs_upgrade U;;
	"q"|"Q") finish;;
	*) gs_ask;;
esac
}

gs_cleanup(){
clear
echo "Deleting all Game Server tables for new content."
$MYG < gs_cleanup.sql
gs_install
}

gs_upgrade(){
clear
echo ""
echo "Upgrading structure of Game Server tables (this could take awhile, be patient)"
echo ""
for file in $(ls ../sql/game/updates/*.sql);do
	$MYG --force < $file 2>> gs_error.log
done
gs_install
}

gs_install(){
clear
if [ "$1" == "I" ]; then 
echo ""
echo "Installing new Game Server content."
echo ""
else
echo ""
echo "Upgrading Game Server content."
echo ""
fi
for gs in $(ls ../sql/game/*.sql);do
	echo "Installing GameServer table : $gs"
	$MYG < $gs
done
finish
}


finish(){
clear
echo "L2JDP Database Installer"
echo ""
echo "Thanks for using our software."
echo "visit http://www.l2jhellas.eu for more info about"
echo "the DataPack Project."

}

clear
load_config $1
MYG="$MYSQLPATH -h $GSDBHOST -u $GSUSER --password=$GSPASS -D $GSDB"
gs_backup