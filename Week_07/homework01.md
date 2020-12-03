学习笔记

**2 按自己设计的表结构，插入100万订单模拟数据，测试不同方式的插入效率** 

方法一 PreparedStatement batch 方法插入1000，000条数据

```
public void test_insert_million_order() throws SQLException {

    int[] users = new int[]{1,2,3,4,5,6,7,8,9,10};
    int[] products = new int[]{1,2,3,4,5,6,7,8,9,10};
    double[] products_price = new double[]{1.1,2.2,3.3,4.3,5.6,6.6,7.9,8.1,9.0,10.1};
    String insert_order_sql = "insert into t_order_1(order_no,product_id,product_amount,product_unit_price, " +
            "order_total_price, user_id,status,order_desc,create_time,update_time) values(?,?,1,?,?,?,0,null,?,?)";


    ConfigurableApplicationContext application = SpringApplication.run(UserApplication.class);


    DataSource dataSource = application.getBean(DataSource.class);

    Connection connection = dataSource.getConnection();
    connection.setAutoCommit(false);
    PreparedStatement statements = connection.prepareStatement(insert_order_sql);
    Random random =new Random();
    long totalTime = 0;
    for(int i=0;i<20;i++){

        for (int j=0;j<50000;j++){
            statements.setString(1,UUID.randomUUID().toString());
            int productId = random.nextInt(10);
            statements.setInt(2,products[productId]);
            statements.setDouble(3,products_price[productId]);
            statements.setDouble(4,products_price[productId]);
            statements.setInt(5,users[random.nextInt(10)]);
            long createTime = System.currentTimeMillis();
            statements.setDouble(6,createTime);
            statements.setDouble(7,createTime);
            statements.addBatch();
        }
        long start = System.currentTimeMillis();
        statements.executeBatch();
        connection.commit();
        long perTimes=(System.currentTimeMillis()-start);
        totalTime+=perTimes;
        System.out.println("第 "+i+"次耗时："+perTimes);

    }
    System.out.println("总次耗时："+totalTime);
}
```

运行结果：

```
第 0次耗时：53645
第 1次耗时：51939
第 2次耗时：50611
第 3次耗时：54714
第 4次耗时：56295
第 5次耗时：54734
第 6次耗时：62389
第 7次耗时：62466
第 8次耗时：59463
第 9次耗时：55386
第 10次耗时：61306
第 11次耗时：55398
第 12次耗时：63637
第 13次耗时：56545
第 14次耗时：55061
第 15次耗时：60377
第 16次耗时：58245
第 17次耗时：60968
第 18次耗时：66517
第 19次耗时：61781
总次耗时：1161477
```

开始一次性插入 1000000，最后一次做commit;程序运行了半小时以上没结束。

查看mysql执行情况（show processlist）sql还处在sending data阶段，所以放弃了。

测试了50000一次commit大约耗时50s,因此改为分批commit.





方法二  使用myql source 命令导入 1000，000条数据

将1000，000数据写入文件，导入数据库

问题，使用 insert into () values(),(),()语句插入mysql ,如果语句过长，mysql会断开链接，

经测试，选用每次插入40000条不会超出长度限制，

执行时间平均每次 6-8秒 总共耗时 8*15 = 120s,

相比程序跑批插入快非常多。



方法三： 使用方法一的代码，msyql链接添加 rewriteBatchedStatements=true参数

结果：

```
第 1次耗时：13025
第 2次耗时：14953
第 3次耗时：14647
第 4次耗时：14800
第 5次耗时：18500
第 6次耗时：16604
第 7次耗时：16727
第 8次耗时：19721
第 9次耗时：18209
第 10次耗时：22862
总次耗时：170048
```



