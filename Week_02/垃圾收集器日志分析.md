

# GC日志分析

## **1 +UseSerialGC** 

日志文件， [SerialGC_512m.demo.log](SerialGC_512m.demo.log)， [SerialGC_1g.demo.log](SerialGC_1g.demo.log) ，[SerialGC_2g.demo.log](SerialGC_2g.demo.log) 

### Minor GC
SerialGC_512m.demo.log中第一次发生GC日志如下：

```
2020-10-24T17:36:03.460+0800: 0.327: [GC (Allocation Failure) 2020-10-24T17:36:03.461+0800: 0.327: [DefNew: 34847K->4351K(39296K), 0.0082927 secs] 34847K->12927K(126720K), 0.0084992 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
```

- Allocation Failure：触发GC原因

- 2020-10-24T17:36:03.461+0800: 0.327：触发GC时刻，JVM启动后0.328s发生一次GC

- DefNew：GC垃圾收集器名称，DefNew表示 年轻代使用的单线程、标记­复制、STW 垃圾收集器 

- 34847K->4351K(39296K)： 34847K 表示GC前新生代使用大小，4351K表示 GC后新生代使用，括号中39296K新生   代总大小，可计算出GC回收了 30496K=34847K-4351K,**可以看到GC前新生代使用率 88.6%=34847K/39296K，GC后新生代使用率 11%=4351K/39296K**。0.0082927 secs 表示 GC执行的时间回收速率 30496K/8ms=3.7M/ms

- 34847K->12927K(126720K), 0.0084992 secs：34847K，表示GC前Java堆总的使用大小，12927K，表示GC后Java堆总的使用大小，126720K,表示Java堆的总大小，可以看到首次GC前，tenured老年代没有对象。GC后堆中老年代新近了8576K=12927K-4351K, 对象计算出老年代使用率 9.81%= 8576K/(126720K-39296K),耗时 0.0155448 secs

- [Times: user=0.02 sys=0.00, real=0.01 secs] ：此次GC事件的持续时间，通过三个部分来衡量： user 部分表示所有 GC线程消耗的CPU时间； sys 部分表示系统调用和系统等待事件消耗的时间。 real 则表示应用程序暂停的时间。因为串行垃圾收集器(Serial Garbage Collector)只使用单个线程，所以这里 real = user + system ，0.01秒也就是10毫秒 。

### Full GC

首次Full GC日志如下：

  ```
  2020-10-24T17:36:03.565+0800: 0.432: [GC (Allocation Failure) 2020-10-24T17:36:03.565+0800: 0.433: [DefNew: 39118K->4347K(39296K), 0.0058201 secs]2020-10-24T17:36:03.571+0800: 0.438: [Tenured: 92082K->92059K(92088K), 0.0135619 secs] 120842K->92625K(131384K), [Metaspace: 2820K->2820K(1056768K)], 0.0202701 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
  ```

  分析2020-10-24T17:36:03.565+0800: 0.433时刻发生了Full GC 耗时0.02 secs，新生代39118K->4347K，回收后新生代利用率 11%=4347K/39296K。耗时0.00580secs。

   [Tenured: 92082K->92059K(92088K), 0.0135619 secs] Tenured 表明使用的是单线程的STW垃圾收集器，使用的算法为 标记‐清除‐整理(mark‐sweep‐compact )   老年代的内存回收，老年代的新进了92082K对象,GC后老年代利用率99.9%=92059K/92088K，Metaspace: 2820K->2820K(1056768K)元空间占用了2820K。从发生的时间上观察，这次老年代的回收发生的时候刚好是在年轻代结束后，立即发生的。

  ### 总结

   **观察每次年轻代GC，可以看出随着回收的内存的增大，GC的时间也基本线性增长。**

   **发生Full GC时，首先清理年轻代，再清理老年代**

  

------



## **2 +UseParallelGC**

## GC日志文件 

