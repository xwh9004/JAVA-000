```
private static void service(Socket socket) {
        try {
            Thread.sleep(20);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            printWriter.println();
            printWriter.write("hello,nio");
            printWriter.close();
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
```



- 1 单线程io

  ```
  public class HttpServer01 {
      public static void main(String[] args) throws IOException{
          ServerSocket serverSocket = new ServerSocket(8801);
          while (true) {
              try {
                  Socket socket = serverSocket.accept();
                  service(socket);
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
  }
  ```

sb -u http://localhost:8801 -c 20 -N 60

```
Finished at 2020/10/27 10:53:38 (took 00:01:09.1929576)
2855    (RPS: 41.4)                     Status 200:    2855

RPS: 46.6 (requests/second)
Max: 2234ms
Min: 346ms
Avg: 418.1ms

  50%   below 402ms
  60%   below 403ms
  70%   below 406ms
  80%   below 410ms
  90%   below 420ms
  95%   below 439ms
  98%   below 483ms
  99%   below 568ms
99.9%   below 2185ms
```

2 每个请求一个线程

```
public static void main(String[] args) throws IOException{
    ServerSocket serverSocket = new ServerSocket(8802);
    while (true) {
        try {
            final Socket socket = serverSocket.accept();
            new Thread(() -> {
                service(socket);
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

sb -u http://localhost:8802 -c 20 -N 60

```
Finished at 2020/10/27 10:57:34 (took 00:01:08.5619215)
Status 200:    38741

RPS: 634.5 (requests/second)
Max: 1207ms
Min: 20ms
Avg: 27.9ms

  50%   below 25ms
  60%   below 26ms
  70%   below 28ms
  80%   below 31ms
  90%   below 35ms
  95%   below 41ms
  98%   below 51ms
  99%   below 67ms
99.9%   below 223ms
```

3 线程池io

```
public static void main(String[] args) throws IOException{
    ExecutorService executorService = Executors.newFixedThreadPool(40);
    final ServerSocket serverSocket = new ServerSocket(8803);
    while (true) {
        try {
            final Socket socket = serverSocket.accept();
            executorService.execute(() -> service(socket));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

sb -u http://localhost:8803 -c 20 -N 60

```
Finished at 2020/10/27 11:00:26 (took 00:01:09.1819570)
Status 200:    44014
Status 303:    6

RPS: 720.4 (requests/second)
Max: 292ms
Min: 19ms
Avg: 23.5ms

  50%   below 21ms
  60%   below 22ms
  70%   below 23ms
  80%   below 24ms
  90%   below 28ms
  95%   below 32ms
  98%   below 42ms
  99%   below 53ms
99.9%   below 141ms
```

4. netty io

   ```
   Finished at 2020/10/27 11:11:45 (took 00:01:08.6249251)
   Status 200:    45256
   
   RPS: 740.4 (requests/second)
   Max: 227ms
   Min: 19ms
   Avg: 21.7ms
   
     50%   below 20ms
     60%   below 20ms
     70%   below 21ms
     80%   below 22ms
     90%   below 25ms
     95%   below 30ms
     98%   below 37ms
     99%   below 45ms
   99.9%   below 95ms
   ```

   