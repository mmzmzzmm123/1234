#!/bin/sh

# Licensed 描述
#
#
#
# 
# -----------------------------------------------------------------------------
#
# 环境变量
#	
#	请不要在这里设置您的变量脚本，若要设置请移步
# 	setenv.sh 
#
#	RUOYI_HOME	可以指向您的“构建”目录。
#	RUOYI_BASE	(可选)解析RUOYI安装的动态部分的基目录#。如果不存在，解析为# RUOYI HOME指向的相同目录。
#	JAVA_OPTS	(可选)Java运行时选项,请在 setenv.sh 中设置	
#	JAVA_HOME	必须指向您的Java开发工具包安装。#使用"debug"参数运行
#	JRE_HOME	必须指向您的Java运行时环境。
#				如果为空，默认为JAVA HOME。如果同时设置JRE HOME和JAVA HOME #，则使用JRE HOME。
#	RUOYI_PID	(可选)当使用时，包含RUOYI启动java进程 pid 的文件路径
#	RUOYI_CMD	执行命令字符串
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

# 设置构建目录
[ -z "$RUOYI_HOME" ] && RUOYI_HOME=`cd "$PRGDIR/.." >/dev/null; pwd`

[ -z "$RUOYI_BASE" ] && RUOYI_BASE="$RUOYI_HOME"

#确保在启动时不使用任何用户定义的CLASSPATH变量，
#但允许在 setenv.sh 中指定它们，在极少数情况下需要时。

CLASSPATH=

if [ -r "$RUOYI_BASE/bin/setenv.sh" ]; then
  . "$RUOYI_BASE/bin/setenv.sh"
elif [ -r "$RUOYI_HOME/bin/setenv.sh" ]; then
  . "$RUOYI_HOME/bin/setenv.sh"
fi


#	java 命令位置
if [ -z "$JAVA_HOME" ]; then
    JAVA_BIN="`which java 2>/dev/null || type java 2>&1`"
    while [ -h "$JAVA_BIN" ]; do
        ls=`ls -ld "$JAVA_BIN"`
        link=`expr "$ls" : '.*-> \(.*\)$'`
        if expr "$link" : '/.*' > /dev/null; then
            JAVA_BIN="$link"
        else
            JAVA_BIN="`dirname "$JAVA_BIN"`/$link"
        fi
    done
    test -x "$JAVA_BIN" && JAVA_HOME="`dirname "$JAVA_BIN"`"
    test ".$JAVA_HOME" != . && JAVA_HOME=`cd "$JAVA_HOME/.." >/dev/null; pwd`
else
    JAVA_BIN="$JAVA_HOME/bin/java"
fi

# 设置 -pidfile
test ".$RUOYI_PID" = . && RUOYI_PID="$RUOYI_BASE/logs/ruoyi.pid"

RUOYI_CMD="nohup $JAVA_BIN -jar $RUOYI_BASE/ruoyi-admin/target/ruoyi-admin.jar $JAVA_OPTS >/dev/null 2>&1 &"
echo $RUOYI_PID
#echo $PRG $RUOYI_BASE $JAVA_OPTS $JAVA_BIN $RUOYI_PID $RUOYI_CMD

touch "$RUOYI_PID"


#eval exec  "$RUOYI_CMD" 



if [ "$1" = "start" ] ; then
	eval exec  "$RUOYI_CMD" 
	if [ ! -z "$RUOYI_PID" ]; then
    	echo $! > "$RUOYI_PID"
	fi

elif [ "$1" = "stop" ] ; then
	#echo "stop"
	kill -9 `cat "$RUOYI_PID"` >/dev/null 2>&1
	
fi

 