[Paralle_512m.demo.log](Paralle_512m.demo.log) ，[Paralle_1g.demo.log](Paralle_1g.demo.log) ，[Paralle_2g.demo.log](Paralle_2g.demo.log) 

###   Minor GC

首次GC  [Paralle_512m.demo.log](Paralle_512m.demo.log)

```
2020-10-24T18:22:45.898+0800: 0.210: [GC (Allocation Failure) [PSYoungGen: 131584K->21495K(153088K)] 131584K->39818K(502784K), 0.0137571 secs] [Times: user=0.03 sys=0.03, real=0.01 secs] 
```



- 分析：PSYoungGen – 垃圾收集器的名称。这个名字表示的是在年轻代中使用的：并行的 标记‐复制(mark‐copy) ，全线暂停(STW) 垃圾收集器 JVM在启动后0.210s时刻发生首次GC，GC耗时0.0137571，回收垃圾110089K=131584K-21495K。提升老年代对象18323K=39818K-21495K。系统停顿0.013s

###   Full GC

```
2020-10-24T18:22:46.233+0800: 0.545: [Full GC (Ergonomics) [PSYoungGen: 21131K->0K(116736K)] [ParOldGen: 315738K->235616K(349696K)] 336870K->235616K(466432K), [Metaspace: 2820K->2820K(1056768K)], 0.0503044 secs] [Times: user=0.17 sys=0.00, real=0.05 secs] 
```

- Full GC 原因：Ergonomics；日志中上一次Minor 如下 ：GC 2020-10-24T18:22:46.218+0800: 0.529: [GC (Allocation Failure) [PSYoungGen: 96442K->21131K(116736K)] 374849K->336870K(466432K), 0.0157317 secs] [Times: user=0.05 sys=0.00, real=0.02 secs] ，提升至老年代对象占用315739K=336870K-21131K。老年占用率 90.28%=315739K/349696K;剩余空间33957K=349696K-315739K 从前面11次Minor  GC 大致计算出 每次Minor GC 提升至老年代的对象大小为29741.6K,虚拟机自适应调节策略认为 **晋升到老生代的平均大小大于老生代的剩余大小** （平均值加上偏差和权重计算得出的平均计算大小），触发Full GC。
- [PSYoungGen: 21131K->0K(116736K)]  清理年轻代的垃圾收集器是名为 “PSYoungGen” 的STW收集器，采用 标记‐复制(mark‐copy) 算法。年轻代使用量从 14341K 变为 0 ，一般 Full GC 中年轻代的结果都是这样 
- [ParOldGen: 315738K->235616K(349696K)] 用于清理老年代空间的垃圾收集器类型。在这里使用的是名为
  ParOldGen 的垃圾收集器，这是一款并行 STW垃圾收集器，算法为 标记‐清除‐整理(mark‐sweep‐compact) 老年代回收 18323K=315738K-235616K
- [Metaspace: 2820K->2820K(1056768K)], 0.0503044 secs] 元空间没有发生垃圾收集，Full GC停顿时间0.0503044 s。整个Full GC时间比较长
- [Times: user=0.17 sys=0.00, real=0.05 secs] ，GC回收时间，整个Full GC 系统STW 0.05secs。

### 总结

**Minor GC 仅仅对 Yong区回收； Full GC 清理了不仅清理了年轻代对象（一般全部清理）和老年代对象（90%降到67%），还清理Metaspace空间（例子中，元空间使用GC前都不变），所以Full GC的耗时远大与Minor GC，从完整的GC 日志来看，Full GC与前一次的Minor GC都是紧挨着发生的，说明JVM在发生Minor GC后，JVM剩余内存仍小于平均晋升到老年代的内存大小，就触发Full GC（Full  GC的一种自使用策略）**

------

## **3 +UseConcMarkSweepGC**

