#!/bin/sh
# Licensed 描述

#
#	JAVA_HOME
#	JAVA_OPTS
# 
# -----------------------------------------------------------------------------
# 开始 脚本 服务
# -----------------------------------------------------------------------------

# 解析链接- $0可能是软链接
PRG="$0"

while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done
#echo $PRG

# 获取标准环境变量
PRGDIR=`dirname "$PRG"`

#echo $PRG

PRGDIR=`dirname "$PRG"`

JAVA_OPTS="-Xms256m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m"


