package com.atguigu.springboot.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
public class DruidConfig {
	
	@ConfigurationProperties(prefix="spring.datasource")
	@Bean
	public DataSource druid() {
		return new DruidDataSource();
	}
	
	//配置Druid的监控

	//1、配置一个管理后台的Servlet

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ServletRegistrationBean statViewServlet() {
		ServletRegistrationBean bean=new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
		Map<String,String> initParams=new HashMap<String,String>();
		initParams.put("loginUsername", "admin");
		initParams.put("loginPassword", "123456");
		initParams.put("allow", "");//默认允许所有访问
		initParams.put("deny", "192.168.0.107");
		bean.setInitParameters(initParams);
		return bean;
	}
	//2、配置一个监控的filter
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FilterRegistrationBean webStatFilter() {
		FilterRegistrationBean bean= new FilterRegistrationBean();
		Map<String,String> initParams=new HashMap<>();
		initParams.put("exclusions", "*.js,*.css,/druid/*");
		bean.setFilter(new WebStatFilter());
		bean.setUrlPatterns(Arrays.asList("/*"));
		return bean;
	}
	
}