GC日志  [CMSGC_512m.demo.log](CMSGC_512m.demo.log) ， [CMSGC_1g.demo.log](CMSGC_1g.demo.log) ， [CMSGC_2g.demo.log](CMSGC_2g.demo.log) 

### Minor GC

 [CMSGC_512m.demo.log](CMSGC_512m.demo.log) 首次

```
[GC (Allocation Failure) 2020-10-25T21:41:39.844+0800: 0.263: [ParNew: 272638K->34048K(306688K), 0.0196342 secs] 272638K->87834K(1014528K), 0.0198509 secs] [Times: user=0.02 sys=0.05, real=0.02 secs]
```

 

- 分析：新生代采用ParNewGC，时SerialGC的多线程版本;回收了238590K 耗时0.0198509 secs,回收速率12019102K/19.8ms = 11.7M/ms,回收速率要大于SerialGC。

###  Full GC

```
2020-10-25T21:40:43.978+0800: 0.382: [GC (CMS Initial Mark) [1 CMS-initial-mark: 207599K(349568K)] 225832K(506816K), 0.0001246 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 2020-10-25T21:40:43.978+0800: 0.382: [CMS-concurrent-mark-start]
2020-10-25T21:40:43.982+0800: 0.386: [CMS-concurrent-mark: 0.004/0.004 secs] [Times: user=0.03 sys=0.00, real=0.00 secs] 
2020-10-25T21:40:43.982+0800: 0.386: [CMS-concurrent-preclean-start]
2020-10-25T21:40:43.983+0800: 0.387: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-25T21:40:43.983+0800: 0.387: [CMS-concurrent-abortable-preclean-start]
2020-10-25T21:40:44.002+0800: 0.406: [GC (Allocation Failure) 2020-10-25T21:40:44.002+0800: 0.406: [ParNew: 157248K->17472K(157248K), 0.0214361 secs] 364847K->271122K(506816K), 0.0215293 secs] [Times: user=0.05 sys=0.02, real=0.02 secs] 
2020-10-25T21:40:44.044+0800: 0.448: [GC (Allocation Failure) 2020-10-25T21:40:44.044+0800: 0.448: [ParNew: 157248K->17472K(157248K), 0.0216689 secs] 410898K->317463K(506816K), 0.0217631 secs] [Times: user=0.06 sys=0.00, real=0.02 secs] 
2020-10-25T21:40:44.086+0800: 0.490: [GC (Allocation Failure) 2020-10-25T21:40:44.086+0800: 0.490: [ParNew: 157248K->17472K(157248K), 0.0222689 secs] 457239K->362660K(506816K), 0.0223604 secs] [Times: user=0.08 sys=0.03, real=0.02 secs] 
2020-10-25T21:40:44.108+0800: 0.513: [CMS-concurrent-abortable-preclean: 0.003/0.126 secs] [Times: user=0.25 sys=0.05, real=0.13 secs] 
2020-10-25T21:40:44.108+0800: 0.513: [GC (CMS Final Remark) [YG occupancy: 21232 K (157248 K)]2020-10-25T21:40:44.108+0800: 0.513: [Rescan (parallel) , 0.0002181 secs]2020-10-25T21:40:44.109+0800: 0.513: [weak refs processing, 0.0000160 secs]2020-10-25T21:40:44.109+0800: 0.513: [class unloading, 0.0002277 secs]2020-10-25T21:40:44.109+0800: 0.514: [scrub symbol table, 0.0002949 secs]2020-10-25T21:40:44.109+0800: 0.514: [scrub string table, 0.0000925 secs][1 CMS-remark: 345188K(349568K)] 366420K(506816K), 0.0009523 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-25T21:40:44.109+0800: 0.514: [CMS-concurrent-sweep-start]
2020-10-25T21:40:44.110+0800: 0.515: [CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-25T21:40:44.110+0800: 0.515: [CMS-concurrent-reset-start]
2020-10-25T21:40:44.111+0800: 0.515: [CMS-concurrent-reset: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
```



