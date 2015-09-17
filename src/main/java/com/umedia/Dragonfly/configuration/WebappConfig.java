package com.umedia.Dragonfly.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.umedia.Dragonfly.dao.DeviceDAO;
import com.umedia.Dragonfly.dao.DeviceDAOImpl;

@Configuration
@EnableWebMvc
@ComponentScan({"com.umedia.Dragonfly.controller", "com.umedia.Dragonfly.configuration","com.umedia.Dragonfly.dao"})
public class WebappConfig extends WebMvcConfigurerAdapter{

	/*@Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
 
        return viewResolver;
    }*/
    
	@Bean
	public TemplateResolver templateResolver() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/views/");
		templateResolver.setSuffix(".html");
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setCacheTTLMs(3600000L);
		templateResolver.setTemplateMode("HTML5");

		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		return templateEngine;
	}

	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setCharacterEncoding("UTF-8");
		viewResolver.setOrder(1);

		return viewResolver;
	}

	// I18n...
	@Bean
	@Description("Spring message resolver")
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("/i18n/messages");

		return messageSource;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/resources/css/**").addResourceLocations("/resources/css/");
	}
    @Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/test");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
		return dataSource;
	}
    @Bean
	public DeviceDAO getDeviceDAO() {
		return new DeviceDAOImpl(getDataSource());
	}
/*    
    @Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("org.postgresql.Driver");
		ds.setUrl("jdbc:postgresql://localhost:5432/springtest");
		ds.setUsername("java");
		ds.setPassword("12345");
		ds.setDriverClassName("org.mariadb.jdbc.Driver");
		ds.setUrl("jdbc:mariadb://localhost:3306/dragonfly");
		ds.setUsername("root");
		ds.setPassword("LZ9ESW");
		return ds;
	}*/
    
/*	@Bean
	public AccessConfirmationController accessConfirmationController(ClientDetailsService clientDetailsService,
			ApprovalStore approvalStore) {
		AccessConfirmationController accessConfirmationController = new AccessConfirmationController();
		accessConfirmationController.setClientDetailsService(clientDetailsService);
		accessConfirmationController.setApprovalStore(approvalStore);
		return accessConfirmationController;
	}*/

}
