#!/bin/sh

# Licensed 描述
#
#
#
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
EXECUTABLE=ruoyi.sh

#判断入口参数进行脚本执行
case "$1" in

	start)
		exec "$PRGDIR"/"$EXECUTABLE" start "$@"
		echo "Starting Ruoyi ... "
		
	;;
	stop)
		exec "$PRGDIR"/"$EXECUTABLE" stop "$@"
		echo "Stoping Ruoyi"
	;;
	
	*)
		echo "{start|stop}"
esac

exit 0

