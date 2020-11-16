package com.example.spring.xml;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 14:07 on 2020/11/16
 * @version V0.1
 * @classNmae StudentNameSpaceHandler
 */
public class StudentNameSpaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("student", new StudentBeanDefinitionParse());
    }
}
