package com.fourkings.shares.spring;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.fourkings.shares.Constants;
import com.fourkings.shares.servlet.SessionTimeoutFilter;
import com.fourkings.shares.utils.FastJsonUtil;

@Configuration
@EnableWebMvc
public class SpringMvcConfig extends WebMvcConfigurerAdapter {

	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer.favorPathExtension(false);
//    }

	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
//		converters.add(converter());
	}
	
//	@Bean
//	VelocityEngine velocityEngine(){
//		VelocityEngine velocityEngine = new VelocityEngine();
//		velocityEngine.setProperty("resource.loader", "class");
//		velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
//		velocityEngine.init();
//	    return velocityEngine;
//	}
	
//	@Bean
//	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory){
//		StringRedisTemplate template = new StringRedisTemplate(factory);
//        //定义key序列化方式
//        //RedisSerializer<String> redisSerializer = new StringRedisSerializer();//Long类型会出现异常信息;需要我们上面的自定义key生成策略，一般没必要
//        //定义value的序列化方式
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);
//        template.setDefaultSerializer(jackson2JsonRedisSerializer);
//        template.afterPropertiesSet();
//        return template;
//    }  
	
//    @Bean
//    public RedisTemplate<Object, Object> sessionRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//
//        // 使用Jackson2JsonRedisSerialize 替换默认序列化
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//
//        // 设置value的序列化规则和 key的序列化规则
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
    
	@Bean
	RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		messageConverters.add(converter());
		restTemplate.setMessageConverters(messageConverters );
		return restTemplate;
	}
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		resolver.setOrder(999999);
		return resolver;
	}
	
	@Bean
	public AbstractHttpMessageConverter<Object> converter() {
//		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(new JsonObjectMapper());
		
		FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setCharset(Charset.forName(Constants.UTF8));
		fastJsonConfig.setWriteContentLength(false);
		fastJsonConfig.setSerializerFeatures(FastJsonUtil.getSerializerFeatures());
		fastJsonConfig.setDateFormat(Constants.YYYY_MM_DD_HH_MM_SS);
		fastJsonConfig.setSerializeFilters(FastJsonUtil.getValueFilter());
		
		List<MediaType> supportedMediaTypes = new ArrayList<>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		converter.setSupportedMediaTypes(supportedMediaTypes );
		
		converter.setFastJsonConfig(fastJsonConfig);
	    return converter;
	}
	
	@Bean
	public FilterRegistrationBean getRequestContextFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		RequestContextFilter requestContextFilter = new RequestContextFilter();
		registrationBean.setFilter(requestContextFilter);
		List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/*");// 拦截路径，可以添加多个
		registrationBean.setUrlPatterns(urlPatterns);
		return registrationBean;
	}
	
	@Bean
	public FilterRegistrationBean sessionFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		SessionTimeoutFilter sessionTimeoutFilter = new SessionTimeoutFilter();
		registrationBean.setFilter(sessionTimeoutFilter);
		List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/*");// 拦截路径，可以添加多个
		registrationBean.setUrlPatterns(urlPatterns);
		return registrationBean;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/static/**").addResourceLocations("/static/")
	            .setCacheControl(CacheControl.maxAge(60, TimeUnit.MINUTES)
                .cachePrivate())
			    .resourceChain(false);
	}
	
//	@ControllerAdvice(basePackages="com.shededata")
//	public class CustomerResponseBodyAdvice implements ResponseBodyAdvice<Object> {
//		private final Logger LOGGER = LoggerFactory.getLogger(CustomerResponseBodyAdvice.class);
//				
//		public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
//			return returnType.getMethod().getReturnType().isAssignableFrom(Response.class); 
//		}
//
//		public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
//				Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
//				ServerHttpResponse response) {
//			Response resp = (Response)body;
//			
//			LOGGER.info("返回【{}】",FastJsonUtil.beanToJson(resp));
//			
//			return resp;
//		}
//		
//	}

}