- 分析CMS GC分为

  CMS GC前一次  GC的日志

  ```
  2020-10-25T21:40:43.956+0800: 0.361: [GC (Allocation Failure) 2020-10-25T21:40:43.956+0800: 0.361: [ParNew: 157248K->17472K(157248K), 0.0212292 secs] 319529K->225071K(506816K), 0.0213361 secs] [Times: user=0.05 sys=0.01, real=0.02 secs] 
  ```

  时刻 0.361+0.0212292=0.3823361与发生CMS GC时间对应，GC后老年代大小349568K=506816K-157248K;

  老年代使用率207599K=225071K-17472K;老年代使用率 =59.38%；

  - ```
    [1 CMS-initial-mark: 207599K(349568K)] 225832K(506816K), 0.0001246 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
    ```

     初始标记仅仅只是标记一下GCRoots能直接关联到的对象， 速度很快 ，耗时0.0001246 secs。

    207599K(349568K)说明，在老年代使用207599K是开始标记，与上一次Minor GC后老年代使用率匹配了，

    225832K(506816K) 表示当前堆使用大小(当前堆总大小)；225832K数值略大与上次GC后的225071K。

  - ```
    2020-10-25T21:40:43.978+0800: 0.382: [CMS-concurrent-mark-start]
    ```

     并发标记开始；并发标记阶段就是从GC Roots的直接关联对象开始遍历整个对象图的过程， 这个过程耗时较长但是不需要停顿用户线程， 可以与垃圾收集线程一起并发运行 

    ```
    [CMS-concurrent-mark: 0.004/0.004 secs] [Times: user=0.03 sys=0.00, real=0.00 secs] 
    ```

    0.004/0.004 secs表示并发标记阶段的时间，real=0.00说明，系统的业务线程并没有暂停

    ```
    2020-10-25T21:40:43.982+0800: 0.386: [CMS-concurrent-preclean-start]
    2020-10-25T21:40:43.983+0800: 0.387: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
    ```

    这个阶段又是一个并发阶段，和应用线程并行运行，不会中断他们。前一个阶段在并行运行的时候，一些对象的引用已经发生了变化，当这些引用发生变化的时候，JVM会标记堆的这个区域为Dirty Card(包含被标记但是改变了的对象，被认为"dirty")

  ```
  2020-10-25T21:40:43.983+0800: 0.387: [CMS-concurrent-abortable-preclean-start]
   	...
  2020-10-25T21:40:44.108+0800: 0.513: [CMS-concurrent-abortable-preclean: 0.003/0.126 secs] [Times: user=0.25 sys=0.05, real=0.13 secs] 
  ```

  ​	又一个并发阶段不会停止应用程序线程。这个阶段尝试着去承担STW的Final Remark阶段足够多的工作。这	个阶段持续的时间依赖好多的因素，由于这个阶段是重复的做相同的事情直到发生aboart的条件（比如：	重复的次数、多少量的工作、持续的时间等等）之一才会停止;

  整个阶段持续时间0.126s,期间伴随这几次年轻代GC（这个过程耗时较长但是不需要停顿用户线程， 可以与垃圾收集线程一起并发运行 ）,但其实该阶段CPU使用时间为0.003s;

```
2020-10-25T21:40:44.108+0800: 0.513: [GC (CMS Final Remark) [YG occupancy: 21232 K (157248 K)]2020-10-25T21:40:44.108+0800: 0.513: [Rescan (parallel) , 0.0002181 secs]2020-10-25T21:40:44.109+0800: 0.513: [weak refs processing, 0.0000160 secs]2020-10-25T21:40:44.109+0800: 0.513: [class unloading, 0.0002277 secs]2020-10-25T21:40:44.109+0800: 0.514: [scrub symbol table, 0.0002949 secs]2020-10-25T21:40:44.109+0800: 0.514: [scrub string table, 0.0000925 secs][1 CMS-remark: 345188K(349568K)] 366420K(506816K), 0.0009523 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
```

