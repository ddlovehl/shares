package com.fourkings.shares.spring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;

import com.fourkings.shares.spring.security.CustomerAuthenticationProvider;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringSecurityConfig.class);
	
	@Autowired
	private CustomerAuthenticationProvider customerAuthenticationProvider;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
//		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("USER");
		
		//因为需要自动登录，就把eraseCredentials设为false
		auth.authenticationProvider(customerAuthenticationProvider)
			.eraseCredentials(false)
			;
		
		AuthenticationEventPublisher eventPublisher = new AuthenticationEventPublisher() {
			public void publishAuthenticationSuccess(Authentication authentication) {
				LOGGER.info("---------publishAuthenticationSuccess--------");
			}
			
			public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
				LOGGER.info("---------publishAuthenticationFailure--------");
			}
		};
		auth.authenticationEventPublisher(eventPublisher);
		
	}

	protected void configure(HttpSecurity http) throws Exception {
		LOGGER.info("-------configure-------");
		http
			.authorizeRequests()
				.antMatchers("/front/*","/static/**").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.successHandler(authenticationSuccessHandler())
//				.failureUrl("/login?error")
				.failureHandler(authenticationFailureHandler())
				.permitAll()
				.and()
			.logout()                                                                
				.logoutUrl("/logout")
//				.logoutSuccessUrl("/index")       
				.invalidateHttpSession(true)          
				.addLogoutHandler(customerLogoutHandler())  
//				.deleteCookies(cookieNamesToClear)
				
				;   
		
		http.rememberMe();
		
//		http.sessionManagement()
//			.maximumSessions(1)
//			.expiredUrl("/")
//			.sessionRegistry(getSessionRegistryImpl())
//			;
		
		http
	        .sessionManagement()
	        .maximumSessions(1)
	        .expiredUrl("/login?expired")
	        .sessionRegistry(getSessionRegistryImpl())
//	        .maxSessionsPreventsLogin(true)
	        .and()
//	        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
	        .invalidSessionUrl("/");
		
		http.csrf().disable();
	}
	
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new CustomerAuthenticationSuccessHandler();
	}
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new CustomerAuthenticationFailureHandler();
	}
	@Bean
	public CustomerLogoutHandler customerLogoutHandler() {
		return new CustomerLogoutHandler();
	}
	
	@Bean
	public ConcurrentSessionFilter getConcurrentSessionFilter() {
		ConcurrentSessionFilter concurrentSessionFilter = new ConcurrentSessionFilter(getSessionRegistryImpl());
		return concurrentSessionFilter;
	}
	
	@Bean
	public SessionRegistryImpl getSessionRegistryImpl() {
		return new SessionRegistryImpl();
	}
	
	
	/**
	 * session 单点登录
	 * @Author dd
	 * @Company: 
	 * @Create Time: 2016年8月29日 下午9:38:19
	 * @return
	 */
	@Bean
	public UsernamePasswordAuthenticationFilter getUsernamePasswordAuthenticationFilter() {
		UsernamePasswordAuthenticationFilter authenticationFilter = new UsernamePasswordAuthenticationFilter();
		authenticationFilter.setSessionAuthenticationStrategy(getCompositeSessionAuthenticationStrategy());
		
		List<AuthenticationProvider> providers = new ArrayList<AuthenticationProvider>();
		providers.add(customerAuthenticationProvider);
		ProviderManager providerManager = new ProviderManager(providers );
		authenticationFilter.setAuthenticationManager(providerManager);
		return authenticationFilter;
	}
	@Bean
	public CompositeSessionAuthenticationStrategy getCompositeSessionAuthenticationStrategy() {
		List<SessionAuthenticationStrategy> delegateStrategies = new ArrayList<SessionAuthenticationStrategy>();
		delegateStrategies.add(getConcurrentSessionControlStrategy());
		CompositeSessionAuthenticationStrategy authenticationStrategy = new CompositeSessionAuthenticationStrategy(delegateStrategies);
		return authenticationStrategy;
	}
	@Bean
	public ConcurrentSessionControlAuthenticationStrategy getConcurrentSessionControlStrategy() {
		ConcurrentSessionControlAuthenticationStrategy authenticationStrategy = new ConcurrentSessionControlAuthenticationStrategy(getSessionRegistryImpl());
		authenticationStrategy.setMaximumSessions(1);
		authenticationStrategy.setExceptionIfMaximumExceeded(false);
		return authenticationStrategy;
	}
	
	
	private class CustomerLogoutHandler implements LogoutHandler{
		public void logout(HttpServletRequest req, HttpServletResponse resp, Authentication auth){
			LOGGER.info("--------LogoutHandler-------");
			
			try {
//				req.getSession().invalidate();
				
				resp.sendRedirect("/");
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			}
		}
	};
	
	private class CustomerAuthenticationFailureHandler implements AuthenticationFailureHandler {
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			request.getSession().setAttribute("loginError", exception.getMessage());
			response.sendRedirect("/login");
		}
		
	}
	private class CustomerAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
		public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth)
				throws IOException, ServletException {
			LOGGER.info("--------AuthenticationSuccessHandler-------");
			HttpSession session = req.getSession();
			
			Object principal = auth.getPrincipal();
//			String name = auth.getName();
//			Object obj = auth.getDetails();
//			Object credentials = auth.getCredentials();
			session.removeAttribute("loginError");
			session.setAttribute("user", principal);
			resp.sendRedirect("/myService");
//			req.getRequestDispatcher("/").forward(req, resp);
		}
	};
	
}
