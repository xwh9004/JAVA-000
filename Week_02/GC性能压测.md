

  使用压测工具（wrk或sb），演练gateway-server-0.0.1-SNAPSHOT.jar 示例。  

环境 Window系统 4核8g环境,

。。。。。压测效果不明显

-XX:+UseParallelGC

| GC                 | -Xms512m -Xmx512m                                            | -Xms1g -Xmx1g                                                | -Xms2g -Xmx2g                                                |
| ------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| UseParallelGC      | RPS: 3436.1 (requests/second)<br/>Max: 129ms<br/>Min: 0ms<br/>Avg: 0.5ms<br/><br/>  50%   below 0ms<br/>  60%   below 0ms<br/>  70%   below 0ms<br/>  80%   below 1ms<br/>  90%   below 2ms<br/>  95%   below 3ms<br/>  98%   below 4ms<br/>  99%   below 5ms<br/>99.9%   below 9ms | RPS: 3780 (requests/second)<br/>Max: 137ms<br/>Min: 0ms<br/>Avg: 0.4ms<br/><br/>  50%   below 0ms<br/>  60%   below 0ms<br/>  70%   below 0ms<br/>  80%   below 1ms<br/>  90%   below 1ms<br/>  95%   below 2ms<br/>  98%   below 4ms<br/>  99%   below 5ms<br/>99.9%   below 9ms | RPS: 3607.2 (requests/second)<br/>Max: 150ms<br/>Min: 0ms<br/>Avg: 0.4ms<br/><br/>  50%   below 0ms<br/>  60%   below 0ms<br/>  70%   below 0ms<br/>  80%   below 1ms<br/>  90%   below 1ms<br/>  95%   below 2ms<br/>  98%   below 4ms<br/>  99%   below 5ms<br/>99.9%   below 9ms |
| UseSerialGC        | RPS: 3535.3 (requests/second)<br/>Max: 147ms<br/>Min: 0ms<br/>Avg: 0.5ms<br/><br/>  50%   below 0ms<br/>  60%   below 0ms<br/>  70%   below 0ms<br/>  80%   below 1ms<br/>  90%   below 2ms<br/>  95%   below 3ms<br/>  98%   below 4ms<br/>  99%   below 5ms<br/>99.9%   below 9ms | RPS: 3709.6 (requests/second)<br/>Max: 142ms<br/>Min: 0ms<br/>Avg: 0.4ms<br/><br/>  50%   below 0ms<br/>  60%   below 0ms<br/>  70%   below 0ms<br/>  80%   below 1ms<br/>  90%   below 1ms<br/>  95%   below 2ms<br/>  98%   below 4ms<br/>  99%   below 5ms<br/>99.9%   below 8ms | RPS: 3610.8 (requests/second)<br/>Max: 148ms<br/>Min: 0ms<br/>Avg: 0.5ms<br/><br/>  50%   below 0ms<br/>  60%   below 0ms<br/>  70%   below 0ms<br/>  80%   below 1ms<br/>  90%   below 1ms<br/>  95%   below 2ms<br/>  98%   below 4ms<br/>  99%   below 5ms<br/>99.9%   below 9ms |
| UseG1GC            | RPS: 3542 (requests/second)<br/>Max: 138ms<br/>Min: 0ms<br/>Avg: 0.4ms<br/><br/>  50%   below 0ms<br/>  60%   below 0ms<br/>  70%   below 0ms<br/>  80%   below 1ms<br/>  90%   below 1ms<br/>  95%   below 2ms<br/>  98%   below 4ms<br/>  99%   below 5ms<br/>99.9%   below 9ms | RPS: 3491.6 (requests/second)<br/>Max: 147ms<br/>Min: 0ms<br/>Avg: 0.4ms<br/><br/>  50%   below 0ms<br/>  60%   below 0ms<br/>  70%   below 0ms<br/>  80%   below 0ms<br/>  90%   below 1ms<br/>  95%   below 2ms<br/>  98%   below 4ms<br/>  99%   below 5ms<br/>99.9%   below 10ms | RPS: 3589.9 (requests/second)<br/>Max: 118ms<br/>Min: 0ms<br/>Avg: 0.4ms<br/><br/>  50%   below 0ms<br/>  60%   below 0ms<br/>  70%   below 0ms<br/>  80%   below 1ms<br/>  90%   below 1ms<br/>  95%   below 2ms<br/>  98%   below 4ms<br/>  99%   below 5ms<br/>99.9%   below 9ms |
| UseConcMarkSweepGC | RPS: 3365.1 (requests/second)<br/>Max: 78ms<br/>Min: 0ms<br/>Avg: 0.5ms<br/><br/>  50%   below 0ms<br/>  60%   below 0ms<br/>  70%   below 0ms<br/>  80%   below 1ms<br/>  90%   below 2ms<br/>  95%   below 3ms<br/>  98%   below 4ms<br/>  99%   below 5ms<br/>99.9%   below 10ms | RPS: 3427.3 (requests/second)<br/>Max: 89ms<br/>Min: 0ms<br/>Avg: 0.3ms<br/><br/>  50%   below 0ms<br/>  60%   below 0ms<br/>  70%   below 0ms<br/>  80%   below 0ms<br/>  90%   below 1ms<br/>  95%   below 2ms<br/>  98%   below 4ms<br/>  99%   below 5ms<br/>99.9%   below 9ms | RPS: 3329 (requests/second)<br/>Max: 89ms<br/>Min: 0ms<br/>Avg: 0.5ms<br/><br/>  50%   below 0ms<br/>  60%   below 0ms<br/>  70%   below 0ms<br/>  80%   below 1ms<br/>  90%   below 2ms<br/>  95%   below 3ms<br/>  98%   below 4ms<br/>  99%   below 5ms<br/>99.9%   below 9ms |

当使用ParallelGC时，当GC线程数为ParallelGCThreads=20结果如下

RPS: 2924.9 (requests/second)
Max: 93ms
Min: 0ms
Avg: 0.5ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 1ms
  90%   below 1ms
  95%   below 2ms
  98%   below 4ms
  99%   below 5ms
99.9%   below 11ms

当使用ParallelGC时，当GC线程数为ParallelGCThreads=5结果如下

Status 200:    218239

RPS: 3566.4 (requests/second)
Max: 84ms
Min: 0ms
Avg: 0.5ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 1ms
  90%   below 2ms
  95%   below 3ms
  98%   below 4ms
  99%   below 5ms
99.9%   below 9ms

