sentinel.conf主从配置说明

redis prot 26379 conf文件修改一下内容 

 

1. port 26379(指定端口)

2. sentinel monitor mymaster 127.0.0.1 7000 2
    sentinel down-after-milliseconds mymaster 10000

3. 修改sentinel id 

   sentinel myid ba3ae7a301cfbcd424d3221ec861e0a2b495726e





启动哨兵 ./src/redis-sentinel ./../26379/redis.conf

登入src/redis-cli -p 26379



master0:name=mymaster,status=ok,address=127.0.0.1:7000,slaves=3,sentinels=3