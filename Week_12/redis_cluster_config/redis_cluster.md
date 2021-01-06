学习笔记

redis cluser config

复制6个节点 7000-7005

修改redis.conf文件

1. port 700X
2. cluster-config-file "nodes-700X.conf"
3. dbfilename "dump_700X.rdb"
4. pidfile "/var/run/redis_700X.pid"(非必须)

配置一主一从

./src/redis-trib.rb create --replicas 1 127.0.0.1:7000 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005