package com.example.train.week3;

import javafx.concurrent.Worker;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 一个简单的代码参考：
 */
public class Homework03 {
    public static void main(String[] args) throws Exception{
        /**
         * 24157817
         */
//        getSum_m1();
//        getSum_m2();
//        getSum_m3();
//        getSum_m4();
//        getSum_m5();
//        getSum_m6();
//        getSum_m7();
//        getSum_m8();
//        getSum_m9();
        getSum_m10();
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

    private static int fibonacci(int a){
        int f1 =1,f2=1,f3 =1;
        for(int i=1;i<a;i++){
            f3 =f1 + f2;
            f1 = f2;
            f2 = f3;
        }
        return f3;
    }

    /**
     * 自定义一个线程执行sum方法
     */
    private static void  getSum_m1(){

        try {
            long start=System.currentTimeMillis();
            // 在这里创建一个线程或线程池，
            // 异步执行 下面方法
            Work work = new Work();
            Thread thread = new Thread(work);
            thread.start();
            thread.join();
            // 确保  拿到result 并输出
            System.out.println("异步计算结果为："+work.getResult() +" 使用时间："+ (System.currentTimeMillis()-start) + " ms");

            // 然后退出main线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    /**
     * 使用线程池完成sum 计算
     */
    private static void getSum_m2() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        long start=System.currentTimeMillis();
        Future<Integer> future = service.submit(() -> sum());
        try {
            int result = future.get();
            // 确保  拿到result 并输出
            System.out.println("异步计算结果为："+result +" 使用时间："+ (System.currentTimeMillis()-start) + " ms");
            service.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * FutureTask的第二种用法
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void getSum_m3() throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sum();
            }
        });
        long start=System.currentTimeMillis();
        Thread thread = new Thread(task);
        thread.start();
        int result = task.get();
        System.out.println("异步计算结果为："+result +" 使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    /**
     * 使用 CountDownLatch 控制线程
     */
    private static void getSum_m4() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);
        WorkWithLatch work= new WorkWithLatch(latch);
        Thread thread = new Thread(work);
        long start=System.currentTimeMillis();
        thread.start();
        latch.await();
        System.out.println("异步计算结果为："+work.getResult() +" 使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
    static   class WorkWithLatch implements Runnable{
        private CountDownLatch latch;

        private int sum;
        public WorkWithLatch(CountDownLatch latch){
            this.latch =latch;
        }

        @Override
        public void run() {
            sum =sum();
            latch.countDown();
        }
        public int  getResult(){
            return sum;
        }
    }
    /**
     * 使用 CyclicBarrier 控制线程
     * @throws BrokenBarrierException
     * @throws InterruptedException
     */
    private static void getSum_m5() throws BrokenBarrierException, InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(2);
        WorkWithBarrier work = new WorkWithBarrier(barrier);
        Thread thread = new Thread(work);
        long start=System.currentTimeMillis();
        thread.start();
        barrier.await();
        System.out.println("异步计算结果为："+work.getResult() +" 使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
    static   class WorkWithBarrier implements Runnable{
        private CyclicBarrier barrier;

        private int sum;
        public WorkWithBarrier(CyclicBarrier barrier){
            this.barrier =barrier;
        }

        @Override
        public void run() {
            try {
                sum = sum();
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
        public int  getResult(){
            return sum;
        }
    }
    /**
     * 使用FutureTask
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void getSum_m6() throws ExecutionException, InterruptedException {


        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sum();
            }
        });
        long start=System.currentTimeMillis();
        new Thread(task).start();
        int result = (Integer) task.get();
        System.out.println("异步计算结果为："+result +" 使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    /**
     * 使用Callable
     * @throws Exception
     */
    private static void getSum_m7() throws Exception {
        CompletableFuture future = new CompletableFuture();
        Work work = new Work();
        Callable<Object> call = Executors.callable(work);
        long start=System.currentTimeMillis();
        call.call();
        System.out.println("异步计算结果为："+work.getResult() +" 使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    /**
     * 使用 CompletableFuture
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void getSum_m8() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() ->{return sum();});
        long start=System.currentTimeMillis();
        int result = future.get();
        System.out.println("异步计算结果为："+result +" 使用时间："+ (System.currentTimeMillis()-start) + " ms");

    }

    private static void getSum_m9(){
        Work work =new Work();
        long start=System.currentTimeMillis();
        CompletableFuture.runAsync(work).join();
        System.out.println("异步计算结果为："+work.getResult() +" 使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    /**
     *使用 wait 和notify 配合完成工作
     */
    private static void getSum_m10() throws InterruptedException {
        Object notifier = new Object();
        WorkWithNotify work = new WorkWithNotify(notifier);
        Thread thread = new Thread(work);
        long start=System.currentTimeMillis();
        thread.start();
        synchronized (notifier){
            notifier.wait();
            System.out.println("异步计算结果为："+work.getResult() +" 使用时间："+ (System.currentTimeMillis()-start) + " ms");
        }


    }
    static   class WorkWithNotify implements Runnable{
        private int sum;
        private Object obj;
        WorkWithNotify( Object obj) {
            this.obj =obj;
        }
        @Override
        public void run() {
            synchronized (obj){
                sum =sum();
                obj.notifyAll();
            }
        }
        public int  getResult(){
            return sum;
        }
    }

  static   class Work implements Runnable{
        private int sum;
        @Override
        public void run() {
            sum =sum();
        }
        public int  getResult(){
            return sum;
        }
    }
}
