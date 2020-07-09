package com.evalution.covid.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Slf4j
@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    Environment env;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) {

        /**
         * TODO
         * Handle Prefetch calls ,
         */

        String userName = null;
        String userAccountUid = null;


        final String requestMethod = request.getMethod();
        final Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder headers = new StringBuilder();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            final String headerValue = request.getHeader(headerName);
            headers.append(headerName + ":" + headerValue + ";");

        }
        log.debug(headers.toString());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView model) {

    }


}
