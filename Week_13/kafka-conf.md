**kafka启动** 

`.\kafka-server-start.bat D:\kafka\kafka_2.13-2.7.0\config\server.properties`

**kafka关闭**

 *.\kafka-server-stop.bat*

 **简单性能测**  

环境 window10 8g ssd

生产者压测

```
.\kafka-producer-perf-test.bat --topic press-test --num-records 1000000 --record-size 1000 --throughput 20000 --producer-props bootstrap.servers=localhost:9092
```



结果：

```
99942 records sent, 19988.4 records/sec (19.06 MB/sec), 76.1 ms avg latency, 436.0 ms max latency.
100060 records sent, 20008.0 records/sec (19.08 MB/sec), 1.3 ms avg latency, 13.0 ms max latency.
100040 records sent, 20004.0 records/sec (19.08 MB/sec), 1.2 ms avg latency, 11.0 ms max latency.
100069 records sent, 20013.8 records/sec (19.09 MB/sec), 1.3 ms avg latency, 12.0 ms max latency.
100019 records sent, 20003.8 records/sec (19.08 MB/sec), 1.3 ms avg latency, 12.0 ms max latency.
100032 records sent, 20002.4 records/sec (19.08 MB/sec), 1.3 ms avg latency, 13.0 ms max latency.
100000 records sent, 20000.0 records/sec (19.07 MB/sec), 1.3 ms avg latency, 27.0 ms max latency.
100048 records sent, 20009.6 records/sec (19.08 MB/sec), 1.2 ms avg latency, 11.0 ms max latency.
98656 records sent, 19731.2 records/sec (18.82 MB/sec), 2.4 ms avg latency, 102.0 ms max latency.
1000000 records sent, 19996.800512 records/sec (19.07 MB/sec), 13.05 ms avg latency, 436.00 ms max latency, 1 ms 50th, 70 ms 95th, 316 ms 99th, 427 ms 99.9th.
```

大约：20000条记录每秒

消费者压测

1个线程：

```
.\ kafka-consumer-perf-test.sh ---bootstrap-server localhost:9092 --topic press-test --fetch-size 1048576 --*messages 100000 --threads 1
```

结果：

```
2021-01-10 21:28:14:313, 2021-01-10 21:28:17:685, 95.7747, 28.4029, 100428, 29782.9181, 1610285294753, -1610285291381, -0.0000, -0.0001
```

3.3s 10w条

4个线程：

```
 .\kafka-consumer-perf-test.bat --bootstrap-server localhost:9092 --topic press-test --fetch-size 1048576 --messages 100000 --threads 4
```

结果：

```
2021-01-10 21:28:26:723, 2021-01-10 21:28:28:731, 95.7747, 47.6965, 100428, 50013.9442, 1610285307150, -1610285305142, -0.0000, -0.0001
```

2s 10w条



**kafka集群**

1. 确保zk干净

   2 准备3个节点  server900X.properties

- broker.id=X

- listeners=PLAINTEXT://:900X
- log.dirs=D:\\kafka\\kafka_2.13-2.7.0\\data\\900X\\logs



测试cluster性能

创建topic 

```
 .\kafka-topics.bat --zookeeper localhost:2181 --create --topic press-test32 --partitions 3 --replication-factor 2
```

​      .\kafka-console-producer.bat --bootstrap-server localhost:9001 --topic quickstart-events



压测

```
.\kafka-producer-perf-test.bat --topic press-test32-num-records 1000000 --record-size 1000 --throughput 20000 --producer-props bootstrap.servers=localhost:9001

79934 records sent, 15980.4 records/sec (15.24 MB/sec), 784.7 ms avg latency, 2015.0 ms max latency.
107412 records sent, 21478.1 records/sec (20.48 MB/sec), 999.1 ms avg latency, 2676.0 ms max latency.
112138 records sent, 22414.2 records/sec (21.38 MB/sec), 362.9 ms avg latency, 1825.0 ms max latency.
100273 records sent, 20054.6 records/sec (19.13 MB/sec), 12.3 ms avg latency, 125.0 ms max latency.
100697 records sent, 20139.4 records/sec (19.21 MB/sec), 6.1 ms avg latency, 100.0 ms max latency.
21553 records sent, 3917.3 records/sec (3.74 MB/sec), 101.7 ms avg latency, 4688.0 ms max latency.
18800 records sent, 3712.5 records/sec (3.54 MB/sec), 8260.6 ms avg latency, 8766.0 ms max latency.
3968 records sent, 754.9 records/sec (0.72 MB/sec), 9577.5 ms avg latency, 13483.0 ms max latency.
13104 records sent, 2515.6 records/sec (2.40 MB/sec), 14845.4 ms avg latency, 18640.0 ms max latency.
113808 records sent, 22761.6 records/sec (21.71 MB/sec), 3369.0 ms avg latency, 19386.0 ms max latency.
140384 records sent, 28076.8 records/sec (26.78 MB/sec), 1145.3 ms avg latency, 2164.0 ms max latency.
12638 records sent, 2527.6 records/sec (2.41 MB/sec), 1197.6 ms avg latency, 6767.0 ms max latency.
21072 records sent, 4141.5 records/sec (3.95 MB/sec), 6691.2 ms avg latency, 10413.0 ms max latency.
33014 records sent, 6328.2 records/sec (6.04 MB/sec), 5309.2 ms avg latency, 13779.0 ms max latency.
1008 records sent, 172.6 records/sec (0.16 MB/sec), 10753.5 ms avg latency, 18160.0 ms max latency.
43586 records sent, 8018.0 records/sec (7.65 MB/sec), 10184.8 ms avg latency, 23532.0 ms max latency.
43440 records sent, 8686.3 records/sec (8.28 MB/sec), 5325.5 ms avg latency, 14879.0 ms max latency.
```

```
.\kafka-consumer-perf-test.bat --bootstrap-server localhost:9003 --topic press-test32 --fetch-size 1048576 --messages 100000 --threads 4

start.time, end.time, data.consumed.in.MB, MB.sec, data.consumed.in.nMsg, nMsg.sec, rebalance.time.ms, fetch.time.ms, fetch.MB.sec, fetch.nMsg.sec
2021-01-10 22:48:59:151, 2021-01-10 22:49:01:268, 95.6783, 45.1952, 100326, 47390.6471, 1610290139590, -1610290137473, -0.0000, -0.0001
```

2s/10w



