//package com.umedia.Dragonfly.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter  {
//	//@Autowired
//	//private DataSource dataSource;
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		
//		//default logout url 
//		//j_spring_security_logout
//		
//		http.authorizeRequests()		
//		.antMatchers("/home").fullyAuthenticated()
//		.and().logout().permitAll().logoutUrl("/logout")
//		.logoutSuccessUrl("/")
//		.and().formLogin();
//		//.loginPage("/login");		
//		//.and().httpBasic();	
//	}
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth)
//			throws Exception {
//	
//		/*auth.jdbcAuthentication().dataSource(dataSource)
//				.passwordEncoder(passwordEncoder());*/
//		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("USER");
//		auth.inMemoryAuthentication().withUser("test").password("test").roles("test");
//	}
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		PasswordEncoder encoder = new BCryptPasswordEncoder();
//		return encoder;
//	}
//
//	/*@Bean
//	public JdbcUserDetailsManager jdbcUserDetailsManager() {
//		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
//		jdbcUserDetailsManager.setDataSource(dataSource);
//	
//		return jdbcUserDetailsManager;
//	}*/
//	
//	@Override
//	@Bean
//	public AuthenticationManager authenticationManagerBean() throws Exception
//	{
//		return super.authenticationManagerBean();
//	}
//}
