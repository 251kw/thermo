package com.shantery.thermo.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author r.kawahara
 *　Springにインターセプターを登録するクラス
 */
@Configuration
public class ThermoWebMvcConfig implements WebMvcConfigurer {

	/**
	 *　インターセプターを生成
	 * @return インターセプターを返却
	 */
	@Bean
	public ThermoInterceptor thermoInterceptor() {
		return new ThermoInterceptor();
	}

	/**
	 * インターセプターの対象を定義（/cssと/jsへのアクセスは除外）
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(thermoInterceptor()).addPathPatterns("/**").excludePathPatterns("/css/**", "/js/**");
	}
}
