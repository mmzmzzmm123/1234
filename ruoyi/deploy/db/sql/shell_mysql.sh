#!/bin/bash
mysql --default-character-set=utf8 -uroot -p$MYSQL_ROOT_PASSWORD <<EOF
source /usr/local/work/database.sql;
source /usr/local/work/quartz.sql;
source /usr/local/work/ry_20200415.sql;