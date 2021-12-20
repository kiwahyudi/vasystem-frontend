package id.co.vasystem.frontend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import id.co.vasystem.frontend.service.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
    	
        http
            .authorizeRequests()
                //.antMatchers("/vendors/**","/js/**","/css/**","/img/**","/fonts/**","/accessdenied","/error").permitAll()
            	.antMatchers("/dist/**", "/plugins/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
            .deleteCookies("JSESSIONID")
                .permitAll();
        http.headers().frameOptions().deny();
    	http.cors().and().csrf().disable();
//    	http
//        .authorizeRequests().anyRequest().authenticated().withObjectPostProcessor(new ObjectPostProcessor 
//        
//        	<FilterSecurityInterceptor>() {
//            public <O extends FilterSecurityInterceptor> O postProcess(
//                    O fsi) {
//                FilterInvocationSecurityMetadataSource newSource = (FilterInvocationSecurityMetadataSource) new MyFilterSecurityMetadataSource();
//                fsi.setSecurityMetadataSource(newSource);
//                fsi.setAccessDecisionManager(new MyAccessDecisionManager());
//                
//                return fsi;
//            }
//        });
    	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
    	//http.sessionManagement().invalidSessionUrl("/login").invalidSessionUrl("/login");
    	
        http.exceptionHandling().accessDeniedPage("/accessdenied");
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
//    }
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
	 
}
