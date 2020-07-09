package com.evalution.covid.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    Environment env;
    @Autowired
    ServletContext context;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        context.setAttribute("appUrl", context.getContextPath());
        System.out.println("Application is started and ready to serve Requests"+context.getContextPath());

    }
}
