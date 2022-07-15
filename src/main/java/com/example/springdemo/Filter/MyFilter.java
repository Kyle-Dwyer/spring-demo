package com.example.springdemo.Filter;

import org.apache.skywalking.apm.toolkit.trace.ActiveSpan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@WebFilter(urlPatterns = {"/"}, filterName = "MyFilter")
public class MyFilter extends HttpFilter {
    private static final Logger logger = LoggerFactory.getLogger(MyFilter.class);
    /**
     * 反序列化
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            try {
                //获取header
                String uuid = request.getHeader("Uid");
                //输出到uuid
                ActiveSpan.tag("uuid", uuid);
                //获取body
                String body = new String(requestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
                if (StringUtils.hasLength(body)) {
                    //输出到input
                    ActiveSpan.tag("input", body);
                    logger.info("input:" + body);
                } else
                    logger.info("input is empty");

                //获取返回值body
                String responseBody = new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
                //输出到output
                ActiveSpan.tag("output", responseBody);
                logger.info("output:" + responseBody);
            } catch (Exception e) {
                logger.warn("fail to build http log", e);
            } finally {
                //这一行必须添加，否则就一直不返回
                responseWrapper.copyBodyToResponse();
            }
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        logger.info("初始化过滤器：" + arg0.getFilterName());
    }
}