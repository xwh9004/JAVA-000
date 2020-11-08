Gateway 压测报告

**httpClient:**  sb -u  http://localhost:8888/api/hello -C 20 -N 60

```
Finished at 2020/11/7 星期六 19:22:57 (took 00:01:04.7073463)
Status 200:    76203

RPS: 1248.4 (requests/second)
Max: 71ms
Min: 0ms
Avg: 0ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 0ms
  95%   below 0ms
  98%   below 1ms
  99%   below 1ms
99.9%   below 2ms

```



**NettyClient**

```
Finished at 2020/11/7 星期六 19:26:12 (took 00:01:04.7250536)
Status 200:    83254

RPS: 1360.2 (requests/second)
Max: 64ms
Min: 0ms
Avg: 0ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 0ms
  95%   below 0ms
  98%   below 1ms
  99%   below 1ms
99.9%   below 3ms
```

