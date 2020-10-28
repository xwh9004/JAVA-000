Serial GC，Parallel GC，CMS，G1总结

不同的GC参数对用yong区，Old区采用的GC如下

![1603785134526](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1603785134526.png)



- Yong区都采用 标记-复制算法；这与Yong区的大小和划分有一定的关系，一般Yong比较小并且区分为Eden,两个Survivor（From,To），发生GC时，直接将 Eden+From区标记存活的对象，复制到To区，然后清理掉所有的Eden+From 区并将From，To指针交换。很是标记复制算法

- Old区都采用标记-清除-整理（CMS除外），因为Old区一般大于Yong区,而且对象存活的时间本身长，如果完全采用复制的算法，移动大量存活对象和更新对象引用势必GC时间很长。该算法不会产生内存碎片。

- CMS 采用标记清除算法，长时间会有内存碎片的堆积。CMS 分为 初始标记，并发标记，重新标记，并发清除四个阶段，在初始标记和重新标记会出现短暂的STW。并发标记与并发清除虽然时间比其他阶段稍长，但是可以用户线程并行。

  

不同GC参数的日志分析 （ -Xms=-Xmx=512m; -Xms=-Xmx=1g,-Xms=-Xmx=2g）的性能对比

![1603786722938](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1603786722938.png)

**备注：吞吐量是以首次开始GC时刻到最后一次GC时刻的时段位采样时间。**

单独分析Serial GC可以得出

![1603785737841](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1603785737841.png)

- Serial GC单线程工作，在需要回收的垃圾增多时，GC时长倍数增加，不适合大的内存JVM运行
- 1g堆内存比512m堆内存的吞吐量高这是因为发生GC的次数明显减少（其中2g 的吞吐量 26.254是在不同机器上测试的结果，而且代码具有随机性，可以不当做参考）。

分析 Paralle GC 可以得出

![1603786888145](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1603786888145.png)

- Paralle GC整体较Serial GC的耗时短，Avg在20ms。但是在GC 线程远大与系统内核数（4核）的时候 GC耗时明显变长，多用于系统线程调度和线程上线文切换与等待。

分析 CMS GC

![1603787235362](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1603787235362.png)

- CMS GC的耗时略好与Paralle GC，因为CMS的并发标记和并发清除阶段可以与用户线程一起运行。体现了CMS的**并发低停顿**的特性。但整体比较两款性能差异不大

分析 G1

 ![1603787602317](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1603787602317.png)

G1的吞吐量明显高于Paralle GC和CMS，停顿时间也是10ms级别以内。