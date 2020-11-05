Gateway 压测报告

**httpClient:**

```
Finished at 2020/11/5 10:58:16 (took 00:01:13.4422007)
Status 200:    19017

RPS: 310.9 (requests/second)
Max: 3404ms
Min: 1ms
Avg: 2.1ms

  50%   below 1ms
  60%   below 2ms
  70%   below 2ms
  80%   below 3ms
  90%   below 3ms
  95%   below 4ms
  98%   below 5ms
  99%   below 7ms
99.9%   below 28ms
```



**NettyClient**

```
Finished at 2020/11/5 10:54:14 (took 00:01:08.6569270)
Status 200:    10294

RPS: 168.6 (requests/second)
Max: 1311ms
Min: 3ms
Avg: 4.7ms

  50%   below 4ms
  60%   below 4ms
  70%   below 5ms
  80%   below 5ms
  90%   below 6ms
  95%   below 8ms
  98%   below 10ms
  99%   below 12ms
99.9%   below 30ms
```