最终标记（重新标记）：这个阶段是CMS中第二个并且是最后一个STW的阶段。该阶段的任务是完成标记整个年老代的所有的存活对象。由于之前的预处理是并发的，它可能跟不上应用程序改变的速度，这个时候，STW是非常需要的来完成这个严酷考验的阶段。通常CMS尽量运行Final Remark阶段在年轻代是足够干净的时候，目的是消除紧接着的连续的几个STW阶段

1. CMS Final Remark – 收集阶段，这个阶段会标记老年代全部的存活对象，包括那些在并发标记阶段更改的或者新创建的引用对象；

2. [YG occupancy: 21232 K (157248 K)]—年轻代当前占用情况和容量；
3. [Rescan (parallel) , 0.0002181 secs] – 这个阶段在应用停止的阶段完成存活对象的标记工作；
4. [weak refs processing, 0.0000160 secs]---第一个子阶段，随着这个阶段的进行处理弱引用；
5.  [class unloading, 0.0002277 secs]--- 第二个子阶段；卸载不使用的class
6. [scrub symbol table, 0.0002949 secs] [scrub string table, 0.0000925 secs] --最后一个子阶段，分别清理不适用类的符号常量和字符串常量。
7. [1 CMS-remark: 345188K(349568K)] 366420K(506816K), 0.0009523 secs] 在这个阶段之后老年代占有的内存大小和老年代的容量， 在这个阶段之后整个堆的内存大小和整个堆的容量；**持续时间0.0009523 secs，这个时间比初始标记时间0.0001246 secs长，但是比并发标记时间要短很多；**
8. [Times: user=0.00 sys=0.00, real=0.00 secs] 

经历了上述重新标记的阶段，所有存活的老年代对象被标记，垃圾收集器将要回收那些所有没有被使用的对象了。

```
2020-10-25T21:40:44.109+0800: 0.514: [CMS-concurrent-sweep-start]
2020-10-25T21:40:44.110+0800: 0.515: [CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
```

  并发清除（CMS concurrent sweep） ：这个阶段主要是清除那些没有标记的对象并且回收空间，与用户线程并行

耗时0.001secs,[Times: user=0.00 sys=0.00, real=0.00 secs] 系统并未暂停。

```
2020-10-25T21:40:44.110+0800: 0.515: [CMS-concurrent-reset-start]
2020-10-25T21:40:44.111+0800: 0.515: [CMS-concurrent-reset: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
```

这个阶段重新设置CMS算法内部的数据结构，为下一个收集阶段做准备；

### 总结

CMS垃圾回收主要分为初始标记，并发标记，重新标记，并发清除，只有初始标记，和重新标记需要STW,但是整体STW的时间都非常短。在并发标记阶段持续的时间很长，在该阶段与垃圾收集线程和用户线程并行，会产生新的无法标记的垃圾，最后重新标记修正新产生的垃圾。最后执行并发清除。因为采用的标记-清除算法，所以，容易产生碎片垃圾。进而触发新的Full GC,要是CMS运行期间预留的内存无法满足程序分配新对象的需要， 就会出现一次“并发失败”（Concurrent Mode Failure） ， 这时候虚拟机将不得不启动后备预案： 冻结用户线程的执行， 临时启用Serial Old收集器来重新进行老年代的垃圾收集，但这样停顿时间就很长了。 如下日志（ [CMSGC_1g.demo.log](CMSGC_1g.demo.log) ），STW时间70ms。

```
2020-10-25T21:41:40.602+0800: 1.021: [GC (Allocation Failure) 2020-10-25T21:41:40.602+0800: 1.021: [ParNew: 306688K->306688K(306688K), 0.0000253 secs]2020-10-25T21:41:40.602+0800: 1.021: [CMS2020-10-25T21:41:40.602+0800: 1.021: [CMS-concurrent-abortable-preclean: 0.001/0.041 secs] [Times: user=0.03 sys=0.00, real=0.04 secs] 
 (concurrent mode failure): 640197K->346964K(707840K), 0.0731003 secs] 946885K->346964K(1014528K), [Metaspace: 2820K->2820K(1056768K)], 0.0732358 secs] [Times: user=0.08 sys=0.00, real=0.07 secs] 
```





