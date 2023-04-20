package com.boot.framework.config;

import com.boot.common.config.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;
import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
	@Autowired
	private SystemConfig systemConfig;
	/**
	 * 静态资源处理
	 **/
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations(systemConfig.getStaticPath());
	}
	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter(
				Charset.forName("UTF-8"));
		return converter;
	}

	/**
	 * 系统配置参数编码
	 * @param converters
	 */
	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		converters.add(responseBodyConverter());
	}


}
