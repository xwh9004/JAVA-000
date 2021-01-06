redis.conf主从配置说明

primary redis prot 7000 conf文件修改一下内容 ： [redis_7000.conf](redis_7000.conf) 

1. port 6379 (指定端口)
2. pidfile /var/run/redis_6379.pid （可选配置）
3. daemonize yes （后台运行）

replicationredis prot 6380conf文件修改一下内容： [redis_7001.conf](redis_7001.conf) 

1. port 6380 (指定端口)
2. pidfile /var/run/redis_6380.pid （可选配置）
3. daemonize yes （后台运行）
4. slaveof 127.0.0.1 7000（指定primary ip）



启动primary实例 ./src/redis-server ./../7000/redis.conf

启动replication实例 ./src/redis-server ./../7001/redis.conf



登入src/redis-cli -p 7000