------

## 4 +UserG1GC

日志文件 

 [G1_512m.demo.log](G1_512m.demo.log) ， [G1_1g.demo.log](G1_1g.demo.log) ， [G1_2g.demo.log](G1_2g.demo.log) 

###  Minor GC

```
2020-10-25T22:29:04.777+0800: 0.161: [GC pause (G1 Evacuation Pause) (young), 0.0022545 secs]
   [Parallel Time: 2.0 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 161.4, Avg: 161.5, Max: 161.5, Diff: 0.1]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.3, Max: 0.6, Diff: 0.5, Sum: 1.2]
      [Update RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
         [Processed Buffers: Min: 0, Avg: 0.0, Max: 0, Diff: 0, Sum: 0]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 1.2, Avg: 1.5, Max: 1.6, Diff: 0.4, Sum: 6.0]
      [Termination (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.2]
         [Termination Attempts: Min: 1, Avg: 1.3, Max: 2, Diff: 1, Sum: 5]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 1.8, Avg: 1.9, Max: 1.9, Diff: 0.1, Sum: 7.5]
      [GC Worker End (ms): Min: 163.3, Avg: 163.3, Max: 163.3, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.0 ms]
   [Other: 0.3 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.0 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.0 ms]
   [Eden: 25.0M(25.0M)->0.0B(21.0M) Survivors: 0.0B->4096.0K Heap: 31.8M(512.0M)->10.9M(512.0M)]
   [Times: user=0.00 sys=0.00, real=0.00 secs] 
```

日志分析：

- ```
  0.161: [GC pause (G1 Evacuation Pause) (young), 0.0022545 secs]
  ```

  在JVM启动0.161s后，G1只收集Yong regions 的垃圾，耗时0.0022545 secs，**业务线程暂停0.0022545 secs**

- ```
  [Parallel Time: 2.0 ms, GC Workers: 4]
  ```

   GC工作线程4个，业务线程暂停耗时2.0ms

- ```
  [GC Worker Start (ms): Min: 161.4, Avg: 161.5, Max: 161.5, Diff: 0.1]
  ```

  表明GC线程工作的启动时间（相对JVM的启动时间），如果Min与Max相差较大，说明有其他线程抢占CPU资源。

- ```
  [Ext Root Scanning (ms): Min: 0.1, Avg: 0.3, Max: 0.6, Diff: 0.5, Sum: 1.2]
  ```

  扫描非堆（Meatdata）的Root对象消耗的时间1.2ms

  ```
  [Update RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
  ```

    GC线程更新remember set的时间

  ```
         [Processed Buffers: Min: 0, Avg: 0.0, Max: 0, Diff: 0, Sum: 0]
  ```

  ​    

  ```
  [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
  ```


    扫描CSet中的region对应的RSet的时间

