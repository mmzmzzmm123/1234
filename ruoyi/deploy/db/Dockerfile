FROM mysql:5.7

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

COPY ./sql/*.sql /usr/local/work/

COPY ./sql/shell_mysql.sh /docker-entrypoint-initdb.d