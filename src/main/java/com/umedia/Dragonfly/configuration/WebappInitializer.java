package com.umedia.Dragonfly.configuration;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class WebappInitializer implements WebApplicationInitializer {

	
	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		// TODO Auto-generated method stub
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        //ctx.register(HelloWorldConfiguration.class);
        //ctx.setServletContext(container);
        ctx.scan("com.umedia.Dragonfly.configuration");
        
        servletContext.addListener(new ContextLoaderListener(ctx));        
        
        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
              
        //security filter??
        /*FilterRegistration.Dynamic securityFilter = servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
        securityFilter.addMappingForUrlPatterns(null, false, "/");*/
        
        DelegatingFilterProxy filter = new DelegatingFilterProxy("springSecurityFilterChain");
		filter.setContextAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher");
		servletContext.addFilter("springSecurityFilterChain", filter).addMappingForUrlPatterns(null, false, "/*");
        
        servlet.setAsyncSupported(true);
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
	}
	
	

}
