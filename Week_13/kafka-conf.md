  简单性能测  

环境 window10 8g ssd

生产者压测

.\kafka-producer-perf-test.bat --topic press-test --num-records 1000000 --record-size 1000 --throughput 20000 --producer-props bootstrap.servers=localhost:9092



结果：

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

大约：20000条记录每秒

消费者压测

1个线程：

 .\ kafka-consumer-perf-test.sh ---bootstrap-server localhost:9092 --topic press-test --
fetch-size 1048576 --messages 100000 --threads 1  

结果：

2021-01-10 21:28:14:313, 2021-01-10 21:28:17:685, 95.7747, 28.4029, 100428, 29782.9181, 1610285294753, -1610285291381, -0.0000, -0.0001

3.3s 10w条

4个线程：

 .\kafka-consumer-perf-test.bat --bootstrap-server localhost:9092 --topic press
-test --fetch-size 1048576 --messages 100000 --threads 4

结果：2021-01-10 21:28:26:723, 2021-01-10 21:28:28:731, 95.7747, 47.6965, 100428, 50013.9442, 1610285307150, -1610285305142, -0.0000, -0.0001

2s 10w条





