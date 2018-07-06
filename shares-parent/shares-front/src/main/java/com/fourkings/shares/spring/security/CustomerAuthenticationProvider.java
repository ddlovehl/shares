package com.fourkings.shares.spring.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * 
 * @Author dd
 * @Company: 
 * @Create Time: 2017年7月17日 下午4:47:45
 */
@Component
public class CustomerAuthenticationProvider implements AuthenticationProvider{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAuthenticationProvider.class);
	
	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	private CustomerBCryptPasswordEncoder customerBCryptPasswordEncoder;
	@Autowired
	SessionRegistry sessionRegistry;
	
	public boolean supports(Class<?> authentication) {
//		LOGGER.info("--------supports------");
		return true;
	}
	
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		LOGGER.info("--------authenticate------");
//		LOGGER.info("username【{}】,password【{}】,getPrincipal【{}】,getAuthorities【{}】",authentication.getName(),authentication.getCredentials(),authentication.getPrincipal(),authentication.getAuthorities());
        WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) authentication.getDetails();
        System.out.println("IP:" + webAuthenticationDetails.getRemoteAddress());
        
        //
        UserDetails userDetails = (UserDetails)this.userDetailsService.loadUserByUsername(authentication.getName());
        
        if(!this.customerBCryptPasswordEncoder.matches(String.valueOf(authentication.getCredentials()), userDetails.getPassword())) {
        	throw new BadCredentialsException("用户名或密码错误");
        }
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(),userDetails.getAuthorities());
        return result;
        
	}
}