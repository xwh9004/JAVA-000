package com.example.school;

import com.example.autoconfig.bean.Student;
import com.example.autoconfig.config.SchoolConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 10:41 on 2020/11/17
 * @version V0.1
 * @classNmae SchoolStarterConfiguration
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.school",name = "enable",havingValue = "true",matchIfMissing = false)
@Import( SchoolConfiguration.class)
public class SchoolStarterConfiguration implements EnvironmentAware {

    private Environment environment;
    /**
     * Set the {@code Environment} that this component runs in.
     *
     * @param environment
     */
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }





}