- ```
  [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
  ```

  扫描来自本地变量表的Root对象的时间

  ```
  [Object Copy (ms): Min: 1.2, Avg: 1.5, Max: 1.6, Diff: 0.4, Sum: 6.0]
  ```

  从Collected regions中拷贝存货的的对象到其他regions消耗的时间 6ms

  ```
   [Termination (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.2]
  ```

  确认GC工作线程可以停止的耗时

  ```
   [Termination Attempts: Min: 1, Avg: 1.3, Max: 2, Diff: 1, Sum: 5]
  ```

  有多少GC线程尝试停止

  ```
  [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
  ```

   每个GC线程中不能归属到之前列出的worker阶段的其他时间

  ```
  [GC Worker Total (ms): Min: 1.8, Avg: 1.9, Max: 1.9, Diff: 0.1, Sum: 7.5]
  ```

  ​    GC线程总的工作时间

  ```
  [GC Worker End (ms): Min: 163.3, Avg: 163.3, Max: 163.3, Diff: 0.0]
  ```

  GC线程结束是的JVM启动的时间

  ```
  [Code Root Fixup: 0.0 ms]
  ```

  用来将code root修正到正确的 evacuate 之后的对象位置所花费的时间。

  ```
     [Code Root Purge: 0.0 ms]
  ```

  清除code root的耗时，code root中的引用已经失效，不再指向Region中的对象，所以需要被清除。

  ```
     [Clear CT: 0.0 ms]
  ```

  清除 RSet 扫描元数据(scanning meta-data)的 card table 消耗的时间.

  ```
     [Other: 0.3 ms]
  ```

  其他活动耗时（什么活动呢？？？）

  ```
     [Choose CSet: 0.0 ms]
  ```

  选定要进行垃圾回收的region集合时消耗的时间。通常很小,在必须选择 old 区时会稍微长一点点.

  ```
        [Ref Proc: 0.1 ms]
  ```

  处理 soft, weak, 等非强引用所花费的时间,不同于前面的GC阶段

  ```
    [Ref Enq: 0.0 ms]
  ```

  将 soft, weak, 等引用放置到待处理列表(pending list)花费的时间

   

  ```
    [Redirty Cards: 0.0 ms]
    [Humongous Register: 0.0 ms]
    [Humongous Reclaim: 0.0 ms]
  ```

  ```
     [Free CSet: 0.0 ms]
  ```

  释放regions消耗的时间

  ```
     [Eden: 25.0M(25.0M)->0.0B(21.0M) Survivors: 0.0B->4096.0K Heap: 31.8M(512.0M)->10.9M(512.0M)]
  ```

  Eden区GC前后的使用（容量）大小 25.0M(25.0M)->0.0B(21.0M)

  Survivors区GC前后的使用: 0.0B->4096.0K

  总堆 GC前后的使用（容量） 31.8M(512.0M)->10.9M(512.0M)

```
[Times: user=0.00 sys=0.00, real=0.00 secs] 
```

​       整个GC时间的耗时

### Full  GC

日志：

```
2020-10-25T22:29:04.998+0800: 0.382: [GC pause (G1 Humongous Allocation) (young) (initial-mark), 0.0062570 secs]
   [Parallel Time: 5.9 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 382.1, Avg: 382.1, Max: 382.1, Diff: 0.0]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.2, Max: 0.2, Diff: 0.0, Sum: 0.6]
      [Update RS (ms): Min: 0.1, Avg: 0.1, Max: 0.1, Diff: 0.0, Sum: 0.3]
         [Processed Buffers: Min: 2, Avg: 2.5, Max: 3, Diff: 1, Sum: 10]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 5.6, Avg: 5.6, Max: 5.6, Diff: 0.1, Sum: 22.4]
      [Termination (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.1]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [GC Worker Total (ms): Min: 5.8, Avg: 5.8, Max: 5.9, Diff: 0.0, Sum: 23.4]
      [GC Worker End (ms): Min: 387.9, Avg: 387.9, Max: 387.9, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.0 ms]
   [Other: 0.3 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.0 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.0 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.1 ms]
   [Eden: 9216.0K(15.0M)->0.0B(102.0M) Survivors: 32.0M->3072.0K Heap: 317.6M(512.0M)->309.4M(512.0M)]
 [Times: user=0.00 sys=0.00, real=0.01 secs] 
2020-10-25T22:29:05.004+0800: 0.388: [GC concurrent-root-region-scan-start]
2020-10-25T22:29:05.004+0800: 0.388: [GC concurrent-root-region-scan-end, 0.0001406 secs]
2020-10-25T22:29:05.004+0800: 0.389: [GC concurrent-mark-start]
2020-10-25T22:29:05.008+0800: 0.392: [GC concurrent-mark-end, 0.0034089 secs]
2020-10-25T22:29:05.008+0800: 0.392: [GC remark 2020-10-25T22:29:05.008+0800: 0.392: [Finalize Marking, 0.0001232 secs] 2020-10-25T22:29:05.008+0800: 0.392: [GC ref-proc, 0.0000925 secs] 2020-10-25T22:29:05.008+0800: 0.393: [Unloading, 0.0005820 secs], 0.0012401 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-25T22:29:05.009+0800: 0.394: [GC cleanup 330M->330M(512M), 0.0006649 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
```

