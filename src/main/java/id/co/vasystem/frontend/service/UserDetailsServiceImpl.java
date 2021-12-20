package id.co.vasystem.frontend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import id.co.vasystem.frontend.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
		
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException  {
    	
//    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String password = request.getParameter("password");
        
        id.co.vasystem.frontend.domain.User user = userRepository.findByUsername(userName);
        	
    	UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
    			user.getId(), 
    			user.getPassword(), 
    			
    			AuthorityUtils.createAuthorityList(user.getRole()));
    		  	SecurityContextHolder.getContext().setAuthentication(token);
                
        return User.builder()
        	.username(user.getId())
        	//change here to store encoded password in db
        	.password(passwordEncoder().encode(user.getPassword()))
        	.disabled(false)
        	.accountExpired(false)
        	.accountLocked(false)
        	.credentialsExpired(false)
        	.roles(user.getRole())
        	.build();
    }
    
    @Bean
   	PasswordEncoder passwordEncoder() {
   		return new BCryptPasswordEncoder();
   	}
       
}