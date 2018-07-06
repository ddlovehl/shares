package com.fourkings.shares.spring.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomerUserDetailsService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*private static final Logger LOGGER = LoggerFactory.getLogger(CustomerUserDetailsService.class);
	
	@Autowired
	private UserDao userDao;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		System.out.println(principal);
		User user = null;
		try{
			user = userDao.findByUserName(username);

			if(null == user) {
				throw new UsernameNotFoundException("用户名不存在");
			}
			if(User.ActivityStatus.DISABLE.getKey().equals(user.getActivityStatus())) {
				throw new UsernameNotFoundException("账户被冻结，请联系客服");
			}
			//自定义 查询 数据库对应角色 
//			ArrayList<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
//	        list.add(new SimpleGrantedAuthority("ROLE_SUPERVISOR"));
	        
	        
	        //这里可以判断用户名是否存在。不存在 抛异常出去
	        
		}catch(UsernameNotFoundException e) {
			throw e;
		}catch(Exception e) {
			LOGGER.error("异常",e);
			throw new UsernameNotFoundException("用户名不存在");
		}
        return new CustomerUserDetails(user);
	}*/
	
}