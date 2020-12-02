package com.example.order.aspect;

import com.example.order.annotatiion.DataSourceTarget;
import com.example.order.config.DynamicRoutingDataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 10:09 on 2020/12/2
 * @version V0.1
 * @classNmae OrderServiceAspect
 */
//@Aspect
//@Component
public class DataSourceRouter implements ApplicationContextAware {

    private ApplicationContext context;

    private String primaryDataSourceName;

    private List<String> replicationDataSourceNames = new ArrayList<>();

    @Autowired
    private DynamicRoutingDataSource sourceRouter;

    /**
     * 基于方法拦截
     * @param joinPoint
     * @return
     * @throws Throwable
     */
//    @Around("execution( * com.example.order.service.impl.OrderServiceImpl.*(..))")
//    public Object dataSourceRouter(ProceedingJoinPoint joinPoint) throws Throwable {
//       String name = joinPoint.getSignature().getName();
//        try{
//            if(name.startsWith("save")){
//                sourceRouter.setRoutKey("primaryDataSource");
//            }
//            if(name.startsWith("query")){
//                sourceRouter.setRoutKey("replicationDataSource");
//            }
//            return joinPoint.proceed();
//        }finally {
//            sourceRouter.clearRoutKey();
//        }
//    }

    @Around("@annotation(com.example.order.annotatiion.DataSourceTarget)")
    public Object dataSourceRouter(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        DataSourceTarget annotation = methodSignature.getMethod().getAnnotation(DataSourceTarget.class);

        try{
            String dataSourceName = annotation.name();
            boolean readyOnly =annotation.readOnly();
            if(StringUtils.hasText(dataSourceName)){
                if(!replicationDataSourceNames.contains(dataSourceName)){
                    throw new RuntimeException("dataSource named "+dataSourceName + "doesn't exist!");
                }
                sourceRouter.setRoutKey(dataSourceName);
            }else if(readyOnly){
                //只读 从库挑一个
                sourceRouter.setRoutKey(getReplicationDataSourceKeyRandom());
            }else{
                sourceRouter.setRoutKey(primaryDataSourceName);
            }

            return joinPoint.proceed();
        }finally {
            sourceRouter.clearRoutKey();
        }
    }

    private String getReplicationDataSourceKeyRandom(){
        Random rand = new Random();
        return   replicationDataSourceNames.get(rand.nextInt(replicationDataSourceNames.size()));
    }

    private void getDataSourceKey() {
        String[] dataSourceNames = this.context.getBeanNamesForType(DataSource.class);
        Stream.of(dataSourceNames).forEach(name->{
            if(name.startsWith("dynamicRoutingDataSource")){
                return ;
            }
            if(name.startsWith("primary")){
                this.primaryDataSourceName = name;
            }else{
                replicationDataSourceNames.add(name);
            }
        });

    }




    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
        getDataSourceKey();
    }
}
