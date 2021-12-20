package id.co.vasystem.frontend.config;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class MyFilterSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	private final Logger log = LoggerFactory.getLogger(MyFilterSecurityMetadataSource.class);
	
	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	@Override
	public List<ConfigAttribute> getAttributes(Object object) {

		FilterInvocation fi = (FilterInvocation) object;
	    String fullRequestUrl = fi.getFullRequestUrl();
	    String requestUrl = fi.getRequestUrl();
	    String stringHttpMethod = fi.getRequest().getMethod();
	    String contextPath = fi.getRequest().getContextPath();
	    log.info("Full request URL: " + fullRequestUrl);
	    log.info("Request URL: " + requestUrl);
	    log.info("HTTP Method: " + stringHttpMethod);
	    log.info("Context path: " + contextPath);
	    HttpServletRequest request = fi.getRequest();
        HttpMethod httpMethod = HttpMethod.valueOf(fi.getRequest().getMethod());
	    
   if (new AntPathRequestMatcher("/vendors/**").matches(request)
                || new AntPathRequestMatcher("/js/**").matches(request)
                || new AntPathRequestMatcher("/css/**").matches(request)
                || new AntPathRequestMatcher("/img/**").matches(request)
                || new AntPathRequestMatcher("/bo/img/**").matches(request)
                || new AntPathRequestMatcher("/fonts/**").matches(request)
                || new AntPathRequestMatcher("/accessdenied").matches(request)
                || new AntPathRequestMatcher("/favicon.ico").matches(request)
                || new AntPathRequestMatcher("/login/**").matches(request)
                || new AntPathRequestMatcher("/data/**").matches(request)){
	    	
            return SecurityConfig.createList(new String[] { "Allow" });
        }

	   /* Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities();*/
	    
	    //LOGIC
   
   		//SecurityContextHolder.getContext().getAuthentication();
   		AbstractAuthenticationToken auth = (AbstractAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
   		//UserDetails details = (UserDetails) auth.getDetails();
   		
   		auth.getPrincipal();
   		
//   		log.info("isAccountNonExpired : " + details.isAccountNonExpired());
//   		log.info("isAccountNonLocked : " + details.isAccountNonLocked());
//   		log.info("isCredentialsNonExpired : " + details.isCredentialsNonExpired());
//   		log.info("isEnabled : " + details.isEnabled());
   		
      
   		//return SecurityConfig.createList(new String[] { "Allow" });
	    
	    return SecurityConfig.createList(new String[] { "Deny" });
	    
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
	    return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}
	
//	public boolean supports(Class clazz) {
//	    return clazz.isAssignableFrom(JoinPoint.class);
//	}
	
	
}