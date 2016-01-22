package com.umedia.Dragonfly.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class HomeController {

	@RequestMapping("/home")
	public String home()
	{
		//test configuration, and resource
		return "home";
	}
	
	@RequestMapping("/")
	public String welcome()
	{
		System.out.println("123");
		return "index";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) throws ServletException
	{
		request.logout();
		
		
/*		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
          if (auth != null){    
             new SecurityContextLogoutHandler().logout(request, response, auth);
          }
        SecurityContextHolder.getContext().setAuthentication(null);*/
		return "index";
	}
	
//	@Autowired
//	private DataSource dataSource;
	
//	@RequestMapping("/clientservice")
//	public String createclient() throws Exception
//	{
//		//create client detail to database
//		JdbcClientDetailsServiceBuilder client = new JdbcClientDetailsServiceBuilder();
//		client.dataSource(dataSource)
//		.withClient("my-trusted-client-with-secret")
//         .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
//         .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//         .scopes("read", "write", "trust")
//         .secret("somesecret");
//		
//		//maybe called by bootstrap process in java.config, not sure
//		client.build();
//		
//		return "index";
//	}
	
}
