#!/usr/bin/env bash

# 导出数据库结构
docker exec demo-server-mysql sh -c 'exec mysqldump --no-data \
--databases demo_server -uroot -p"$MYSQL_ROOT_PASSWORD"' > mysql/init-sql/demo-server.sql


