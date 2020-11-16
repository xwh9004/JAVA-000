package com.example.spring.xml;

import com.example.spring.bean.Student;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 14:08 on 2020/11/16
 * @version V0.1
 * @classNmae StudentBeanDefinitionParse
 */
public class StudentBeanDefinitionParse extends AbstractSimpleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return Student.class;
    }

    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {

       String id = element.getAttribute("id");

       builder.addPropertyValue("id",id);

        String name = element.getAttribute("name");

        builder.addPropertyValue("name",name);

    }


}