日志分析：

上一次Minor GC ：

```
2020-10-25T22:29:04.987+0800: 0.371: [GC pause (G1 Evacuation Pause) (young) (to-space exhausted), 0.0082244 secs]
 [Eden: 302.0M(302.0M)->0.0B(15.0M) Survivors: 5120.0K->32.0M Heap: 427.5M(512.0M)->308.8M(512.0M)]
```

显示分配的Yong 区已经将要耗尽，在次Minor GC耗时0.01s,之后紧接着 发生了G1的并发标记清理垃圾（时刻0.382=0.371+0.01）。对空间的使用量 60%=308.8M/512.0M。

第一阶段：initial-mark，初始标记 [Times: user=0.00 sys=0.00, real=0.01 secs] ，耗时0062570s,发生STW。

第二阶段：concurrent-root-region-scan ，从root regions中扫存活对象，耗时 0.0001406 secs

第三阶段：GC concurrent-mark-并发标记，递归扫描整个堆里的对象图， 找出要回收的对象， 这阶段耗时较长（0.0034089 secs）， 但可与用户程序并发执行。 当对象图扫描完成以后， 还要重新处理SATB记录下的在并发时有引用变动的对象。 

第四阶段：remark，对用户线程做另一个短暂的暂停， 用于处理并发阶段结束后仍遗留下来的最后那少量的SATB记录。  [GC ref-proc, 0.0000925 secs] 2020-10-25T22:29:05.008+0800: 0.393: [Unloading, 0.0005820 secs], 0.0012401 secs 日志中可以看到这个阶段伴有提前清理的工作耗时0.0005820 

第五阶段：cleanup;这一阶段主要为接下来即将要进行的对象转移阶段做准备。统计出所有小堆区中的存活对象，并且对这些小堆区按存活对象数进行排序。也为下一次标记阶段作必要的整理工作，维护并发标记的内部状态 [GC cleanup 330M->330M(512M), 0.0006649 secs],说明这些region中的对象都是存活的，在 [G1_1g.demo.log](G1_1g.demo.log) 日志中该阶段日志格式为

```
2020-10-25T22:29:19.997+0800: 0.633: [GC cleanup 399M->388M(1024M), 0.0005775 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-25T22:29:19.998+0800: 0.634: [GC concurrent-cleanup-start]
2020-10-25T22:29:19.998+0800: 0.634: [GC concurrent-cleanup-end, 0.0000150 secs]
```

表示一些regions值包含垃圾对象

 [Times: user=0.00 sys=0.00, real=0.00 secs] ：该阶段耗时

### 总结

在应用刚启动的时候G1的 的垃圾回收只发生在yong区，即G1收集器会在Fully Young模式下运行，该过程涉及Survivor区转移。会出现短暂的STW。在Old regions区的垃圾回收采用的并发标记清理算法，基本流程与CMS很相似。在Old regions区收集过后，Old regions的region很多时候并不能全部释放，这是JVM会启动混合过程，这个过程不仅会灰机Yong regions也会收集Old regions。从日志中可以看出并发标记模式后有很多 mixed日志（并不是马上发生在并发标记清理模式下）。



------



# 参考

https://plumbr.io/handbook/garbage-collection-algorithms-implementations









